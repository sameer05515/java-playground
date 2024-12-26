package com.prem.junit.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ValueSourceExampleTest {

  @ParameterizedTest
  @ValueSource(ints = {2, 4, 6, 8, 10})
  void testEvenNumbers(int number) {
    assertTrue(number % 2 == 0, number + " is not even");
  }
}
