//package com.coding.practice.interview.questions.leetcode.q9;
//
//public class Optimized {
//}

package com.coding.practice.interview.questions.leetcode.q9;

public class Optimized {
    public int myAtoi(String s) {
        if (s == null || s.isEmpty()) return 0;

        int res = 0;
        int ptr = 0;
        int multipler = 1;

        // Skip leading whitespace
        while (ptr < s.length() && s.charAt(ptr) == ' ') {
            ptr++;
        }

        // Check if the input is empty or contains only spaces
        if (ptr >= s.length()) return 0;

        // Check for sign
        char currentChar = s.charAt(ptr);
        if (currentChar == '-') {
            multipler = -1;
            ptr++;
        } else if (currentChar == '+') {
            ptr++;
        }

        // Process numeric characters
        while (ptr < s.length() && Character.isDigit(s.charAt(ptr))) {
            int digit = s.charAt(ptr) - '0';

            // Check for overflow/underflow before multiplying or adding
            if (res > (Integer.MAX_VALUE - digit) / 10) {
                return multipler == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            res = res * 10 + digit;
            ptr++;
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
                "-2147483649",
                "2147483647",
                "   +0 123",
                "   0000100"
        };

        Optimized sol = new Optimized();

        for (String s : arr) {
            run(sol, s);
        }
    }

    public static void run(Optimized sol, String s) {
        System.out.println("-----------\nInput String: " + s);
        System.out.println("Output: " + sol.myAtoi(s));
    }
}

