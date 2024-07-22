package com.coding.practice.codes.stringOperations;

public class StringOperationsV5 {

    public static final StringOperation toUpperCase = String::toUpperCase;
    public static final StringOperation toLowerCase = String::toLowerCase;

    public static void main(String[] args) {
        try {
            String sampleString = "Premendra Kumar";
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
        String perform(String input) throws InvalidInputException;

        default String performWithTitle(String title, String input) throws InvalidInputException {
            if (title != null) {
                System.out.println(title);
            }
            if (input == null || input.isEmpty()) {
                throw new InvalidInputException("Input string is null or empty");
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
