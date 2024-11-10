package com.coding.practice.interview.questions.leetcode.q9;

public class Solution {
    public int myAtoi(String s) {
        if (s == null || s.isEmpty()) return 0;

        int res = 0;

        int ptr = 0;
        char currentChar = s.charAt(0);
        while (currentChar == ' ' && ptr < s.length() - 1) {
            ptr++;
            currentChar = s.charAt(ptr);
        }

        int multipler = 1;

        if (currentChar == '-') {
            multipler = -1;
            ptr++;
            if (ptr < s.length()) {
                currentChar = s.charAt(ptr);
            }
        } else if (currentChar == '+') {
            multipler = 1;
            ptr++;
            if (ptr < s.length()) {
                currentChar = s.charAt(ptr);
            }
        }
//        else if (/**currentChar>='0' && currentChar<='9'*/ !Character.isDigit(currentChar)) {
//            return 0;
//        }
        StringBuilder sb = new StringBuilder();
        while (Character.isDigit(currentChar) && ptr < s.length()) {
            sb.append(currentChar);
            ptr++;
            if (ptr < s.length()) {
                currentChar = s.charAt(ptr);
            }
        }
//        System.out.println(sb);
        while (!sb.isEmpty() && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
//            System.out.println(sb);
        }

        if (sb.isEmpty()) return 0;
        System.out.printf("refined sb: %s, multipler: %s %n", sb, multipler);

        while (!sb.isEmpty()) {
            int digit = Character.getNumericValue(sb.charAt(0));
//            int resultant = res * multipler;

//            if (resultant > (Integer.MAX_VALUE / 10) || (resultant == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
//                return Integer.MAX_VALUE; // Overflow for positive numbers
//            }
//            if (resultant < (Integer.MIN_VALUE / 10) || (resultant == Integer.MIN_VALUE / 10 && digit < Integer.MIN_VALUE % 10)) {
//                return Integer.MIN_VALUE; // Underflow for negative numbers
//            }
            // Check for overflow/underflow before multiplying or adding
            if (res > (Integer.MAX_VALUE - digit) / 10) {
                return multipler == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            res = res * 10 + digit;

            sb.deleteCharAt(0);
        }
        return res * multipler;
    }

    public static void main(String[] args) {
        String[] arr = new String[]{
                "42",
                "   -042",
                "1337c0d3",
                "0-1",
                "words and 987",
                "+",
                "-2147483649"
        };

        Solution sol = new Solution();

        for (String s : arr) {
            run(sol, s);
        }
    }

    public static void run(Solution sol, String s) {
        System.out.println("-----------\nInput String: " + s);
        System.out.println("Output: " + sol.myAtoi(s));
    }
}
