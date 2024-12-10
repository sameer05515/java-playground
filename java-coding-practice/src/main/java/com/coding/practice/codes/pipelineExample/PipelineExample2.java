package com.coding.practice.codes.pipelineExample;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PipelineExample2 {
    public static void main(String[] args) {
        // Step 1: Split raw string into a list of strings (lines)
        Function<String, List<String>> step1 = raw -> Arrays.asList(raw.split("\n"));

        // Step 2: Convert each line to lowercase and trim spaces
        Function<List<String>, List<String>> step2 = lines ->
                lines.stream()
                        .map(String::toLowerCase)
                        .map(String::trim)
                        .collect(Collectors.toList());

        // Step 3: Calculate the word count from the processed lines
        Function<List<String>, Long> step3 = lines ->
                lines.stream()
                        .flatMap(line -> Stream.of(line.split("\\s+")))
                        .count();

        // Combine the steps using andThen
        Function<String, Long> pipeline = step1
                .andThen(step2)
                .andThen(step3);

        // Example input
        String rawInput = "Java 8 is great\nStream API is powerful\nFunctional Programming is fun";

        // Apply the pipeline
        Long wordCount = pipeline.apply(rawInput);

        System.out.println("Total Word Count: " + wordCount); // Output: Total Word Count: 10
    }
}
