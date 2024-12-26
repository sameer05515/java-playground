package com.coding.practice.interview.questions;

import java.util.*;
import java.util.stream.Collectors;

public class Test6 {
  public static void main(String[] args) {
    List<String> words =
        List.of("apple", "banana", "kiwi", "orange", "apple", "banana", "avocado", "avocado");

    // Count frequency of each word
    Map<String, Long> wordCount =
        words.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));

    // Group words by frequency (count -> list of words)
    Map<Long, List<String>> groupedByCount = new TreeMap<>();

    wordCount.forEach(
        (word, count) -> {
          groupedByCount.computeIfAbsent(count, x -> new ArrayList<>()).add(word);
        });

    // Sort words inside each frequency group
    groupedByCount.forEach((k, v) -> Collections.sort(v));

    // Print final result
    System.out.println(groupedByCount);
  }
}
