package com.coding.practice.codes.stringOperations;

import java.util.stream.Collectors;

public class ReverseStringJava8 {
  public static void main(String[] args) {
    String input = "OpenAI";
    String reversed = new StringBuilder(input).reverse().toString(); // Classic way (still valid)

    System.out.println("Reversed (Classic): " + reversed);

    // Java 8 Stream way
    String reversedStream =
        input
            .chars()
            .mapToObj(c -> (char) c)
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> {
                      java.util.Collections.reverse(list);
                      return list.stream().map(String::valueOf).collect(Collectors.joining());
                    }));

    System.out.println("Reversed (Stream): " + reversedStream);
  }
}
