package com.coding.practice.codes.level1.a_fibonacciSeries;

import java.util.ArrayList;
import java.util.List;

public class FibonacciGenerator {
  public static List<Integer> generateFibonacci(int length) {
    List<Integer> series = new ArrayList<>();
    if (length <= 0) return series;
    series.add(0);
    if (length == 1) return series;
    series.add(1);

    for (int i = 2; i < length; i++) {
      int next = series.get(i - 1) + series.get(i - 2);
      series.add(next);
    }
    return series;
  }

  public static void main(String[] args) {
    List<Integer> fib = generateFibonacci(0);
    System.out.println(fib);
  }
}
