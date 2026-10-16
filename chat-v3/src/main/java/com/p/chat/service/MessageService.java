package com.p.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.p.chat.model.SnapshotOutput;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    private final ConversationService conversationService;
    private final ObjectMapper mapper;

    public MessageService(ConversationService conversationService, ObjectMapper mapper) {
        this.conversationService = conversationService;
        this.mapper = mapper;
    }

    public SnapshotOutput buildSnapshotOutput(
            ArrayNode conversationRecords,
            String slug,
            String createdBy
    ) {
        ArrayNode conversationSummaries = mapper.createArrayNode();
        ArrayNode messageContents = mapper.createArrayNode();
        ArrayNode messagesWithoutContent = mapper.createArrayNode();

        int totalMessageCount = 0;

        for (JsonNode conversation : conversationRecords) {
            String conversationId = firstNonBlank(
                    text(conversation, "id"),
                    text(conversation, "conversation_id"));

            List<ObjectNode> messages = conversationService.getConversationMessages(conversation);

            ObjectNode summary = mapper.createObjectNode();
            summary.put("id", conversationId);
            copyIfPresent(conversation, summary, "title");
            summary.set("createdOn", unixTimestampToIso(conversation.get("create_time")));
            summary.set("updatedOn", unixTimestampToIso(conversation.get("update_time")));
            summary.put("msgCount", messages.size());

            ArrayNode userMessageIds = mapper.createArrayNode();
            messages.stream()
                    .filter(message -> "User".equals(text(message, "author")))
                    .forEach(message -> userMessageIds.add(message.path("id").deepCopy()));
            summary.set("messages", userMessageIds);
            conversationSummaries.add(summary);

            for (ObjectNode message : messages) {
                ObjectNode contentRow = mapper.createObjectNode();
                copyIfPresent(message, contentRow, "id");
                copyIfPresent(message, contentRow, "content");
                contentRow.put("convId", conversationId);
                messageContents.add(contentRow);

                ObjectNode metadataRow = message.deepCopy();
                metadataRow.remove("content");
                metadataRow.put("convId", conversationId);
                messagesWithoutContent.add(metadataRow);
            }

            totalMessageCount += messages.size();
        }

        ObjectNode index = mapper.createObjectNode();
        index.put("slug", slug);
        index.put("convCount", conversationRecords.size());
        index.put("totalMsgCount", totalMessageCount);
        index.put("userName", createdBy);

        ObjectNode contentsMap = buildMessageContentsMap(messageContents, messagesWithoutContent);

        return new SnapshotOutput(index, conversationSummaries,
                messagesWithoutContent, messageContents, contentsMap);
    }

    public ObjectNode buildMessageContentsMap(ArrayNode contents, ArrayNode metadata) {
        Map<String, ObjectNode> metadataById = new LinkedHashMap<>();
        for (JsonNode message : metadata) {
            String id = text(message, "id");
            if (!id.isBlank()) {
                metadataById.put(id, (ObjectNode) message);
            }
        }

        ObjectNode result = mapper.createObjectNode();
        for (JsonNode content : contents) {
            String id = text(content, "id");
            ObjectNode combined = metadataById.containsKey(id)
                    ? metadataById.get(id).deepCopy()
                    : mapper.createObjectNode();

            content.fields().forEachRemaining(entry ->
                    combined.set(entry.getKey(), entry.getValue().deepCopy()));

            if (!id.isBlank()) {
                result.set(id, combined);
            }
        }
        return result;
    }

    private JsonNode unixTimestampToIso(JsonNode timestamp) {
        if (timestamp == null || timestamp.isNull() || !timestamp.isNumber()
                || timestamp.asDouble() == 0.0d) {
            return mapper.getNodeFactory().nullNode();
        }
        return mapper.getNodeFactory().textNode(
                java.time.Instant.ofEpochMilli((long) (timestamp.asDouble() * 1000)).toString());
    }

    private String text(JsonNode node, String field) {
        JsonNode value = node == null ? null : node.get(field);
        return value == null || value.isNull() ? "" : value.asText("");
    }

    private String firstNonBlank(String first, String second) {
        return first == null || first.isBlank() ? second : first;
    }

    private void copyIfPresent(JsonNode source, ObjectNode target, String field) {
        if (source.has(field)) {
            target.set(field, source.get(field).deepCopy());
        }
    }
}
