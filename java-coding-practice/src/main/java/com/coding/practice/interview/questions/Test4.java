package com.coding.practice.interview.questions;

import java.util.Arrays;
import java.util.stream.Collectors;

// count month name , group by first letter (lowercase)
public class Test4 {
  public static void main(String[] args) {
    Arrays.stream(
            new String[] {
              "january",
              "february",
              "march",
              "april",
              "may",
              "june",
              "july",
              "august",
              "september",
              "october",
              "november",
              "december"
            })
        .collect(Collectors.groupingBy(s -> s.substring(0, 1).toLowerCase(), Collectors.counting()))
        .forEach((k, v) -> System.out.println(k + " : " + v));
  }
}
