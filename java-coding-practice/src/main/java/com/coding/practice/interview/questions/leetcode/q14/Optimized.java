//package com.coding.practice.interview.questions.leetcode.q14;
//
//public class Optimized {
//}


package com.coding.practice.interview.questions.leetcode.q14;

import java.util.ArrayList;
import java.util.List;

public class Optimized {

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        dfs(0, 0, n, sb, res);
        return res;
    }

    private void dfs(int openP, int closeP, int n, StringBuilder sb, List<String> res) {
        // Base case: if we have used all parentheses
        if (openP == n && closeP == n) {
            res.add(sb.toString());
            return;
        }

        // Add an open parenthesis if possible
        if (openP < n) {
            sb.append("(");
            dfs(openP + 1, closeP, n, sb, res);
            sb.deleteCharAt(sb.length() - 1); // Backtrack
        }

        // Add a close parenthesis if possible
        if (closeP < openP) {
            sb.append(")");
            dfs(openP, closeP + 1, n, sb, res);
            sb.deleteCharAt(sb.length() - 1); // Backtrack
        }
    }

    public static void main(String[] args) {
        Optimized sol = new Optimized();
        run(sol, 3);
        run(sol, 1);
    }

    public static void run(Optimized sol, int n) {
        System.out.println("Input int: " + n);
        System.out.println("Parenthesis output: " + sol.generateParenthesis(n));
    }
}
