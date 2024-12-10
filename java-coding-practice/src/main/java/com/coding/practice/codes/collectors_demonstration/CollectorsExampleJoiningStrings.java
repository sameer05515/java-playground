package com.coding.practice.codes.collectors_demonstration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectorsExampleJoiningStrings {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Jack");

        String joinedNames = names.stream()
                .collect(Collectors.joining(" ### "));

        System.out.println(joinedNames);
    }
}
