package com.p.chat.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class JsonFileRepository {

    private final ObjectMapper mapper;

    public JsonFileRepository(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public JsonNode read(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            throw new IOException("JSON file does not exist: " + path);
        }
        return mapper.readTree(path.toFile());
    }

    public ArrayNode readArray(Path path) throws IOException {
        JsonNode node = read(path);
        if (!node.isArray()) {
            throw new IOException("Expected a JSON array in: " + path);
        }
        return (ArrayNode) node;
    }

    public void write(Path path, JsonNode data) throws IOException {
        Path parent = path.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), data);
    }

    public List<Path> listRegularFiles(Path directory) throws IOException {
        try (Stream<Path> stream = Files.list(directory)) {
            return stream.filter(Files::isRegularFile).sorted().toList();
        }
    }

    public ObjectMapper mapper() {
        return mapper;
    }
}
