package com.p.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Java equivalent of the uploaded Node.js ChatGPT snapshot pipeline.
 *
 * Expected base.json format:
 * [
 *   {"slug":"my-snapshot", "location":"snapshot-folder", "createdBy":"Prem"}
 * ]
 *
 * Source conversations are read from: <projectRoot>/public/<location>
 * Output is written to: <outputRoot>/itr2/<slug>/
 */
public class SnapshotPipeline {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // Change these paths for your machine.
    private static final Path PROJECT_ROOT = Path.of("D:/chatgpt-project");
    private static final Path OUTPUT_ROOT = Path.of("D:/v-dir-v2");
    private static final Path BASE_JSON = OUTPUT_ROOT.resolve("base.json");

    public static void start() throws Exception {
        long startedAt = System.nanoTime();
        printStep("1. Base metadata", BASE_JSON.toString());
        printStep("2. Output root directory", OUTPUT_ROOT.toString());

        if (!Files.isRegularFile(BASE_JSON)) {
            throw new IllegalStateException("base.json not found: " + BASE_JSON
                    + ". Create it using the expected format shown in the class documentation.");
        }

        ArrayNode snapshotRows = (ArrayNode) MAPPER.readTree(BASE_JSON.toFile());
        processSnapshots(snapshotRows);
        buildQuestionAnswerFiles(snapshotRows);
        buildDatewiseMessageFiles(snapshotRows);

        System.out.printf("All tasks completed successfully! Time taken: %.2f ms%n",
                (System.nanoTime() - startedAt) / 1_000_000.0);
    }

    private static void printStep(String title, String detail) {
        System.out.println("-------------------------------------");
        System.out.println(title);
        System.out.println(detail);
    }

    private static Path getOutputDirectory(String slug) {
        return OUTPUT_ROOT.resolve("itr2").resolve(slug);
    }

