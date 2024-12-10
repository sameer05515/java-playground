package com.coding.practice.codes.pipelineExample.common;

// StepException.java
public class StepException extends RuntimeException {
    private String stepName;

    public StepException(String stepName, String message) {
        super(message);
        this.stepName = stepName;
    }

    public StepException(String stepName, String message, Throwable cause) {
        super(message, cause);
        this.stepName = stepName;
    }

    public String getStepName() {
        return stepName;
    }

    @Override
    public String toString() {
        return "StepException in '" + stepName + "': " + getMessage();
    }
}

