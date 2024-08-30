package com.coding.practice.codes.level1.b_palindromeCheck;

import java.util.Arrays;

public class TraditionalApproachOptimised {
    public static void main(String[] args) {
        String[] valid = {"madam", "racecar", "121", "A man, a plan, a canal, Panama", "Able was I ere I saw Elba"};
        String[] invalid = {"hello", "apple", "12345", "Palindrome", "world"};

        // Merge valid and invalid arrays into input
        String[] input = Arrays.copyOf(valid, valid.length + invalid.length);
        System.arraycopy(invalid, 0, input, valid.length, invalid.length);

        // Print the result for each string in the input array
        for (String str : input) {
            System.out.println("Given string: " + str + " , is a palindrome: " + checkPalindrome(str));
        }
    }

    private static String normalizeString(String input) {
        // Remove spaces, punctuation, and make it lowercase
        return input.replaceAll("[\\W_]", "").toLowerCase();
    }

    private static boolean checkPalindrome(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        String normalized = normalizeString(str);
        int len = normalized.length();

        // Compare characters from start to end
        for (int i = 0; i < len / 2; i++) {
            if (normalized.charAt(i) != normalized.charAt(len - i - 1)) {
                return false;
            }
        }

        return true;
    }
}
