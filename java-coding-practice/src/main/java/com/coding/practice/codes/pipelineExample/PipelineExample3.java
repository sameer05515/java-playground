package com.coding.practice.codes.pipelineExample;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PipelineExample3 {

    public static void main(String[] args) {
        String rawInput = "Hello world\nThis is a sample text\nJava 8 is powerful";

        // Step 1: Convert raw string to a list of lines
        Function<String, List<String>> step1 = createFunction(
                input -> input != null && !input.trim().isEmpty(),
                input -> Arrays.asList(input.split("\\n")),
                "Input string is null or empty"
        );

        // Step 2: Convert each line to a list of words
        Function<List<String>, List<String>> step2 = createFunction(
                lines -> lines != null && !lines.isEmpty(),
                lines -> lines.stream()
                        .flatMap(line -> Arrays.stream(line.split("\\s+")))
                        .collect(Collectors.toList()),
                "Lines list is null or empty"
        );

        // Step 3: Count the words
        Function<List<String>, Integer> step3 = createFunction(
                words -> words != null && !words.isEmpty(),
                words -> words.size(),
                "Words list is null or empty"
        );

        // Build the pipeline
        Function<String, Integer> pipeline = step1
                .andThen(step2)
                .andThen(step3);

        try {
            int wordCount = pipeline.apply(rawInput);
            System.out.println("Word Count: " + wordCount);
        } catch (RuntimeException e) {
            System.err.println("Pipeline error: " + e.getMessage());
        }
    }

    // Utility to create custom Function with validation and exception handling
    private static <T, R> Function<T, R> createFunction(
            Function<T, Boolean> validator,
            Function<T, R> processor,
            String errorMessage) {

        return input -> {
            if (validator.apply(input)) {
                try {
                    return processor.apply(input);
                } catch (Exception e) {
                    throw new RuntimeException("Processing error: " + e.getMessage(), e);
                }
            } else {
                throw new RuntimeException(errorMessage);
            }
        };
    }
}

