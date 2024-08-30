package com.coding.practice.codes.collectors_demonstration;

import java.util.*;
import java.util.stream.Collectors;

public class CollectorsExampleCountingElements {
    public static void main(String[] args) {
        List<String> items = Arrays.asList("Apple", "Banana", "Apple", "Orange", "Banana", "Apple");

        Map<String, Long> itemCount = items.stream()
            .collect(Collectors.groupingBy(item -> item, Collectors.counting()));

        System.out.println(itemCount);
    }
}