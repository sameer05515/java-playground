package com.p.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

@SpringBootApplication
public class ChatApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    private String snapshotPath = "D:\\GIT\\microservices-playground\\example-base-03\\fontend\\chat-gpt-conversation\\src\\common\\utils\\snapshots.json";

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting chatgpt conversation backup processing");
        System.out.println("Reading snapshot from " + snapshotPath);

        //readSnapshotV1(snapshotPath);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new File(snapshotPath));

        System.out.println("LATEST_CONVERSATION_FILE: " + rootNode.get("LATEST_CONVERSATION_FILE").asText());
        System.out.println("baseDir: " + rootNode.get("baseDir").asText());

        diagnoseConversationNames(rootNode);

        createBaseJson(rootNode);

        ///////
        SnapshotPipeline.start();
    }

    private void createBaseJson(JsonNode snapRootNode) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Create JSON object
        ArrayNode rootNode = mapper.createArrayNode();


        String baseDir = snapRootNode.get("baseDir").asText();
        JsonNode conversationNamesNode = snapRootNode.get("coversationNames");
        Iterator<String> conversationNames = snapRootNode.get("coversationNames").fieldNames();

        int counter = 1;
        while (conversationNames.hasNext()) {
            ObjectNode baseObject = mapper.createObjectNode();
            String id = conversationNames.next();
            baseObject.put("id", id);
            baseObject.put("slug", "v" + counter);
            baseObject.put("order", counter);
            baseObject.put("location", baseDir + conversationNamesNode.get(id).asText());
            baseObject.put("createdBy", "Prem");

            rootNode.add(baseObject);


            counter++;
        }


        // Write JSON to file
        File file = new File("D:/v-dir-v2/base.json");

        file.getParentFile().mkdirs();

        mapper.writeValue(file, rootNode);

        System.out.println("base.json created successfully!");
    }

    private void diagnoseConversationNames(JsonNode snapRootNode) throws IOException {
        System.out.println("coversationNames: ");

        JsonNode conversationNames = snapRootNode.get("coversationNames");

        Iterator<String> names = snapRootNode.get("coversationNames").fieldNames();
        while (names.hasNext()) {

            String path = snapRootNode.get("baseDir").asText() + conversationNames.get(names.next()).asText();

            System.out.println(path);
            Path filePath = Path.of(path);

            if (Files.isRegularFile(filePath)) {
                System.out.println("It is a file");
                readConvo(path);

            } else if (Files.isDirectory(filePath)) {
                System.out.println("It is a directory");
                try (Stream<Path> files = Files.list(filePath)) {

                    files.filter(Files::isRegularFile).forEach(file -> {
                        try {
                            readConvo(file.toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });


                }
            } else {
                System.out.println("Path does not exist or is inaccessible");
            }
        }
    }

    private void readConvo(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode convRootNode = mapper.readTree(new File(path));
        System.out.println("" + convRootNode.isArray() + convRootNode.size());
    }

    private void readSnapshotV1(String snapshotPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(new File(snapshotPath));

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root));
    }
}
