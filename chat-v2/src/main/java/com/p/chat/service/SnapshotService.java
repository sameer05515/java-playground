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

    public void run() throws IOException {
        long startedAt = System.nanoTime();

        System.out.println("==================================================");
        System.out.println("Starting ChatGPT snapshot pipeline V2");
        System.out.println("Snapshot config : " + properties.getConfigPath());
        System.out.println("Output root     : " + properties.getOutputRoot());
        System.out.println("==================================================");

        ArrayNode snapshotRows = configService.createBaseJson();
        int successCount = 0;
        int failureCount = 0;

        for (JsonNode row : snapshotRows) {
            String slug = row.path("slug").asText("");
            try {
                processSnapshot(row);
                successCount++;
                System.out.println("[SUCCESS] Snapshot " + slug);
            } catch (Exception exception) {
                failureCount++;
                System.err.println("[FAILED] Snapshot " + slug + ": " + exception.getMessage());
                if (!properties.isContinueOnError()) {
                    throw exception instanceof IOException io
                            ? io
                            : new IOException("Snapshot processing failed: " + slug, exception);
                }
            }
        }

        double elapsedMs = (System.nanoTime() - startedAt) / 1_000_000.0;
        System.out.printf(
                "Pipeline completed. Success: %d, Failed: %d, Time: %.2f ms%n",
                successCount, failureCount, elapsedMs);
    }

    private void processSnapshot(JsonNode row) throws IOException {
        String slug = requiredText(row, "slug");
        String location = requiredText(row, "location");
        String createdBy = row.path("createdBy").asText(properties.getCreatedBy());

        Path sourcePath = Path.of(location);
        if (!sourcePath.isAbsolute()) {
            sourcePath = sourcePath.toAbsolutePath().normalize();
        }

        ArrayNode conversations = conversationService.loadConversationRecords(sourcePath);
        SnapshotOutput output = messageService.buildSnapshotOutput(conversations, slug, createdBy);

        Path outputDirectory = properties.getSnapshotOutputRoot().resolve(slug).normalize();
        if (!outputDirectory.startsWith(properties.getSnapshotOutputRoot().normalize())) {
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
    }

    private String requiredText(JsonNode node, String field) {
        String value = node.path(field).asText("");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Missing required field '" + field + "' in base.json row");
        }
        return value;
    }
}
