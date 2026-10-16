package com.p.chat.model;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public record SnapshotOutput(
        ObjectNode index,
        ArrayNode conversations,
        ArrayNode messages,
        ArrayNode messageContents,
        ObjectNode messageContentsMap
) {
}
