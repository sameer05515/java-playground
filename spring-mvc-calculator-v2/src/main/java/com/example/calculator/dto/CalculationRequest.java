package com.example.calculator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CalculationRequest {
    @NotNull(message = "First number is required")
    private Double first;
    @NotNull(message = "Second number is required")
    private Double second;
    @NotBlank(message = "Operation is required")
    private String operation;

    public Double getFirst() { return first; }
    public void setFirst(Double first) { this.first = first; }
    public Double getSecond() { return second; }
    public void setSecond(Double second) { this.second = second; }
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
}
