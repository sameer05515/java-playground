

package com.coding.practice.codes.level1.b_palindromeCheck;

import java.util.Arrays;
import java.util.Optional;

public class Java8ApproachOptimized {
    public static void main(String[] args) {
        String[] valid = {"madam", "racecar", "121", "A man, a plan, a canal, Panama", "Able was I ere I saw Elba"};
        String[] invalid = {"hello", "apple", "12345", "Palindrome", "world"};

        // Merge valid and invalid arrays into input
        String[] input = Arrays.copyOf(valid, valid.length + invalid.length);
        System.arraycopy(invalid, 0, input, valid.length, invalid.length);

        // Print the result for each string in the input array using streams
        Arrays.stream(input)
                .forEach(str -> System.out.println("Given string: " + str + " , is a palindrome: " + checkPalindrome(str)));
    }

    private static boolean checkPalindrome(String str) {
        return Optional.ofNullable(str)
                .map(s -> s.replaceAll("[\\W_]", "").toLowerCase()) // Normalize string
                .map(normalized -> new StringBuilder(normalized).reverse().toString().equals(normalized)) // Compare with reverse
                .orElse(false); // Handle null cases
    }
}
