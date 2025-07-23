package com.prem.junit.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CalculatorTest {

  Calculator calc = new Calculator();

  @Test
  void testAdd() {
    assertEquals(7, calc.add(3, 4));
  }

  @Test
  void testSubtract() {
    assertEquals(1, calc.subtract(5, 4));
  }

  @Test
  void testMultiply() {
    assertEquals(12, calc.multiply(3, 4));
  }

  @Test
  void testException() {
    int[] numbers = null;

    assertThrows(
        NullPointerException.class,
        () -> {
          Arrays.sort(numbers);
        });
  }

  @Test
  void testSort_Performance() {
    assertTimeout(
        Duration.ofMillis(100),
        () -> {
          int[] array = {12, 23, 4};
          for (int i = 0; i < 100000; i++) {
            array[0] = i;
            Arrays.sort(array);
          }
        });
  }
}
