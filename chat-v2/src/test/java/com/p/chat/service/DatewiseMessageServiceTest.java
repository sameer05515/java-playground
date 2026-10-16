package com.p.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DatewiseMessageServiceTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final DatewiseMessageService service = new DatewiseMessageService(mapper);

    @Test
    void shouldGroupUserMessagesByUtcDate() throws Exception {
        ArrayNode messages = (ArrayNode) mapper.readTree("""
                [
                  {"id":"u1", "isUserMessage":true, "createdOn":"2026-11-12T01:00:00Z"},
                  {"id":"a1", "isUserMessage":false, "createdOn":"2026-11-12T01:01:00Z"},
                  {"id":"u2", "isUserMessage":true, "createdOn":"2026-11-12T02:00:00Z"},
                  {"id":"u3", "isUserMessage":true, "createdOn":"2026-11-13T00:15:00+02:00"}
                ]
                """);

        var result = service.buildDatewiseUserMessageIds(messages);

        assertThat(result.has("2026-11-12")).isTrue();
        assertThat(result.path("2026-11-12").toString()).isEqualTo("[\"u1\",\"u2\",\"u3\"]");
    }
}
