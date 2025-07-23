package com.coding.practice.interview.questions.ankith;

import java.util.*;
import java.util.stream.*;

public class StudentAverage {
  public static void main(String[] args) {
    String[] input = {
      "Akash 78",
      "Bharat 89",
      "Akash 90",
      "Akash 70",
      "Chetan 67",
      "Bharat 88",
      "Bharat 94",
      "Chetan 84"
    };

    // Map<String, Double> averageMap =
    Arrays.stream(input)
        .map(line -> line.split(" "))
        .collect(
            Collectors.groupingBy(
                arr -> arr[0], Collectors.averagingInt(arr -> Integer.parseInt(arr[1]))))
        .forEach((name, avg) -> System.out.println(name + " : " + String.format("%.2f", avg)));
  }
}
