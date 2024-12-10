package com.coding.practice.interview.questions.leetcode.q8;

public class Solution {

    public int reverse(int x) {
        int res=0;
        int q=x;
        while(q!=0){
            int digit=q%10;
            q=q/10;
            // Check for overflow/underflow before multiplying by 10 or adding the digit
            if (res > (Integer.MAX_VALUE / 10) || (res == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return 0; // Overflow for positive numbers
            }
            if (res < (Integer.MIN_VALUE / 10) || (res == Integer.MIN_VALUE / 10 && digit < Integer.MIN_VALUE % 10)) {
                return 0; // Underflow for negative numbers
            }
            res=res*10+(digit);
        }
        return res;
    }

    public static void main(String[] args) {
        Solution sol=new Solution();
        run(sol, 123);
        run(sol, -123);
        run(sol, 120);
        run(sol, 1534236469);
    }

    public static void run(Solution sol, int x){
        System.out.println("-----------\nInput int: "+x);
        System.out.println("Output: "+ sol.reverse(x));
    }
}
