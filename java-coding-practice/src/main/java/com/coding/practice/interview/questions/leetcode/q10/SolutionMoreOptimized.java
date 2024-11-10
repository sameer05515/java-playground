package com.coding.practice.interview.questions.leetcode.q10;

public class SolutionMoreOptimized {

    public boolean isPalindrome(int x) {
        if(x<0) return false;
        char[] s=String.valueOf(x).toCharArray();

        for (int i = 0; i < s.length/2; i++) {
            if(s[i]!=s[s.length-i-1]){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        SolutionMoreOptimized sol=new SolutionMoreOptimized();
        int[] arr=new int[]{
                121,
                -121,
                10};

        for(int x:arr){
            run(sol, x);
        }
    }

    public static void run(SolutionMoreOptimized sol, int x) {
        System.out.println("-----------\nInput int: " + x);
        System.out.println("Output: " + sol.isPalindrome(x));
    }
}
