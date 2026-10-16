package com.p.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

@Service
public class QuestionAnswerService {

    private final ObjectMapper mapper;

    public QuestionAnswerService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Associates each user message ID with all subsequent non-user message IDs
     * until the next user message is encountered.
     */
    public ObjectNode buildQuestionAnswerMapping(JsonNode messages) {
        ObjectNode result = mapper.createObjectNode();
        String activeUserMessageId = null;

        for (JsonNode message : messages) {
            if (message.path("isUserMessage").asBoolean(false)) {
                activeUserMessageId = message.path("id").asText("");
                if (activeUserMessageId.isBlank()) {
                    activeUserMessageId = null;
                } else {
                    result.set(activeUserMessageId, mapper.createArrayNode());
                }
            } else if (activeUserMessageId != null) {
                ((ArrayNode) result.get(activeUserMessageId))
                        .add(message.path("id").deepCopy());
            }
        }
        return result;
    }
}
