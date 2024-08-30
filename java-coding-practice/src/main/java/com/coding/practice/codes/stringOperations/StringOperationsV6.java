package com.coding.practice.codes.stringOperations;

import java.util.Optional;

public class StringOperationsV6 {

    public static final StringOperation toUpperCase = input ->
            input.map(String::toUpperCase).orElse("Input string is null or empty");

    public static final StringOperation toLowerCase = input ->
            input.map(String::toLowerCase).orElse("Input string is null or empty");

    public static void main(String[] args) {
        try {
            Optional<String> sampleString = Optional.of("Premendra Kumar");
            System.out.println(toUpperCase.perform(sampleString));
            System.out.println(toLowerCase.perform(sampleString));
            System.out.println(toUpperCase.performWithTitle("Post Capitalization", sampleString));
            System.out.println(toLowerCase.performWithTitle("Post Making String in LowerCase", sampleString));
        } catch (InvalidInputException ex) {
            System.err.println("Error: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface StringOperation {
        String perform(Optional<String> input) throws InvalidInputException;

        default String performWithTitle(String title, Optional<String> input) throws InvalidInputException {
            if (title != null) {
                System.out.println(title);
            }
            if (input.isEmpty()) {
                throw new InvalidInputException("Input is missing or empty");
            }
            return perform(input);
        }
    }

    private static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }
}
