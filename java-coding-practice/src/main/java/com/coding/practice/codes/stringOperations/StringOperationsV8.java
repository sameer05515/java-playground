package com.coding.practice.codes.stringOperations;

import com.coding.practice.codes.common.InvalidInputException;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class StringOperationsV8 {

    // Supplier to create InvalidInputException with a consistent message
    private static final Supplier<InvalidInputException> invalidInputExceptionSupplier =
            () -> new InvalidInputException("Input string is null or empty");

    // Define StringOperations as functional interfaces for various string manipulations
    private static final StringOperation toUpperCase = input ->
            input.map(String::toUpperCase)
                    .map(s -> "'" + s + "'")
                    .orElseThrow(invalidInputExceptionSupplier);

    private static final StringOperation toLowerCase = input ->
            input.map(String::toLowerCase)
                    .map(s -> "'" + s + "'")
                    .orElseThrow(invalidInputExceptionSupplier);

    private static final StringOperation noOfCharacter = input ->
            input.map(s -> String.valueOf(s.length()))
                    .map(s -> "'" + s + "'")
                    .orElseThrow(invalidInputExceptionSupplier);

    private static final StringOperation reverseString = input ->
            input.map(s -> s.chars()
                            .mapToObj(c -> String.valueOf((char) c))
                            .reduce("", (acc, c) -> c + acc))
                    .orElseThrow(invalidInputExceptionSupplier);

    private static final StringOperation replaceAWithZ = input ->
            input.map(s -> s.replace("a", "z"))
                    .map(s -> "'" + s + "'")
                    .orElseThrow(invalidInputExceptionSupplier);

    private static final StringOperation extractIndex1To3 = input ->
            input.map(s -> s.length() >= 3 ? s.substring(1, 3) : s)
                    .map(s -> "'" + s + "'")
                    .orElseThrow(invalidInputExceptionSupplier);

    private static final StringOperation appendString = input ->
            input.map(s -> s + "ef")
                    .map(s -> "'" + s + "'")
                    .orElseThrow(invalidInputExceptionSupplier);

    private static final StringOperation getFrequency = input -> input.map(s -> {
                // Create a frequency map using Collectors
                Map<Character, Long> frequencyMap = s.trim().chars()
                        .filter(c -> !Character.isWhitespace(c))
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

                // Format the frequency map as a string
                String result = frequencyMap.entrySet().stream()
                        .map(entry -> "'" + entry.getKey() + "' : '" + entry.getValue() + "'")
                        .collect(Collectors.joining(", ", "{", "}"));

                return "'" + result + "'";
            })
            .orElseThrow(invalidInputExceptionSupplier);

    private static final StringOperation palindromeResult = input ->
            input.map(s -> {
                        char[] characters = s.toCharArray();
                        int length = characters.length;
                        for (int i = 0; i < length / 2; i++) {
                            if (characters[i] != characters[length - 1 - i]) {
                                return "no";
                            }
                        }
                        return "yes";
                    })
                    .orElseThrow(invalidInputExceptionSupplier);

    private static final StringOperation getTotalLineNumbers = input ->
            input.map(s -> s.split("\\n").length + "")
                    .orElseThrow(invalidInputExceptionSupplier);

    private static final StringOperation normalizeString = input ->
            input.map(s -> s.replaceAll("[\\W_]", "").toLowerCase())
                    .orElseThrow(invalidInputExceptionSupplier);

    private static final StringOperation isAnagram = input -> input
            .filter(s -> !s.trim().isEmpty() && s.split(",").length == 2)
            .map(s -> Arrays.asList(s.split(",")))
            .map(list -> isAnagram(list.get(0), list.get(1)) ? " are anagram" : "are not anagram")
            .orElseThrow(invalidInputExceptionSupplier);

    public static boolean isAnagram(String s1, String s2) {
        return (s1 != null && s2 != null) &&
                s1.trim().length() == s2.trim().length() &&
                s1.chars().mapToObj(c -> (char) c)
                        .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                        .equals(s2.chars().mapToObj(c -> (char) c)
                                .collect(Collectors.groupingBy(c -> c, Collectors.counting())));
    }

    private static void executeTest(String str) throws InvalidInputException {
        Optional<String> sampleString = Optional.ofNullable(str);

        System.out.println(toUpperCase.performWithTitle("Post Capitalization: ", str));
        System.out.println(toLowerCase.performWithTitle("Change to lowercase: ", str));
        System.out.println(noOfCharacter.performWithTitle("Number of characters: ", str));
        System.out.println(reverseString.performWithTitle("Reversed string: ", str));
        System.out.println("Is palindrome: " + palindromeResult.perform(Optional.ofNullable(str)));
        System.out.println("Replace 'a' with 'z': " + replaceAWithZ.perform(Optional.ofNullable(str)));
        System.out.println("Extract substring 1-3: " + extractIndex1To3.perform(Optional.ofNullable(str)));
        System.out.println("Append string: " + appendString.perform(Optional.ofNullable(str)));
        System.out.println("Character frequency: " + getFrequency.perform(Optional.ofNullable(str)));
        System.out.println(getTotalLineNumbers.performWithTitle(
                "Split lines by new-line character and return total no of lines: ", str));
        System.out.println(normalizeString.performWithTitle(
                "method that removes spaces, punctuation, and capitalization from a string: ", str));

        System.out.println(isAnagram.performWithTitle("listen and silent ", "silent,listen"));
        System.out.println(isAnagram.performWithTitle("earth and heaven ", "earth,heaven"));
    }

    public static void main(String[] args) {
        String[] inputs = {"Premendra Kumar", "abba", "Hello world\nThis is a sample text\nJava 8 is powerful", null, "",
                "A man, a plan, a canal, Panama", "Able was I ere I saw Elba"};
        for (String input : inputs) {
            try {
                System.out.println("=====================\nStarting test: '" + input + "'");
                executeTest(input);
                System.out.println("\nEnding test: '" + input + "' \n=====================");
            } catch (InvalidInputException ex) {
                System.out.println("\nEnding test for Input: '" + input + "', with Error: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FunctionalInterface
    private interface StringOperation {
        String perform(Optional<String> input) throws InvalidInputException;

        default String performWithTitle(String title, String input) throws InvalidInputException {
            if (title != null) {
                System.out.print(title + "\t\t");
            }
            if (input == null || input.isEmpty()) {
                throw new InvalidInputException("Input is missing or empty");
            }
            return perform(Optional.of(input));
        }
    }
}
