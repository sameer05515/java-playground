package com.example.calculator.service;

import com.example.calculator.exception.CalculationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {
    private CalculatorService service;

    @BeforeEach
    void setUp() {
        service = new CalculatorService();
    }

    @Test void addsNumbers() {
        assertEquals(7.0, service.calculate(3, 4, "add"));
    }

    @Test void subtractsNumbers() {
        assertEquals(2.0, service.calculate(5, 3, "subtract"));
    }

    @Test void multipliesNumbers() {
        assertEquals(12.0, service.calculate(3, 4, "multiply"));
    }

    @Test void dividesNumbers() {
        assertEquals(2.5, service.calculate(5, 2, "divide"));
    }

    @Test void divisionByZeroThrows() {
        assertThrows(CalculationException.class, () -> service.calculate(5, 0, "divide"));
    }

    @Test void calculatesSquareRoot() {
        assertEquals(4.0, service.calculateUnary(16, "sqrt"));
    }

    @Test void trigonometryUsesDegrees() {
        assertEquals(1.0, service.calculateUnary(90, "sin"), 1e-10);
    }

    @Test void factorialWorks() {
        assertEquals(120.0, service.calculateUnary(5, "factorial"));
    }

    @Test void negativeSquareRootThrows() {
        assertThrows(CalculationException.class, () -> service.calculateUnary(-1, "sqrt"));
    }

    @Test void rejectsUnknownOperation() {
        assertThrows(CalculationException.class, () -> service.calculate(1, 2, "unknown"));
    }
}
