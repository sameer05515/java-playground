package com.example.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double calculate(double a, double b, String operation) {
        return switch (operation) {
            case "add" -> a + b;
            case "subtract" -> a - b;
            case "multiply" -> a * b;
            case "divide" -> {
                if (b == 0) {
                    throw new IllegalArgumentException(
                        "Division by zero is not allowed"
                    );
                }
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Invalid operation");
        };
    }
}
