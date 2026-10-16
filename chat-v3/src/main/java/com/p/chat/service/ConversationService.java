package com.p.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.p.chat.repository.JsonFileRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ConversationService {

    private final JsonFileRepository jsonFiles;
    private final ObjectMapper mapper;
    private final ConcurrentMap<Path, Object> directoryLocks = new ConcurrentHashMap<>();

    public ConversationService(JsonFileRepository jsonFiles, ObjectMapper mapper) {
        this.jsonFiles = jsonFiles;
        this.mapper = mapper;
    }

    /**
     * Reads one conversation JSON file, or merges conversations-*.json shards
     * when the supplied path is a directory.
     */
    public ArrayNode loadConversationRecords(Path sourcePath) throws IOException {
        if (!Files.exists(sourcePath)) {
            throw new IOException("Conversation source does not exist: " + sourcePath);
        }

        if (Files.isRegularFile(sourcePath)) {
            JsonNode parsed = jsonFiles.read(sourcePath);
            if (parsed.isArray()) {
                return (ArrayNode) parsed;
            }
            ArrayNode single = mapper.createArrayNode();
            if (!parsed.isNull()) {
                single.add(parsed);
            }
            return single;
        }

        if (Files.isDirectory(sourcePath)) {
            return mergeConversationShards(sourcePath);
        }

        throw new IOException("Unsupported conversation source: " + sourcePath);
    }

    public ArrayNode mergeConversationShards(Path directory) throws IOException {
        Path normalizedDirectory = directory.toAbsolutePath().normalize();
        Object lock = directoryLocks.computeIfAbsent(normalizedDirectory, ignored -> new Object());

        // Two snapshot rows may reference the same source directory.
        // Protect the shared merged-conversations.json from concurrent writes.
        synchronized (lock) {
            ArrayNode merged = mapper.createArrayNode();

            List<Path> shardFiles = jsonFiles.listRegularFiles(normalizedDirectory).stream()
                    .filter(path -> {
                        String name = path.getFileName().toString();
                        return name.startsWith("conversations-") && name.endsWith(".json");
                    })
                    .toList();

            for (Path shard : shardFiles) {
                JsonNode parsed = jsonFiles.read(shard);
                if (parsed.isArray()) {
                    parsed.forEach(item -> merged.add(item.deepCopy()));
                } else if (!parsed.isNull()) {
                    merged.add(parsed.deepCopy());
                }
                System.out.println("Loaded conversation shard: " + shard.getFileName());
            }

            jsonFiles.write(normalizedDirectory.resolve("merged-conversations.json"), merged);
            return merged;
        }
    }

    /**
     * Equivalent to getConversationMessages.js.
     * Follows current_node -> parent links, then reverses to chronological order.
     */
    public List<ObjectNode> getConversationMessages(JsonNode conversation) {
        List<ObjectNode> messages = new ArrayList<>();
        JsonNode mapping = conversation.path("mapping");
        JsonNode currentNode = conversation.get("current_node");
        Set<String> visited = new HashSet<>();

        while (currentNode != null && !currentNode.isNull() && !currentNode.asText().isBlank()) {
            String nodeId = currentNode.asText();

            if (!visited.add(nodeId)) {
                System.err.println("Cycle detected in mapping at node " + nodeId);
                break;
            }

            JsonNode node = mapping.path(nodeId);
            if (node.isMissingNode() || node.isNull()) {
                break;
            }

            JsonNode sourceMessage = node.path("message");
            if (isIncludedMessage(sourceMessage)) {
                messages.add(toMessage(sourceMessage));
            }

            currentNode = node.get("parent");
        }

        Collections.reverse(messages);
        return messages;
    }

    private boolean isIncludedMessage(JsonNode message) {
        if (message == null || message.isMissingNode() || message.isNull()) {
            return false;
        }

        JsonNode content = message.path("content");
        if (!"text".equals(content.path("content_type").asText())) {
            return false;
        }

        JsonNode parts = content.path("parts");
        if (!parts.isArray() || parts.isEmpty()) {
            return false;
        }

        JsonNode firstPart = parts.get(0);
        if (firstPart == null || firstPart.isNull()) {
            return false;
        }
        if (firstPart.isTextual() && firstPart.asText().isEmpty()) {
            return false;
        }

        String role = message.path("author").path("role").asText();
        return !"system".equals(role)
                || message.path("metadata").path("is_user_system_message").asBoolean(false);
    }

    private ObjectNode toMessage(JsonNode source) {
        ObjectNode target = mapper.createObjectNode();
        copyIfPresent(source, target, "id");

        String role = source.path("author").path("role").asText("");
        boolean userSystemMessage = source.path("metadata")
                .path("is_user_system_message").asBoolean(false);

        target.put("author", formatAuthor(role, userSystemMessage));
        target.put("isUserMessage", "user".equals(role));
        target.set("content", source.path("content").path("parts").get(0).deepCopy());
        target.set("createdOn", unixTimestampToIso(source.get("create_time")));
        target.set("updatedOn", unixTimestampToIso(source.get("update_time")));
        return target;
    }

    private String formatAuthor(String role, boolean userSystemMessage) {
        if ("assistant".equals(role)) {
            return "ChatGPT";
        }
        if ("system".equals(role) && userSystemMessage) {
            return "Custom user info";
        }
        if (role == null || role.isEmpty()) {
            return "";
        }
        return role.substring(0, 1).toUpperCase() + role.substring(1);
    }

    private JsonNode unixTimestampToIso(JsonNode timestamp) {
        if (timestamp == null || timestamp.isNull() || !timestamp.isNumber()
                || timestamp.asDouble() == 0.0d) {
            return mapper.getNodeFactory().nullNode();
        }
        return mapper.getNodeFactory().textNode(
                java.time.Instant.ofEpochMilli((long) (timestamp.asDouble() * 1000)).toString());
    }

    private void copyIfPresent(JsonNode source, ObjectNode target, String field) {
        if (source.has(field)) {
            target.set(field, source.get(field).deepCopy());
        }
    }
}
