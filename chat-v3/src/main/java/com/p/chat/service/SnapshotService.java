package com.p.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.p.chat.config.SnapshotProperties;
import com.p.chat.model.SnapshotOutput;
import com.p.chat.repository.JsonFileRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SnapshotService {

    private final SnapshotProperties properties;
    private final SnapshotConfigService configService;
    private final JsonFileRepository jsonFiles;
    private final ConversationService conversationService;
    private final MessageService messageService;
    private final QuestionAnswerService questionAnswerService;
    private final DatewiseMessageService datewiseMessageService;

    public SnapshotService(
            SnapshotProperties properties,
            SnapshotConfigService configService,
            JsonFileRepository jsonFiles,
            ConversationService conversationService,
            MessageService messageService,
            QuestionAnswerService questionAnswerService,
            DatewiseMessageService datewiseMessageService
    ) {
        this.properties = properties;
        this.configService = configService;
        this.jsonFiles = jsonFiles;
        this.conversationService = conversationService;
        this.messageService = messageService;
        this.questionAnswerService = questionAnswerService;
        this.datewiseMessageService = datewiseMessageService;
    }

    /**
     * Processes independent snapshots concurrently.
     *
     * Each worker owns one snapshot and writes only to that snapshot's output
     * directory. The shared Spring services/ObjectMapper are stateless after
     * initialization and are safe to use concurrently.
     */
    public void run() throws IOException {
        long startedAt = System.nanoTime();

        System.out.println("==================================================");
        System.out.println("Starting ChatGPT snapshot pipeline V3 (multithreaded)");
        System.out.println("Snapshot config : " + properties.getConfigPath());
        System.out.println("Output root     : " + properties.getOutputRoot());
        System.out.println("Worker threads  : " + properties.getThreads());
        System.out.println("==================================================");

        ArrayNode snapshotRows = configService.createBaseJson();
        if (snapshotRows.isEmpty()) {
            System.out.println("No snapshots found. Nothing to process.");
            return;
        }

        validateUniqueSlugs(snapshotRows);

        int threadCount = Math.min(properties.getThreads(), snapshotRows.size());
        ExecutorService executor = Executors.newFixedThreadPool(
                threadCount, snapshotThreadFactory());
        CompletionService<SnapshotResult> completionService =
                new ExecutorCompletionService<>(executor);
        List<Future<SnapshotResult>> futures = new ArrayList<>();

        int successCount = 0;
        int failureCount = 0;

        try {
            for (JsonNode row : snapshotRows) {
                JsonNode snapshotRow = row.deepCopy();
                futures.add(completionService.submit(() -> processSnapshot(snapshotRow)));
            }

            for (int completed = 0; completed < futures.size(); completed++) {
                try {
                    SnapshotResult result = completionService.take().get();
                    successCount++;
                    System.out.printf(
                            "[SUCCESS] Snapshot %s | thread=%s | time=%.2f ms%n",
                            result.slug(), result.threadName(), result.elapsedMs());
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    cancelAll(futures);
                    throw new IOException("Snapshot processing was interrupted", exception);
                } catch (ExecutionException exception) {
                    failureCount++;
                    Throwable cause = exception.getCause() == null
                            ? exception
                            : exception.getCause();

                    System.err.println("[FAILED] " + cause.getMessage());

                    if (!properties.isContinueOnError()) {
                        cancelAll(futures);
                        throw cause instanceof IOException io
                                ? io
                                : new IOException("Snapshot processing failed", cause);
                    }
                }
            }
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException exception) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        double elapsedMs = (System.nanoTime() - startedAt) / 1_000_000.0;
        System.out.printf(
                "Pipeline completed. Success: %d, Failed: %d, Threads: %d, Time: %.2f ms%n",
                successCount, failureCount, threadCount, elapsedMs);
    }

    private SnapshotResult processSnapshot(JsonNode row) throws IOException {
        long startedAt = System.nanoTime();
        String slug = requiredText(row, "slug");
        String location = requiredText(row, "location");
        String createdBy = row.path("createdBy").asText(properties.getCreatedBy());

        Path sourcePath = Path.of(location);
        if (!sourcePath.isAbsolute()) {
            sourcePath = sourcePath.toAbsolutePath().normalize();
        }

        ArrayNode conversations = conversationService.loadConversationRecords(sourcePath);
        SnapshotOutput output = messageService.buildSnapshotOutput(conversations, slug, createdBy);

        Path outputRoot = properties.getSnapshotOutputRoot().toAbsolutePath().normalize();
        Path outputDirectory = outputRoot.resolve(slug).normalize();
        if (!outputDirectory.startsWith(outputRoot)) {
            throw new IOException("Invalid snapshot slug; output path escapes output root: " + slug);
        }
        Files.createDirectories(outputDirectory);

        jsonFiles.write(outputDirectory.resolve("index.json"), output.index());
        jsonFiles.write(outputDirectory.resolve("conversations.json"), output.conversations());
        jsonFiles.write(outputDirectory.resolve("message.json"), output.messages());
        jsonFiles.write(outputDirectory.resolve("message.contents.json"), output.messageContents());
        jsonFiles.write(outputDirectory.resolve("messageContentsMap.json"), output.messageContentsMap());
        jsonFiles.write(outputDirectory.resolve("qNa.json"),
                questionAnswerService.buildQuestionAnswerMapping(output.messages()));
        jsonFiles.write(outputDirectory.resolve("datewiseMessages.json"),
                datewiseMessageService.buildDatewiseUserMessageIds(output.messages()));

        double elapsedMs = (System.nanoTime() - startedAt) / 1_000_000.0;
        return new SnapshotResult(slug, Thread.currentThread().getName(), elapsedMs);
    }

    private void validateUniqueSlugs(ArrayNode snapshotRows) throws IOException {
        Set<String> slugs = new HashSet<>();

        for (JsonNode row : snapshotRows) {
            String slug = requiredText(row, "slug");
            if (!slugs.add(slug)) {
                throw new IOException("Duplicate snapshot slug found in base.json: " + slug);
            }
        }
    }

    private ThreadFactory snapshotThreadFactory() {
        AtomicInteger counter = new AtomicInteger(1);
        return runnable -> {
            Thread thread = new Thread(runnable, "snapshot-worker-" + counter.getAndIncrement());
            thread.setDaemon(false);
            return thread;
        };
    }

    private void cancelAll(List<Future<SnapshotResult>> futures) {
        futures.forEach(future -> future.cancel(true));
    }

    private String requiredText(JsonNode node, String field) {
        String value = node.path(field).asText("");
        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    "Missing required field '" + field + "' in base.json row");
        }
        return value;
    }

    private record SnapshotResult(String slug, String threadName, double elapsedMs) {
    }
}
