package com.coding.practice.codes.transform.treedata.indentedString;

import com.coding.practice.codes.pipelineExample.common.StepException;
import com.coding.practice.codes.transform.treedata.common.IndentationUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Java8ApproachV2 {
    private static String rawInput = "Hello world\nThis is a sample text\nJava 8 is powerful";
    private static String input= """
            1
                1.1
                1.2
                1.3
                
                
            2
                2.1
                2.2
                2.3
                    2.3.1
                
                
            """;

    public static void main(String[] args) {
//        String[] arr={rawInput, input, null, "", "       "};
        String[] arr={ input};
        for(String s:arr){
            System.out.println("-----------    Start processing: '"+s+"'");
            process(s);
            System.out.println("###############    End processing: '"+s+"'\n\n");
        }
    }

    public static void process(String inputText) {

        // Step 1: Convert raw string to a list of lines
        Function<String, List<String>> step1 = createFunction(
                "Step 1: Split into Lines, post validation that input is non-null and non-empty trimmed text",
                input -> input != null && !input.trim().isEmpty(),
                input -> Arrays.asList(input.split("\\n")),
                "Input string is null or empty"
        );

        // Step 2: Convert each line to a list of Map having trimmed text and indentation level
        Function<List<String>, List<Map<String, Object>>> step2 = createFunction(
                "Step 2: Discard empty lines",
                lines -> lines != null && !lines.isEmpty(),
                lines -> lines.stream()
                        .filter(l-> !l.trim().isEmpty())
                        .collect(Collectors.toList())
                        .stream()
                        .map(s->{
                            Map<String, Object> m= new HashMap<>();
                            m.put("name", s.trim());
                            m.put("indentationLevel", IndentationUtil.findIndentationLevel(s));
                            return m;
                        }).collect(Collectors.toList()),
//                        .flatMap(line -> Arrays.stream(line.split("\\s+")))
//                        .collect(Collectors.toList()),
                "Lines list is null or empty"
        );



        // Step 3: Count the words
        Function<List<String>, Integer> step3 = createFunction(
                "Step 3: Count Words",
                words -> words != null && !words.isEmpty(),
                words -> words.size(),
                "Words list is null or empty"
        );

        // Build the pipeline
//        Function<String, Integer> pipeline = step1
//                .andThen(step2);
                //.andThen(step3);
        Function<String, List<Map<String, Object>>> pipeline = step1
                .andThen(step2);

        try {
//            int wordCount = pipeline.apply(inputText);
            List<Map<String, Object>> wordCount = pipeline.apply(inputText);
            System.out.println("Word Count: " + wordCount);
        } catch (StepException e) {
            System.out.println("Pipeline error: " + e);
            // Additional error handling can be done here
        }
    }

    /**
     * Finds the indentation level for a given string.
     * The method counts the number of leading spaces or tabs in the string.
     *
     * @param input the string to analyze
     * @return the number of leading spaces or tabs
     */
    // Lambda function for finding indentation level
    private static final Function<String, Integer> findIndentationLevel = input -> {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        return (int) input.chars()
                .takeWhile(c -> c == ' ' || c == '\t')
                .count();
    };

    private static <T, R> Function<T, R> createFunction(
            String stepName,
            Function<T, Boolean> validator,
            Function<T, R> processor,
            String errorMessage) {

        return input -> {
            System.out.println("===========\nStarting step: " + stepName);
            if (validator.apply(input)) {
                try {
                    R result= processor.apply(input);
                    System.out.println("Completed step: " + stepName);
                    System.out.println("Step Result : "+result.toString());
                    return result;
                } catch (Exception e) {
                    System.out.println("Error in step '" + stepName + "': " + e.getMessage());
                    throw new StepException(stepName, "Processing error: " + e.getMessage(), e);
                }
            } else {
                System.out.println("Validation failed in step '" + stepName + "': " + errorMessage);
                throw new StepException(stepName, errorMessage);
            }
        };
    }
}
