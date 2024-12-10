package com.coding.practice.codes.level1.factorial;

import java.util.Arrays;
import java.util.Optional;

public class Java8Approach {
    public static void main(String[] args) {
        int[] arr = {0, 1, 2, 3, 10};

        System.out.println("Recursive Approach");
        Arrays.stream(arr).forEach(val -> System.out.println("Factorial of " + val + " = " + factorialRecursive(val)));


        System.out.println("Iterative Approach");
        Arrays.stream(arr).forEach(val -> System.out.println("Factorial of " + val + " = " + factorialIterative(val)));

    }

    private static int factorialRecursive(int val) {
        return Optional.of(val)
                .filter(v -> v >= 1)
                .map(v -> v * factorialRecursive(v - 1))
                .orElse(1);
    }

    private static int factorialIterative(int val) {
        // Use Optional to handle invalid values
        return Optional.of(val)
                .filter(v -> v >= 1) // Filter to only process values >= 1
                .map(v -> java.util.stream.IntStream.rangeClosed(2, v)
                        .reduce(1, (a, b) -> a * b)) // Compute factorial
                .orElse(1); // Return 1 if val < 1 (or empty Optional)
    }
}

