package com.coding.practice.interview.questions.leetcode.q5;

import java.util.HashSet;

public class Optimized {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;

        int resultLength = 0; // To store the length of the longest substring
        int start = 0; // Start pointer of the sliding window
        String currentLongestStr = ""; // To store the current longest substring

        // Use a HashSet to store the unique characters in the current substring
        HashSet<Character> charSet = new HashSet<>();
        StringBuilder subStr = new StringBuilder();

        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            // If the character is already in the HashSet, move the start pointer forward
            while (charSet.contains(c)) {
                charSet.remove(s.charAt(start));
                start++;
            }

            // Add the current character to the HashSet
            charSet.add(c);
            subStr.append(c);

            // Update the current longest substring
            if (resultLength < end - start + 1) {
                currentLongestStr = s.substring(start, end + 1);
            }

            resultLength = Math.max(resultLength, end - start + 1);

//            System.out.printf("LastLongestStr: %s, Index: %d, Character: %s, SubStr: %s %n",
//                    currentLongestStr, end, c, subStr.toString());
        }

//        System.out.println("currentLongestStr: " + currentLongestStr);
        return resultLength;
    }

    public static void main(String[] args) {
        Optimized sol = new Optimized();
        String[] arr = new String[]{
                "dvdf",
                "au",
                "abcabcbb",
                "bbbbb",
                "pwwkew"
        };
        for (String s : arr) {
            System.out.println("\n\n----------------------\n" + s + "\n" + sol.lengthOfLongestSubstring(s));
        }
    }
}
