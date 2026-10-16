package com.p.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionAnswerServiceTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final QuestionAnswerService service = new QuestionAnswerService(mapper);

    @Test
    void shouldMapEachUserMessageToFollowingAssistantMessages() throws Exception {
        ArrayNode messages = (ArrayNode) mapper.readTree("""
                [
                  {"id":"u1", "isUserMessage":true},
                  {"id":"a1", "isUserMessage":false},
                  {"id":"a2", "isUserMessage":false},
                  {"id":"u2", "isUserMessage":true},
                  {"id":"a3", "isUserMessage":false}
                ]
                """);

        var mapping = service.buildQuestionAnswerMapping(messages);

        assertThat(mapping.path("u1").toString()).isEqualTo("[\"a1\",\"a2\"]");
        assertThat(mapping.path("u2").toString()).isEqualTo("[\"a3\"]");
    }
}
