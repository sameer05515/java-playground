package com.coding.practice.codes.pipelineExample.common;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PipelineExecutorV1<T, R> {

    private final List<PipelineStep<T, R>> steps;

    public PipelineExecutorV1(List<PipelineStep<T, R>> steps) {
        this.steps = steps;
    }

    public R execute(T input) {
        T currentInput = input;
        R result = null;

        for (PipelineStep<T, R> step : steps) {
            System.out.println("=====\nStarting step: " + step.getStepName());

            if (step.getValidator().apply(currentInput)) {
                try {
                    result = step.getProcessor().apply(currentInput);
                    System.out.println("Completed step: " + step.getStepName() + "\n-------------");
                    currentInput = (T) result;  // Update the input for the next step
                } catch (Exception e) {
                    System.out.println("Error in step '" + step.getStepName() + "': " + e.getMessage());
                    throw new StepException(step.getStepName(), "Processing error: " + e.getMessage(), e);
                }
            } else {
                System.out.println("Validation failed in step '" + step.getStepName() + "': " + step.getErrorMessage());
                throw new StepException(step.getStepName(), step.getErrorMessage());
            }
        }
        return result;
    }
}
