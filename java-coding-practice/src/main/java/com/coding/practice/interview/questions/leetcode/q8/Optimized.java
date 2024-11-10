//package com.coding.practice.interview.questions.leetcode.q8;
//
//public class Optimized {
//}

package com.coding.practice.interview.questions.leetcode.q8;

public class Optimized {

    public int reverse(int x) {
        int res = 0;

        while (x != 0) {
            int digit = x % 10; // Extract the last digit
            x /= 10; // Remove the last digit

            // Check for overflow/underflow before multiplying by 10 or adding the digit
            if (res > (Integer.MAX_VALUE / 10) || (res == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0; // Overflow for positive numbers
            }
            if (res < (Integer.MIN_VALUE / 10) || (res == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0; // Underflow for negative numbers
            }

            res = res * 10 + digit;
        }

        return res;
    }

    public static void main(String[] args) {
        Optimized sol = new Optimized();
        run(sol, 123);           // Expected: 321
        run(sol, -123);          // Expected: -321
        run(sol, 120);           // Expected: 21
        run(sol, 1534236469);    // Expected: 0 (overflow case)
        run(sol, -2147483412);   // Expected: -2143847412
    }

    public static void run(Optimized sol, int x) {
        System.out.println("-----------\nInput int: " + x);
        System.out.println("Output: " + sol.reverse(x));
    }
}

