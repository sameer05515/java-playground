package com.coding.practice.interview.questions.leetcode.q12;

import java.util.LinkedList;

public class Solution {
    public boolean isValid(String s) {
        LinkedList<Character> stack = new LinkedList<>();

        for (char ch : s.toCharArray()) {
            switch (ch) {
                case '(':
                case '{':
                case '[':
                    // Push opening brackets onto the stack
                    stack.push(ch);
                    System.out.printf("Pushing %s, current stack: %s %n", ch, stack);
                    break;

                case ')':
                    if (stack.isEmpty() || stack.pop() != '(') return false;
                    System.out.printf("Popped for %s, current stack: %s %n", ch, stack);
                    break;

                case '}':
                    if (stack.isEmpty() || stack.pop() != '{') return false;
                    System.out.printf("Popped for %s, current stack: %s %n", ch, stack);
                    break;

                case ']':
                    if (stack.isEmpty() || stack.pop() != '[') return false;
                    System.out.printf("Popped for %s, current stack: %s %n", ch, stack);
                    break;

                default:
                    // Ignore any invalid characters (if allowed)
                    break;
            }
        }

        // If stack is empty, all brackets are valid and matched
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String[] arr = new String[]{
                "()",
                "()[]{}",
                "(]",
                "([])"
        };

        Solution sol = new Solution();

        for (String s : arr) {
            run(sol, s);
        }
    }

    private static void run(Solution sol, String s) {
        System.out.println("Input string: " + s + " is valid: " + sol.isValid(s));
    }
}
