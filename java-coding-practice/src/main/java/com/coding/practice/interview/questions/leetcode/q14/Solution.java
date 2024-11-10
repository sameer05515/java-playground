package com.coding.practice.interview.questions.leetcode.q14;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();

        dfs(0, 0, "", n, res);

        return res;
    }

    private void dfs(int openP, int closeP, String s, int n, List<String> res) {
        if (openP == closeP && openP + closeP == n * 2) {
            res.add(s);
            return;
        }

        if (openP < n) {
            dfs(openP + 1, closeP, s + "(", n, res);
        }

        if (closeP < openP) {
            dfs(openP, closeP + 1, s + ")", n, res);
        }
    }

    public static void main(String[] args) {
        Solution sol=new Solution();
        run(sol, 3);
        run(sol, 1);
    }

    public static void run(Solution sol, int n){
        System.out.println("Input int: "+n);
        System.out.println("Parenthesis output: "+ sol.generateParenthesis(n));
    }
}
