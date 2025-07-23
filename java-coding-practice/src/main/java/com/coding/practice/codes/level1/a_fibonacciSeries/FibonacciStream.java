package com.coding.practice.codes.level1.a_fibonacciSeries;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FibonacciStream {
  public static List<Integer> generateFibonacci(int length) {
    return Stream.iterate(new int[] {0, 1}, f -> new int[] {f[1], f[0] + f[1]})
        .limit(length)
        .map(f -> f[0])
        .collect(Collectors.toList());
  }

  public static void main(String[] args) {
    List<Integer> fib = generateFibonacci(1);
    System.out.println(fib);
  }
}