    // Equivalent to mergeConversationShardFiles().
    private static ArrayNode mergeConversationShardFiles(Path directory) throws IOException {
        ArrayNode merged = MAPPER.createArrayNode();
        try (Stream<Path> files = Files.list(directory)) {
            for (Path file : files
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().startsWith("conversations-")
                            && p.getFileName().toString().endsWith(".json"))
                    .sorted()
                    .toList()) {
                JsonNode parsed = MAPPER.readTree(file.toFile());
                if (parsed.isArray()) {
                    parsed.forEach(merged::add);
                } else if (!parsed.isNull()) {
                    merged.add(parsed);
                }
                System.out.println("Loaded: " + file.getFileName());
            }
        }
        writeJson(directory.resolve("merged-conversations.json"), merged);
        return merged;
    }

    // Equivalent to loadConversationRecords().
    private static JsonNode loadConversationRecords(Path absolutePath) throws IOException {
        if (!Files.exists(absolutePath)) {
            System.err.println("Path not found: " + absolutePath);
            return null;
        }
        if (Files.isRegularFile(absolutePath)) {
            return MAPPER.readTree(absolutePath.toFile());
        }
        if (Files.isDirectory(absolutePath)) {
            return mergeConversationShardFiles(absolutePath);
        }
        System.err.println("Unknown path type: " + absolutePath);
        return null;
    }

    private static void processSnapshots(ArrayNode snapshotRows) throws IOException {
        for (JsonNode row : snapshotRows) {
            try {
                String slug = text(row, "slug");
                String location = text(row, "location");
                String createdBy = text(row, "createdBy");
                Path sourcePath = PROJECT_ROOT.resolve("public").resolve(location);

                JsonNode recordsNode = loadConversationRecords(sourcePath);
                if (recordsNode == null) continue;
                ArrayNode conversationRecords = recordsNode.isArray()
                        ? (ArrayNode) recordsNode : MAPPER.createArrayNode().add(recordsNode);

                SnapshotOutputs outputs = buildSnapshotOutputs(conversationRecords, slug, createdBy);
                Path outputDirectory = getOutputDirectory(slug);
                Files.createDirectories(outputDirectory);

                writeJson(outputDirectory.resolve("index.json"), outputs.snapshotSummary());
                writeJson(outputDirectory.resolve("conversations.json"), outputs.conversationSummaries());
                writeJson(outputDirectory.resolve("message.json"), outputs.messagesWithoutContent());
                writeJson(outputDirectory.resolve("message.contents.json"), outputs.messageContentsList());
                writeJson(outputDirectory.resolve("messageContentsMap.json"),
                        buildMessageContentsById(outputs.messageContentsList(), outputs.messagesWithoutContent()));
            } catch (Exception e) {
                System.err.println("Snapshot Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private record SnapshotOutputs(ObjectNode snapshotSummary, ArrayNode conversationSummaries,
                                   ArrayNode messageContentsList, ArrayNode messagesWithoutContent) {}

    // Equivalent to buildSnapshotOutputs().
    private static SnapshotOutputs buildSnapshotOutputs(ArrayNode conversationRecords,
                                                        String slug, String createdBy) {
        ArrayNode conversationSummaries = MAPPER.createArrayNode();
        ArrayNode messageContentsList = MAPPER.createArrayNode();
        ArrayNode messagesWithoutContent = MAPPER.createArrayNode();
        int totalMessageCount = 0;

        for (JsonNode conversation : conversationRecords) {
            String conversationId = firstNonBlank(text(conversation, "id"), text(conversation, "conversation_id"));
            List<ObjectNode> messages = getConversationMessages(conversation);

            ObjectNode summary = MAPPER.createObjectNode();
            summary.put("id", conversationId);
            copyIfPresent(conversation, summary, "title");
            summary.set("createdOn", unixTimestampToIso(conversation.get("create_time")));
            summary.set("updatedOn", unixTimestampToIso(conversation.get("update_time")));
            summary.put("msgCount", messages.size());
            ArrayNode userMessageIds = MAPPER.createArrayNode();
            messages.stream().filter(m -> "User".equals(text(m, "author")))
                    .forEach(m -> userMessageIds.add(m.path("id")));
            summary.set("messages", userMessageIds);
            conversationSummaries.add(summary);

            for (ObjectNode message : messages) {
                ObjectNode contentRow = MAPPER.createObjectNode();
                copyIfPresent(message, contentRow, "id");
                copyIfPresent(message, contentRow, "content");
                contentRow.put("convId", conversationId);
                messageContentsList.add(contentRow);

                ObjectNode metadataRow = message.deepCopy();
                metadataRow.remove("content");
                metadataRow.put("convId", conversationId);
                messagesWithoutContent.add(metadataRow);
            }
            totalMessageCount += messages.size();
        }

        ObjectNode snapshotSummary = MAPPER.createObjectNode();
        snapshotSummary.put("slug", slug);
        snapshotSummary.put("convCount", conversationRecords.size());
        snapshotSummary.put("totalMsgCount", totalMessageCount);
        snapshotSummary.put("userName", createdBy);
        return new SnapshotOutputs(snapshotSummary, conversationSummaries,
                messageContentsList, messagesWithoutContent);
    }

    /**
     * Java equivalent of getConversationMessages.js.
     * Walk backwards from current_node through parent links, retain eligible
     * text messages, and reverse the result into chronological order.
     */
    private static List<ObjectNode> getConversationMessages(JsonNode conversation) {
        List<ObjectNode> result = new ArrayList<>();
        JsonNode mapping = conversation.path("mapping");
        JsonNode currentNode = conversation.get("current_node");
        java.util.Set<String> visited = new java.util.HashSet<>();

        while (currentNode != null && !currentNode.isNull() && !currentNode.asText().isBlank()) {
            String nodeId = currentNode.asText();
            if (!visited.add(nodeId)) {
                System.err.println("Cycle detected in conversation mapping at node: " + nodeId);
                break;
            }

            JsonNode node = mapping.path(nodeId);
            if (node.isMissingNode() || node.isNull()) break;

            JsonNode message = node.path("message");
            if (isIncludedConversationMessage(message)) {
                result.add(toConversationMessage(message));
            }
            currentNode = node.get("parent");
        }

        java.util.Collections.reverse(result);
        return result;
    }

    private static boolean isIncludedConversationMessage(JsonNode message) {
        if (message == null || message.isMissingNode() || message.isNull()) return false;

        JsonNode content = message.path("content");
        if (!"text".equals(content.path("content_type").asText())) return false;

        JsonNode parts = content.path("parts");
        if (!parts.isArray() || parts.isEmpty()) return false;

        JsonNode firstPart = parts.get(0);
        if (firstPart == null || firstPart.isNull()
                || (firstPart.isTextual() && firstPart.asText().isEmpty())
                || (firstPart.isArray() && firstPart.isEmpty())
                || (firstPart.isObject() && firstPart.isEmpty())) return false;

        String role = message.path("author").path("role").asText();
        return !"system".equals(role)
                || message.path("metadata").path("is_user_system_message").asBoolean(false);
    }

    private static ObjectNode toConversationMessage(JsonNode source) {
        ObjectNode message = MAPPER.createObjectNode();
        copyIfPresent(source, message, "id");

        String role = source.path("author").path("role").asText("");
        boolean userSystemMessage = source.path("metadata")
                .path("is_user_system_message").asBoolean(false);
        message.put("author", formatAuthor(role, userSystemMessage));
        message.put("isUserMessage", "user".equals(role));
        message.set("content", source.path("content").path("parts").get(0).deepCopy());
        message.set("createdOn", unixTimestampToIso(source.get("create_time")));
        message.set("updatedOn", unixTimestampToIso(source.get("update_time")));
        return message;
    }

    private static String formatAuthor(String role, boolean isUserSystemMessage) {
        if ("assistant".equals(role)) return "ChatGPT";
        if ("system".equals(role) && isUserSystemMessage) return "Custom user info";
        if (role == null || role.isEmpty()) return "";
        return role.substring(0, 1).toUpperCase() + role.substring(1);
    }

    // Equivalent to buildMessageContentsById().
    private static ObjectNode buildMessageContentsById(ArrayNode contents, ArrayNode metadata) {
        Map<String, ObjectNode> metadataById = new LinkedHashMap<>();
        for (JsonNode message : metadata) metadataById.put(text(message, "id"), (ObjectNode) message);

        ObjectNode result = MAPPER.createObjectNode();
        for (JsonNode content : contents) {
            String id = text(content, "id");
            ObjectNode combined = metadataById.containsKey(id)
                    ? metadataById.get(id).deepCopy() : MAPPER.createObjectNode();
            content.fields().forEachRemaining(entry -> combined.set(entry.getKey(), entry.getValue().deepCopy()));
            result.set(id, combined);
        }
        return result;
    }

    // Equivalent to buildQuestionAnswerMapping().
    private static ObjectNode buildQuestionAnswerMapping(JsonNode messages) {
        ObjectNode result = MAPPER.createObjectNode();
        String activeUserMessageId = null;
        for (JsonNode message : messages) {
            if (message.path("isUserMessage").asBoolean(false)) {
                activeUserMessageId = text(message, "id");
                result.set(activeUserMessageId, MAPPER.createArrayNode());
            } else if (activeUserMessageId != null) {
                ((ArrayNode) result.get(activeUserMessageId)).add(message.path("id"));
            }
        }
        return result;
    }

    private static void buildQuestionAnswerFiles(ArrayNode snapshotRows) {
        for (JsonNode row : snapshotRows) {
            String slug = text(row, "slug");
            try {
                Path output = getOutputDirectory(slug);
                JsonNode messages = MAPPER.readTree(output.resolve("message.json").toFile());
                writeJson(output.resolve("qNa.json"), buildQuestionAnswerMapping(messages));
            } catch (Exception e) {
                System.err.println("QnA Map Error for slug " + slug + ": " + e.getMessage());
            }
        }
    }

    // Equivalent to buildSortedUserMessageIdsByDate().
    private static ObjectNode buildSortedUserMessageIdsByDate(JsonNode messages) {
        Map<LocalDate, List<JsonNode>> grouped = new LinkedHashMap<>();
        for (JsonNode message : messages) {
            if (!message.path("isUserMessage").asBoolean(false)) continue;
            LocalDate date = parseDate(message.path("createdOn").asText(null));
            if (date != null) grouped.computeIfAbsent(date, ignored -> new ArrayList<>()).add(message);
        }

        ObjectNode result = MAPPER.createObjectNode();
        grouped.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            entry.getValue().sort(Comparator.comparing(m -> m.path("createdOn").asText("")));
            ArrayNode ids = MAPPER.createArrayNode();
            entry.getValue().forEach(message -> ids.add(message.path("id")));
            result.set(entry.getKey().toString(), ids);
        });
        return result;
    }

    private static void buildDatewiseMessageFiles(ArrayNode snapshotRows) {
        for (JsonNode row : snapshotRows) {
            String slug = text(row, "slug");
            try {
                Path output = getOutputDirectory(slug);
                JsonNode messages = MAPPER.readTree(output.resolve("message.json").toFile());
                writeJson(output.resolve("datewiseMessages.json"), buildSortedUserMessageIdsByDate(messages));
            } catch (Exception e) {
                System.err.println("Datewise messages error for slug " + slug + ": " + e.getMessage());
            }
        }
    }

    private static LocalDate parseDate(String value) {
        if (value == null || value.isBlank() || "null".equals(value)) return null;
        try { return OffsetDateTime.parse(value).toLocalDate(); } catch (DateTimeParseException ignored) {}
        try { return Instant.parse(value).atZone(ZoneId.systemDefault()).toLocalDate(); } catch (DateTimeParseException ignored) {}
        for (DateTimeFormatter formatter : List.of(
                DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"),
                DateTimeFormatter.ofPattern("dd-MMM-yyyy"))) {
            try { return LocalDate.parse(value.substring(0, Math.min(value.length(), 11)).trim(), formatter); }
            catch (DateTimeParseException ignored) {}
        }
        return null;
    }

    private static JsonNode unixTimestampToIso(JsonNode timestamp) {
        if (timestamp == null || timestamp.isNull() || !timestamp.isNumber()) return MAPPER.nullNode();
        return MAPPER.getNodeFactory().textNode(Instant.ofEpochMilli((long) (timestamp.asDouble() * 1000))
                .toString());
    }

    private static void writeJson(Path path, JsonNode data) throws IOException {
        Files.createDirectories(path.getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), data);
    }

    private static String text(JsonNode node, String field) {
        JsonNode value = node == null ? null : node.get(field);
        return value == null || value.isNull() ? "" : value.asText("");
    }

    private static String firstNonBlank(String first, String second) {
        return first == null || first.isBlank() ? second : first;
    }

    private static void copyIfPresent(JsonNode source, ObjectNode target, String field) {
        if (source.has(field)) target.set(field, source.get(field).deepCopy());
    }
}
