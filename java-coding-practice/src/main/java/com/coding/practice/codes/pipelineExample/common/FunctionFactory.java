package com.coding.practice.codes.pipelineExample.common;

import java.util.Optional;
import java.util.function.Function;


public interface FunctionFactory {

    static <T, R> Function<T, R> createFunction(
            int stepNo,
            String stepName,
            Function<T, Boolean> validator,
            Function<T, R> processor,
            String errorMessage) {
        return input -> {
            System.out.println("=====\n" +
                    Optional.of(stepNo)
                            .filter(n -> n > 0)
                            .map(n -> "Step: "+stepNo + " :\n")
                            .orElse("") +
                    "Starting step: " + stepName);
            if (validator.apply(input)) {
                try {
                    R result = processor.apply(input);
                    System.out.println("Completed step: " + stepName + "\n-------------");
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

    static <T, R> Function<T, R> createFunction(
            String stepName,
            Function<T, Boolean> validator,
            Function<T, R> processor,
            String errorMessage) {

        return createFunction(0, stepName, validator, processor, errorMessage);
    }
}
