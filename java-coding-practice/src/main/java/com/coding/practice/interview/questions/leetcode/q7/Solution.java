package com.coding.practice.interview.questions.leetcode.q7;

public class Solution {

    public String longestPalindrome(String s) {
//        System.out.println("\n------------------\n"+s);
        if (s == null || s.isEmpty()) return "";

        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i); // Odd-length palindrome
            int len2 = expandAroundCenter(s, i, i + 1); // Even-length palindrome
            int len = Math.max(len1, len2);

            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }

            System.out.printf("i: %s, character: %s, len1: %s, len2: %s, len: %s, start: %s, end: %s, palindrome: %s   %n",
                    i, s.charAt(i), len1, len2, len, start, end, s.substring(start, end + 1));
        }

        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        System.out.printf("\n-------- %s -----------\n",
                (left==right)?"Odd-length check":"Even-length check"
                );
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            System.out.printf("left: %s, right: %s,s.charAt(left): %s, s.charAt(right): %s,  %n",left, right,s.charAt(left),s.charAt(right));
            left--;
            right++;
        }
        return right - left - 1; // Length of the palindrome
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test cases
        System.out.println("Longest Palindromic Substring: " + sol.longestPalindrome("babad")); // Output: "bab" or "aba"
//        System.out.println("Longest Palindromic Substring: " + sol.longestPalindrome("cbbd")); // Output: "bb"
//        System.out.println("Longest Palindromic Substring: " + sol.longestPalindrome("a")); // Output: "a"
//        System.out.println("Longest Palindromic Substring: " + sol.longestPalindrome("ac")); // Output: "a" or "c"
    }
}
