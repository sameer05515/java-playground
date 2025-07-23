package com.prem.junit.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CsvSourceExampleTest {

  @ParameterizedTest
  @CsvSource({"2, 3, 5", "10, 20, 30", "-1, 1, 0"})
  void testAddition(int a, int b, int expectedSum) {
    assertEquals(expectedSum, a + b);
  }
}
