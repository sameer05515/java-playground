package com.coding.practice.interview.questions.leetcode.q10;

import java.util.Arrays;

public class Solution {

    public boolean isPalindrome(int x) {
        if(x<0) return false;
        return String.valueOf(x).equals(String.valueOf(x).chars()
                .mapToObj(c -> String.valueOf((char) c))
                .reduce("", (acc, c) -> c + acc));
    }

    public static void main(String[] args) {
        Solution sol=new Solution();
        int[] arr=new int[]{
                121,
                -121,
                10};

        for(int x:arr){
            run(sol, x);
        }
    }

    public static void run(Solution sol, int x) {
        System.out.println("-----------\nInput int: " + x);
        System.out.println("Output: " + sol.isPalindrome(x));
    }
}
