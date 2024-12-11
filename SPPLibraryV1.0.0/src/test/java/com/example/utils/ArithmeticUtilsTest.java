package com.example.utils;

import static org.junit.jupiter.api.Assertions.*;

import com.spp.demo.utils.ArithmeticUtils;
import org.junit.jupiter.api.Test;

public class ArithmeticUtilsTest {

  @Test
  public void testAdd() {
    assertEquals(5, ArithmeticUtils.add(2, 3));
  }

  @Test
  public void testSubtract() {
    assertEquals(1, ArithmeticUtils.subtract(3, 2));
  }

  @Test
  public void testMultiply() {
    assertEquals(6, ArithmeticUtils.multiply(2, 3));
  }

  @Test
  public void testDivide() {
    assertEquals(2, ArithmeticUtils.divide(6, 3));
  }

  @Test
  public void testDivideByZero() {
    assertThrows(IllegalArgumentException.class, () -> ArithmeticUtils.divide(6, 0));
  }

  @Test
  public void testNegativeInputs() {
    assertThrows(IllegalArgumentException.class, () -> ArithmeticUtils.add(-1, 5));
  }
}
