//package com.coding.practice.codes.pipelineExample;
//
//public class PipelineExecutorExampleV1 {
//}

package com.coding.practice.codes.pipelineExample;

import com.coding.practice.codes.pipelineExample.common.PipelineExecutorV1;
import com.coding.practice.codes.pipelineExample.common.PipelineStep;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PipelineExecutorExampleV1 {
    public static void main(String[] args) {
        String rawInput = "Hello world\nThis is a sample text\nJava 8 is powerful";
        int totalWords = countWords(rawInput);
        System.out.println("Total Words: " + totalWords);

        System.out.println("Total Words: " + countWords(null));  // Example of failure case
        System.out.println("Total Words: " + countWords(""));    // Example of empty input
    }

    private static Integer countWords(String rawInput) {
        System.out.println("\n\n$$$$$$$$$$$$$   Starting pipeline for input: '" + rawInput + "'");

        // Define pipeline steps
        List<PipelineStep<Object, Object>> steps = Arrays.asList(
                PipelineStep.<Object, Object>builder()
                        .stepName("Split into Lines")
                        .validator(input -> input != null && !input.toString().trim().isEmpty())
                        .processor(input -> Arrays.asList(input.toString().split("\\n")))
                        .errorMessage("Input string is null or empty")
                        .build(),
                PipelineStep.<Object, Object>builder()
                        .stepName("Process Lines")
                        .validator(lines -> lines != null && !((List<?>) lines).isEmpty())
                        .processor(lines -> ((List<String>) lines).stream()
                                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                                .collect(Collectors.toList()))
                        .errorMessage("Lines list is null or empty")
                        .build(),
                PipelineStep.<Object, Object>builder()
                        .stepName("Count Words")
                        .validator(words -> words != null && !((List<?>) words).isEmpty())
                        .processor(words -> ((List<?>) words).size())
                        .errorMessage("Words list is null or empty")
                        .build()
        );

        // Create and execute pipeline
        PipelineExecutorV1<Object, Object> pipelineExecutor = new PipelineExecutorV1<>(steps);

        try {
            return (Integer) pipelineExecutor.execute(rawInput);
        } catch (Exception e) {
            System.out.println("Pipeline error: " + e.getMessage());
            return 0;
        }
    }
}

