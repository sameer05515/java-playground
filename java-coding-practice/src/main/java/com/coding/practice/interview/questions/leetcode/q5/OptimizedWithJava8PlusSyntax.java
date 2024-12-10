//package com.coding.practice.interview.questions.leetcode.q5;
//
//public class OptimizedWithJava8PlusSyntax {
//}

package com.coding.practice.interview.questions.leetcode.q5;

import java.util.HashSet;
import java.util.stream.Stream;

public class OptimizedWithJava8PlusSyntax {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;

        int resultLength = 0; // To store the length of the longest substring
        int start = 0; // Start pointer of the sliding window
        HashSet<Character> charSet = new HashSet<>();

        // Sliding window loop
        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);

            // Remove characters until the duplicate is removed
            while (charSet.contains(c)) {
                charSet.remove(s.charAt(start++));
            }

            // Add the current character to the set
            charSet.add(c);

            // Update the maximum result length
            resultLength = Math.max(resultLength, end - start + 1);
        }

        return resultLength;
    }

    public static void main(String[] args) {
        OptimizedWithJava8PlusSyntax sol = new OptimizedWithJava8PlusSyntax();
        String[] arr = new String[]{
                "dvdf",
                "au",
                "abcabcbb",
                "bbbbb",
                "pwwkew"
        };

        // Using Java 8 Stream API for cleaner iteration
        Stream.of(arr).forEach(s -> {
            System.out.println("\n----------------------");
            System.out.println("Input: " + s);
            System.out.println("Longest Substring Length: " + sol.lengthOfLongestSubstring(s));
        });
    }
}

