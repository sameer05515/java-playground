package com.p.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.p.chat.repository.JsonFileRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConversationServiceTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ConversationService service =
            new ConversationService(new JsonFileRepository(mapper), mapper);

    @Test
    void shouldTraverseCurrentNodeParentsAndReturnChronologicalMessages() throws Exception {
        String json = """
                {
                  "current_node": "n3",
                  "mapping": {
                    "n1": {
                      "parent": null,
                      "message": {
                        "id": "m1",
                        "author": {"role": "user"},
                        "content": {"content_type": "text", "parts": ["Question"]},
                        "create_time": 1700000000
                      }
                    },
                    "n2": {
                      "parent": "n1",
                      "message": {
                        "id": "m2",
                        "author": {"role": "assistant"},
                        "content": {"content_type": "text", "parts": ["Answer"]},
                        "create_time": 1700000001
                      }
                    },
                    "n3": {
                      "parent": "n2",
                      "message": {
                        "id": "m3",
                        "author": {"role": "user"},
                        "content": {"content_type": "text", "parts": ["Next question"]},
                        "create_time": 1700000002
                      }
                    }
                  }
                }
                """;

        List<ObjectNode> messages = service.getConversationMessages(mapper.readTree(json));

        assertThat(messages).extracting(node -> node.path("id").asText())
                .containsExactly("m1", "m2", "m3");
        assertThat(messages).extracting(node -> node.path("author").asText())
                .containsExactly("User", "ChatGPT", "User");
        assertThat(messages.get(0).path("isUserMessage").asBoolean()).isTrue();
    }

    @Test
    void shouldExcludeSystemMessagesUnlessTheyAreUserSystemMessages() throws Exception {
        String json = """
                {
                  "current_node": "n2",
                  "mapping": {
                    "n1": {
                      "parent": null,
                      "message": {
                        "id": "system-1",
                        "author": {"role": "system"},
                        "content": {"content_type": "text", "parts": ["hidden"]}
                      }
                    },
                    "n2": {
                      "parent": "n1",
                      "message": {
                        "id": "system-2",
                        "author": {"role": "system"},
                        "metadata": {"is_user_system_message": true},
                        "content": {"content_type": "text", "parts": ["visible"]}
                      }
                    }
                  }
                }
                """;

        List<ObjectNode> messages = service.getConversationMessages(mapper.readTree(json));

        assertThat(messages).hasSize(1);
        assertThat(messages.get(0).path("author").asText()).isEqualTo("Custom user info");
    }
}
