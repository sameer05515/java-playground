package com.coding.practice.codes.level1.a_fibonacciSeries;

import java.util.ArrayList;
import java.util.List;

public class TraditionalApproachOptimised {
    public static void main(String[] args) {
        List<Integer> series = getFibonacciSeries(10);
        System.out.println(series);
    }

    private static List<Integer> getFibonacciSeries(int n) {
        List<Integer> result = new ArrayList<>();

        if (n < 1) {
            System.out.println("Invalid input: " + n);
            return result;
        }

        // Add initial two Fibonacci numbers
        result.add(0);
        if (n > 1) {
            result.add(1);
        }

        // Generate Fibonacci series
        for (int i = 2; i < n; i++) {
            int next = result.get(i - 1) + result.get(i - 2);
            result.add(next);
        }

        return result;
    }
}
