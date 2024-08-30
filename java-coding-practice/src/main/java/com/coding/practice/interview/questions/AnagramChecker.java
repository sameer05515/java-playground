package com.coding.practice.interview.questions;

import com.coding.practice.codes.common.InvalidInputException;
import com.coding.practice.codes.common.TwoValuesOperation;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnagramChecker {

    public static boolean isAnagramFromInput(String str) {
        return Optional.ofNullable(str)
                .filter(s -> !s.trim().isEmpty() && s.split(",").length == 2)
                .map(s -> Arrays.asList(s.split(",")))
                .map(list -> isAnagram(list.get(0), list.get(1)))
                .orElseThrow(() -> new RuntimeException("Null or improperly formatted input string"));
    }

    public static boolean isAnagram(String s1, String s2) {
        return (s1 != null && s2 != null) &&
                s1.trim().length() == s2.trim().length() &&
                s1.chars().mapToObj(c -> (char) c)
                        .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                        .equals(s2.chars().mapToObj(c -> (char) c)
                                .collect(Collectors.groupingBy(c -> c, Collectors.counting())));
    }

    private static TwoValuesOperation<String, Boolean> isAnagram = (String s1, String s2) ->
            (s1 != null && s2 != null) &&
                    s1.trim().length() == s2.trim().length() &&
                    s1.chars().mapToObj(c -> (char) c)
                            .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                            .equals(s2.chars().mapToObj(c -> (char) c)
                                    .collect(Collectors.groupingBy(c -> c, Collectors.counting())));

    public static void main(String[] args) {
        String input = "listen,silent";

        try {
            System.out.println(isAnagramFromInput(input) ? "The strings are anagrams." : "The strings are not anagrams.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        String arr[] = input.split(",");
        try {
            System.out.println(isAnagram.perform(arr[0], arr[1]) ? "The strings are anagrams." : "The strings are not anagrams.");
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }


}
