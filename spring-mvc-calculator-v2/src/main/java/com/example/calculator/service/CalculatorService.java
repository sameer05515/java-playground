package com.example.calculator.service;

import com.example.calculator.exception.CalculationException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service
public class CalculatorService {
    private static final Set<String> BINARY_OPERATIONS = Set.of(
        "add", "subtract", "multiply", "divide", "modulo", "power"
    );
    private static final Set<String> UNARY_OPERATIONS = Set.of(
        "sqrt", "sin", "cos", "tan", "log", "ln", "abs", "factorial"
    );

    public double calculate(double a, double b, String operation) {
        String op = normalize(operation);
        if (!BINARY_OPERATIONS.contains(op)) {
            throw new CalculationException("Unsupported binary operation: " + operation);
        }
        double result = switch (op) {
            case "add" -> a + b;
            case "subtract" -> a - b;
            case "multiply" -> a * b;
            case "divide" -> {
                if (b == 0.0) throw new CalculationException("Division by zero is not allowed");
                yield a / b;
            }
            case "modulo" -> {
                if (b == 0.0) throw new CalculationException("Modulo by zero is not allowed");
                yield a % b;
            }
            case "power" -> Math.pow(a, b);
            default -> throw new CalculationException("Unsupported operation");
        };
        return ensureFinite(result);
    }

    public double calculateUnary(double value, String operation) {
        String op = normalize(operation);
        if (!UNARY_OPERATIONS.contains(op)) {
            throw new CalculationException("Unsupported unary operation: " + operation);
        }
        double result = switch (op) {
            case "sqrt" -> {
                if (value < 0) throw new CalculationException("Square root requires a non-negative number");
                yield Math.sqrt(value);
            }
            case "sin" -> Math.sin(Math.toRadians(value));
            case "cos" -> Math.cos(Math.toRadians(value));
            case "tan" -> {
                double normalized = Math.abs(value % 180.0);
                if (Math.abs(normalized - 90.0) < 1e-10) {
                    throw new CalculationException("Tangent is undefined at this angle");
                }
                yield Math.tan(Math.toRadians(value));
            }
            case "log" -> {
                if (value <= 0) throw new CalculationException("log requires a number greater than zero");
                yield Math.log10(value);
            }
            case "ln" -> {
                if (value <= 0) throw new CalculationException("ln requires a number greater than zero");
                yield Math.log(value);
            }
            case "abs" -> Math.abs(value);
            case "factorial" -> factorial(value);
            default -> throw new CalculationException("Unsupported operation");
        };
        return ensureFinite(result);
    }

    private long factorial(double value) {
        if (value < 0 || value != Math.floor(value)) {
            throw new CalculationException("Factorial requires a non-negative integer");
        }
        if (value > 170) throw new CalculationException("Factorial input must be 170 or less");
        double result = 1;
        for (int i = 2; i <= (int) value; i++) result *= i;
        return (long) result;
    }

    private double ensureFinite(double value) {
        if (!Double.isFinite(value)) throw new CalculationException("Result is outside the supported numeric range");
        return value;
    }

    private String normalize(String operation) {
        if (operation == null) throw new CalculationException("Operation is required");
        return operation.trim().toLowerCase(Locale.ROOT);
    }
}
