package com.coding.practice.interview.questions;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Java 8+ program using Streams to find common elements in all three arrays
public class Test7 {
  public static void main(String[] args) {
    int a[] = {2, 3, 4, 9};
    int b[] = {2, 3, 7, 4};
    int c[] = {2, 3, 1};

    // common elements
    Set<Integer> common = IntStream.of(a).boxed().collect(Collectors.toSet());

    common.retainAll(IntStream.of(b).boxed().collect(Collectors.toSet()));
    common.retainAll(IntStream.of(c).boxed().collect(Collectors.toSet()));

    System.out.println("Common elements: " + common);

    Set<Integer> common1 = toSet(a);
    common1.retainAll(toSet(b));
    common1.retainAll(toSet(c));
    System.out.println(common1);
  }

  private static Set<Integer> toSet(int[] arr) {
    return IntStream.of(arr).boxed().collect(Collectors.toSet());
  }
}
