package com.coding.practice.codes.level1.factorial;

public class TraditionalApproachOptimised {
    public static void main(String[] args) {
        int[] arr = {0, 1, 2, 3, 10};

        System.out.println("Recursive Approach");
        for (int val : arr) {
            System.out.println("Factorial of " + val + " = " + factorialRecursive(val));
        }

        System.out.println("Iterative Approach");
        for (int val : arr) {
            System.out.println("Factorial of " + val + " = " + factorialIterative(val));
        }
    }

    private static int factorialRecursive(int val) {
        return (val < 1) ? 1 : val * factorialRecursive(val - 1);
    }

    private static int factorialIterative(int val) {
        if (val < 1) {
            return 1;
        }
        int result = 1;
        for (int i = 2; i <= val; i++) {
            result *= i;
        }
        return result;
    }
}
