package com.example.calculator.controller;

import com.example.calculator.dto.CalculationRequest;
import com.example.calculator.dto.CalculationResponse;
import com.example.calculator.service.CalculatorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorRestController {
    private final CalculatorService calculatorService;

    public CalculatorRestController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/binary")
    public CalculationResponse calculateBinary(@Valid @RequestBody CalculationRequest request) {
        double result = calculatorService.calculate(
            request.getFirst(), request.getSecond(), request.getOperation()
        );
        return new CalculationResponse(
            request.getFirst(), request.getSecond(), request.getOperation(), result
        );
    }

    @PostMapping("/unary")
    public UnaryResponse calculateUnary(@Valid @RequestBody UnaryRequest request) {
        double result = calculatorService.calculateUnary(request.getValue(), request.getOperation());
        return new UnaryResponse(request.getValue(), request.getOperation(), result);
    }

    public static class UnaryRequest {
        @jakarta.validation.constraints.NotNull(message = "Value is required")
        private Double value;
        @jakarta.validation.constraints.NotBlank(message = "Operation is required")
        private String operation;
        public Double getValue() { return value; }
        public void setValue(Double value) { this.value = value; }
        public String getOperation() { return operation; }
        public void setOperation(String operation) { this.operation = operation; }
    }

    public static class UnaryResponse {
        private final double value;
        private final String operation;
        private final double result;
        public UnaryResponse(double value, String operation, double result) {
            this.value = value; this.operation = operation; this.result = result;
        }
        public double getValue() { return value; }
        public String getOperation() { return operation; }
        public double getResult() { return result; }
    }
}
