package com.p.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.p.chat.config.SnapshotProperties;
import com.p.chat.repository.JsonFileRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;

@Service
public class SnapshotConfigService {

    private final SnapshotProperties properties;
    private final JsonFileRepository jsonFiles;
    private final ObjectMapper mapper;

    public SnapshotConfigService(
            SnapshotProperties properties,
            JsonFileRepository jsonFiles,
            ObjectMapper mapper
    ) {
        this.properties = properties;
        this.jsonFiles = jsonFiles;
        this.mapper = mapper;
    }

    /**
     * Converts snapshots.json into the normalized base.json consumed by the pipeline.
     * Supports the existing property spelling "coversationNames" and the corrected
     * spelling "conversationNames".
     */
    public ArrayNode createBaseJson() throws IOException {
        Path configPath = properties.getConfigPath();
        JsonNode config = jsonFiles.read(configPath);

        JsonNode baseDirNode = config.get("baseDir");
        if (baseDirNode == null || baseDirNode.isNull() || baseDirNode.asText().isBlank()) {
            throw new IllegalArgumentException("Missing or empty 'baseDir' in " + configPath);
        }

        JsonNode names = config.has("conversationNames")
                ? config.get("conversationNames")
                : config.get("coversationNames");

        if (names == null || !names.isObject()) {
            throw new IllegalArgumentException(
                    "Expected 'coversationNames' or 'conversationNames' object in " + configPath);
        }

        Path baseDirectory = Path.of(baseDirNode.asText())
//                .resolve("public")
                .normalize();
        ArrayNode rows = mapper.createArrayNode();
        Iterator<Map.Entry<String, JsonNode>> fields = names.fields();

        int order = 1;
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String relativeLocation = entry.getValue().asText();

            relativeLocation = relativeLocation.replaceFirst("^[/\\\\]+", "");

            Path resolvedLocation = baseDirectory
                    .resolve(relativeLocation)
                    .normalize();

            ObjectNode row = mapper.createObjectNode();
            row.put("id", entry.getKey());
            row.put("slug", "v" + order);
            row.put("order", order);
            row.put("location", resolvedLocation.toString());
            row.put("createdBy", properties.getCreatedBy());
            rows.add(row);
            order++;
        }

        jsonFiles.write(properties.getBaseJsonPath(), rows);
        System.out.println("Created base.json: " + properties.getBaseJsonPath());
        return rows;
    }
}
