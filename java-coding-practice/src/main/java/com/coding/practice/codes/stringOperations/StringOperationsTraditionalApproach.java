package com.coding.practice.codes.stringOperations;

import com.coding.practice.codes.common.InvalidInputException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StringOperationsTraditionalApproach {

    public static void main(String[] args) {
        String[] inputs = {"Premendra Kumar", "abcd", "abba", "abc", null, "", "        ",
                "Hello world\nThis is a sample text\nJava 8 is powerful",
                "A man, a plan, a canal, Panama", "Able was I ere I saw Elba"};
        for (String input : inputs) {
            try {
                System.out.println("=====================\nStarting test: '" + input + "'");
                executeTest(input);
                System.out.println("\nEnding test: '" + input + "' \n=====================");
            } catch (InvalidInputException e) {
                System.out.println("Ending test for Input: '" + input + "', with Error: " + e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void executeTest(String input) throws InvalidInputException {
        if (input == null || input.isEmpty() || input.trim().isEmpty()) {
            throw new InvalidInputException("Input is missing or empty");
        }

        String transformedOutput = input.trim().toUpperCase();
        System.out.println("Post capitaliation: " + transformedOutput);

        transformedOutput = transformedOutput.toLowerCase();
        System.out.println("Change the entire string to lowercase after capitalization: " + transformedOutput);

//        transformedOutput= transformedOutput
        System.out.println("Calculate the number of characters present in the string: " + transformedOutput.length());

        String reverse = reverse(transformedOutput);
        System.out.println("Reverse the order of characters in the string: " + reverse);

        System.out.println(input.trim() + " is " + (transformedOutput.equals(reverse) ? "" : "not") + " a palindrome");

        System.out.println("Replace `'a'` with `'z'` in `\"abcd\"` → `\"zbcd\"` : " + transformedOutput.replace("a", "z"));

        System.out.println("Extract from index 1 to 3 in `\"abcd\"` → `\"bc\"` : " + transformedOutput.substring(1, 3));

        System.out.println("Append another string to the original string.\n" +
                " - Example: `\"abcd\"` + `\"ef\"` → `\"abcdef\"` : " + (transformedOutput + "ef"));

        System.out.println("Determine the frequency of each character in the string. : " + getFrequency(transformedOutput));

        System.out.println("Split lines by new-line character and return total no of lines: " + getTotalNoOfLines(input));

        System.out.println("method that removes spaces, punctuation, and capitalization from a string: " + normalizeString(input));

        String s1 = "listen";
        String s2 = "silent";

        if (isAnagramOld(s1, s2)) {
            System.out.println(s1 + " and " + s2 + " are anagrams.");
        } else {
            System.out.println(s1 + " and " + s2 + " are not anagrams.");
        }

        System.out.println("isAnagramFromInput: "+s1 + " and " + s2 + " are" +
                (isAnagramFromInput(s1+","+s2)?"":" not") +
                " anagrams.");
        System.out.println("isAnagramFromInput: "+s1  + " are" +
                (isAnagramFromInput(s1)?"":" not") +
                " anagrams.");
//        System.out.println("To check anagram: ");
    }

    //
    public static boolean isAnagramFromInput(String str) {
        return Optional.ofNullable(str)
                .filter(s -> !s.trim().isEmpty() && s.split(",").length == 2)
                .map(s -> Arrays.asList(s.split(",")))
                .map(list -> isAnagram(list.get(0), list.get(1)))
                .orElse(false);
//                .orElseThrow(() -> new RuntimeException("Null or improperly formatted input string"));
    }

    public static boolean isAnagram(String s1, String s2) {
        return (s1 != null && s2 != null) &&
                s1.trim().length() == s2.trim().length() &&
                s1.chars().mapToObj(c -> (char) c)
                        .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                        .equals(s2.chars().mapToObj(c -> (char) c)
                                .collect(Collectors.groupingBy(c -> c, Collectors.counting())));
    }

    public static boolean isAnagramOld(String s1, String s2) {
        if (s1 != null && s2 != null) {
            s1 = s1.trim();
            s2 = s2.trim();

            // If lengths are different, they can't be anagrams
            if (s1.length() != s2.length()) {
                return false;
            }

            // Creating character count maps for both strings
            Map<Character, Long> s1Map = s1.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

            Map<Character, Long> s2Map = s2.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

            // Compare both maps
            return s1Map.equals(s2Map);
        } else {
            return false;
        }
    }

    private static String normalizeString(String input) {
        // Remove spaces, punctuation, and make it lowercase
        return input.replaceAll("[\\W_]", "").toLowerCase();
    }

    private static int getTotalNoOfLines(String input) throws InvalidInputException {
        if (input == null || input.isEmpty() || input.trim().isEmpty()) {
            throw new InvalidInputException("Input is missing or empty");
        }

        String[] lines = input.split("\\n");
        if (lines != null && lines.length > 0) {
            return lines.length;
        } else {
            return 0;
        }
    }

    private static String getFrequency(String input) throws InvalidInputException {
        if (input == null || input.isEmpty() || input.trim().isEmpty()) {
            throw new InvalidInputException("Input is missing or empty");
        }
        StringBuffer res = new StringBuffer();
        char[] chArr = input.trim().toCharArray();
        Map<Character, Integer> characterIntegerMap = new HashMap<>();

        for (int i = 0; i <= chArr.length - 1; i++) {
            if (chArr[i] == ' ') continue;
            if (characterIntegerMap.containsKey(chArr[i])) {
                characterIntegerMap.put(chArr[i], characterIntegerMap.get(chArr[i]) + 1);
            } else {
                characterIntegerMap.put(chArr[i], 1);
            }
        }

        System.out.println(characterIntegerMap);

        res.append("{");
        for (Character c : characterIntegerMap.keySet()) {
            res.append("'").append(c).append("' : '").append(characterIntegerMap.get(c)).append("',");
        }
        res.append("}");

        return res.toString();
    }

    private static String getFrequencyFuture(String input) throws InvalidInputException {
        if (input == null || input.isEmpty() || input.trim().isEmpty()) {
            throw new InvalidInputException("Input is missing or empty");
        }

        StringBuilder res = new StringBuilder();
        char[] chArr = input.trim().toCharArray();
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
    }

    private static String reverse(String input) throws InvalidInputException {
        if (input == null || input.isEmpty() || input.trim().isEmpty()) {
            throw new InvalidInputException("Input is missing or empty");
        }

        char[] chArr = input.trim().toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = chArr.length - 1; i >= 0; i--) {
            stringBuffer.append(chArr[i]);
        }

        return stringBuffer.toString();

    }
}
