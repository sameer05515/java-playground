package com.coding.practice.interview.questions;

import java.util.List;
import java.util.stream.Collectors;

public class Test8 {
  public static void main(String[] args) {
    List<String> words =
        List.of(
            "Ram", "Shyam", "Mio", "Ram", "Shyam", "Mio", "Ram", "Shyam", "Mio", "Raman", "ggg");

    words.stream()
        .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
        .forEach((k, v) -> System.out.println(k + " : " + v));
  }
}
