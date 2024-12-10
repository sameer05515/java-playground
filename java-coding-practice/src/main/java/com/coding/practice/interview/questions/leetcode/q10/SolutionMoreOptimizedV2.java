//package com.coding.practice.interview.questions.leetcode.q10;
//
//public class SolutionMoreOptimizedV2 {
//}

package com.coding.practice.interview.questions.leetcode.q10;

public class SolutionMoreOptimizedV2 {

    public boolean isPalindrome(int x) {
        // Negative numbers are not palindromes
        if (x < 0) return false;

        // Numbers ending in 0 (except 0 itself) are not palindromes
        if (x != 0 && x % 10 == 0) return false;

        int reversed = 0;

        // Reverse only half of the digits
        while (x > reversed) {
            int lastDigit = x % 10;
            reversed = reversed * 10 + lastDigit;
            x /= 10;
        }

        // For even-length numbers: reversed == x
        // For odd-length numbers: reversed/10 == x (removes the middle digit)
        return x == reversed || x == reversed / 10;
    }

    public static void main(String[] args) {
        SolutionMoreOptimizedV2 sol = new SolutionMoreOptimizedV2();
        int[] arr = new int[]{
//                121,
//                -121,
//                10,
//                12321,
//                1234321,
//                0,
                10
        };

        for (int x : arr) {
            run(sol, x);
        }
    }

    public static void run(SolutionMoreOptimizedV2 sol, int x) {
        System.out.println("-----------\nInput int: " + x);
        System.out.println("Output: " + sol.isPalindrome(x));
    }
}
