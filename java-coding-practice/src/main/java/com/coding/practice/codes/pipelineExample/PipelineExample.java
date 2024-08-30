package com.coding.practice.codes.pipelineExample;

import java.util.function.Function;

public class PipelineExample {
    public static void main(String[] args) {
        // Step 1: Convert to uppercase
        Function<String, String> step1 = String::toUpperCase;

        // Step 2: Replace spaces with hyphens
        Function<String, String> step2 = s -> s.replace(" ", "-");

        // Step 3: Remove vowels
        Function<String, String> step3 = s -> s.replaceAll("[AEIOUaeiou]", "");

        // Step 4: Reverse the string
        Function<String, String> step4 = s -> new StringBuilder(s).reverse().toString();

        // Combine the steps using andThen
        Function<String, String> pipeline = step1
                .andThen(step2)
                .andThen(step3)
                .andThen(step4);

        // Apply the pipeline
        String input = "Java 8 functional programming";
        String result = pipeline.apply(input);

        System.out.println(result);  // Output: GN-MMRG-LNCTNLRP-L-8-VJ
    }
}
