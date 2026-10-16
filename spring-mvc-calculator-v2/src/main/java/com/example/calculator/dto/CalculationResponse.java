package com.example.calculator.dto;

public class CalculationResponse {
    private final double first;
    private final double second;
    private final String operation;
    private final double result;

    public CalculationResponse(double first, double second, String operation, double result) {
        this.first = first;
        this.second = second;
        this.operation = operation;
        this.result = result;
    }
    public double getFirst() { return first; }
    public double getSecond() { return second; }
    public String getOperation() { return operation; }
    public double getResult() { return result; }
}
