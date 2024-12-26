package com.coding.practice.codes.level1.a_fibonacciSeries;

import java.util.ArrayList;
import java.util.List;

public class TraditionalApproachOptimised {
  public static void main(String[] args) {
    List<Integer> series = getFibonacciSeries(0);
    System.out.println(series);
  }

  private static List<Integer> getFibonacciSeries(int seriesLength) {
    List<Integer> result = new ArrayList<>();

    if (seriesLength < 1) {
      System.out.println("Invalid input: " + seriesLength);
      return result;
    }

    // Add initial two Fibonacci numbers
    result.add(0);
    if (seriesLength > 1) {
      result.add(1);
    }

    // Generate Fibonacci series
    for (int i = 2; i < seriesLength; i++) {
      int next = result.get(i - 1) + result.get(i - 2);
      result.add(next);
    }

    return result;
  }
}
