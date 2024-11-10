//package com.coding.practice.interview.questions.leetcode.q12;
//
//public class Optimized {
//}


package com.coding.practice.interview.questions.leetcode.q12;

import java.util.*;

public class Optimized {
    public boolean isValid(String s) {
        // Define matching brackets using a Map
        Map<Character, Character> bracketPairs = Map.of(')', '(', '}', '{', ']', '[');
        Deque<Character> stack = new LinkedList<>();

        // Iterate through the string
        for (char ch : s.toCharArray()) {
            if (bracketPairs.containsValue(ch)) {
                // If it's an opening bracket, push onto the stack
                stack.push(ch);
//                System.out.printf("Pushing %s, current stack: %s %n", ch, stack);
            } else if (bracketPairs.containsKey(ch)) {
                // If it's a closing bracket, validate the match
                if (stack.isEmpty() || stack.pop() != bracketPairs.get(ch)) {
                    return false;
                }
//                System.out.printf("Popping for %s, current stack: %s %n", ch, stack);
            }
        }

        // Stack should be empty if all brackets matched
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        List<String> testCases = Arrays.asList(
                "()", "()[]{}", "(]", "([])"
        );

        Optimized sol = new Optimized();

        testCases.forEach(testCase -> run(sol, testCase));
    }

    private static void run(Optimized sol, String s) {
        System.out.println("Input string: " + s + " is valid: " + sol.isValid(s));
    }
}
