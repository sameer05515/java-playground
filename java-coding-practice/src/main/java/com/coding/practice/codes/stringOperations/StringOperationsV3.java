package com.coding.practice.codes.stringOperations;

public class StringOperationsV3 {

    public static final PerformStringOperations toUpperCase = (String str) -> {
        if (str == null || str.isEmpty()) {
            throw new InvalidInputException("Input string is null or empty");
        }
        return str.toUpperCase();
    };

    public static final PerformStringOperations toLowerCase = (String str) -> {
        if (str == null || str.isEmpty()) {
            throw new InvalidInputException("Input string is null or empty");
        }
        return str.toLowerCase();
    };

    public static void main(String[] args) {
        try {
            String sampleString = "Premendra Kumar";
            System.out.println(toUpperCase.perform(sampleString));
            System.out.println(toLowerCase.perform(sampleString));
            System.out.println(toUpperCase.performWithDefaultMethod("Post Capitalization", sampleString));
            System.out.println(toLowerCase.performWithDefaultMethod("Post Making String in LowerCase", sampleString));
        } catch (InvalidInputException ex) {
            System.err.println("Error: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private interface PerformStringOperations {
        String perform(String input) throws InvalidInputException;

        default String performWithDefaultMethod(String title, String input) throws InvalidInputException {
            if (title != null) {
                System.out.println(title);
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


