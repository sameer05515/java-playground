package com.coding.practice.codes.pipelineExample;

import com.coding.practice.codes.pipelineExample.common.StepException;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.coding.practice.codes.pipelineExample.common.FunctionFactory.createFunction;

public class PipelineExample6 {
    public static void main(String[] args) {
        String rawInput = "Hello world\nThis is a sample text\nJava 8 is powerful";
        int totalWords = countWords(rawInput);
        System.out.println("Total Words: " + totalWords);
        System.out.println("Total Words: " + countWords(null));
        System.out.println("Total Words: " + countWords(""));
    }

    private static Integer countWords(String rawInput) {
        System.out.println("\n\n$$$$$$$$$$$$$   Starting pipeline for input: '" + rawInput + "'");
        int stepCount = 1;
        Function<String, Integer> pipeline = createFunction(
                stepCount++,
                "Split into Lines",
                (String input) -> input != null && !input.trim().isEmpty(),
                input -> Arrays.asList(input.split("\\n")),
                "Input string is null or empty"
        ).andThen(
                createFunction(
                        stepCount++,
                        "Process Lines",
                        lines -> lines != null && !lines.isEmpty(),
                        lines -> lines.stream()
                                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                                .collect(Collectors.toList()),
                        "Lines list is null or empty"
                )
        ).andThen(
                createFunction(
                        stepCount++,
                        "Count Words",
                        words -> words != null && !words.isEmpty(),
                        words -> words.size(),
                        "Words list is null or empty"
                )
        );
        try {
            return pipeline.apply(rawInput);
        } catch (StepException e) {
            System.out.println("Pipeline error: " + e);
        }
        return 0;
    }
}
