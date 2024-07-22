package com.coding.practice.codes.stringOperations;

import com.coding.practice.codes.stringOperations.common.InvalidInputException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StringOperationsV7 {

    private static final StringOperation toUpperCase = input ->
            input.map(String::toUpperCase).map(s -> "'" + s + "'")
                    .orElseThrow(() -> new InvalidInputException("Input string is null or empty"));
    private static final StringOperation toLowerCase = input ->
            input.map(String::toLowerCase).map(s -> "'" + s + "'")
                    .orElseThrow(() -> new InvalidInputException("Input string is null or empty"));

    private static final StringOperation noOfCharacter = input ->
            input.map(s -> String.valueOf(s.length())).map(s -> "'" + s + "'")
                    .orElseThrow(() -> new InvalidInputException("Input string is null or empty"));

    private static final StringOperation reversString = input ->
            input.map(s -> s.chars()
                            .mapToObj(c -> String.valueOf((char) c))
                            .reduce("", (acc, c) -> c + acc))
                    .orElseThrow(() -> new InvalidInputException("Input string is null or empty"));

    private static final StringOperation replaceAWithZ = input ->
            input.map(s -> s.replace("a", "z")).map(s -> "'" + s + "'")
                    .orElseThrow(() -> new InvalidInputException("Input string is null or empty"));

    private static final StringOperation extractIndex1To3 = input ->
            input.map(s -> s.length() >= 3 ? s.substring(1, 3) : s)
                    .map(s -> "'" + s + "'")
                    .orElseThrow(() -> new InvalidInputException("Input string is null or empty"));

    private static final StringOperation appendString = input -> input.map(s -> s + "ef")
            .map(s -> "'" + s + "'")
            .orElseThrow(() -> new InvalidInputException("Input string is null or empty"));

    private static final StringOperation getFrequency = input -> input.map(s -> {
                StringBuilder res = new StringBuilder();
                char[] chArr = s.trim().toCharArray();
                Map<Character, Integer> characterIntegerMap = new HashMap<>();

                for (char c : chArr) {
                    if (Character.isWhitespace(c)) continue;
                    characterIntegerMap.merge(c, 1, Integer::sum);
                }

                System.out.println(characterIntegerMap);

                res.append("{");
                characterIntegerMap.forEach((c, count) -> res.append("'").append(c).append("' : '").append(count).append("', "));
                if (res.length() > 1) res.setLength(res.length() - 2); // Remove the trailing comma and space
                res.append("}");

                return res.toString();
            })
            .map(s -> "'" + s + "'")
            .orElseThrow(() -> new InvalidInputException("Input string is null or empty"));


//    private static final StringOperation palindromeResult = input ->
//            input.map(s -> {
//                        String reversed = reversString.perform(Optional.of(s));
//                        return s.equals(reversed) ? "is a palindrome" : "is not a palindrome";
//                    })
//                    .orElseThrow(() -> new InvalidInputException("Input string is null or empty"));

    private static final StringOperation palindromeResult = input ->
            input.map(s -> {
                        String res = "not";
                        try {
                            if (s.equals(reversString.perform(Optional.of(s)))) {
                                return "";
                            }
                        } catch (Exception e) {
                        }
                        return res;
                    })
                    .orElseThrow(() -> new InvalidInputException("Input string is null or empty"));

    private static void executeTest(String str) throws InvalidInputException {
//        Optional<String> sampleString = Optional.ofNullable(str);
//        System.out.println(toUpperCase.perform(sampleString));
//        System.out.println(toLowerCase.perform(sampleString));
        // Uncomment if you want to test performWithTitle
        System.out.println(toUpperCase.performWithTitle("Post Capitalization: ", str));
        System.out.println(toLowerCase.performWithTitle("Change the entire string to lowercase after capitalization: ", str));

        System.out.println(noOfCharacter.performWithTitle("Calculate the number of characters present in the string: ", str));
        System.out.println(reversString.performWithTitle("Reverse the order of characters in the string: ", str));

        System.out.println(str.trim() + " is " + (palindromeResult.perform(Optional.ofNullable(str))) + " a palindrome");
        System.out.println("Replace `'a'` with `'z'` : " + replaceAWithZ.perform(Optional.ofNullable(str)));
        System.out.println("Extract from index 1 to 3 in `\"abcd\"` → `\"bc\"` : " + extractIndex1To3.perform(Optional.ofNullable(str)));
        System.out.println("Append another string to the original string. : " + appendString.perform(Optional.ofNullable(str)));
        System.out.println("Determine the frequency of each character in the string. : "+ getFrequency.perform(Optional.ofNullable(str)));
    }




    public static void main(String[] args) {
        String[] inputs = {"Premendra Kumar", "abba", null, ""};
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
