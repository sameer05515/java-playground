package com.coding.practice.codes.level1.a_fibonacciSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8Approach {
    public static void main(String[] args) {
        List<Integer> series = getFibonacciSeries(10);
        System.out.println(series);
    }

    private static List<Integer> getFibonacciSeries(int count) {
        if (count < 0) {
            System.out.println("Invalid number: " + count);
            return List.of();
        }

        return Stream.iterate(new int[]{0, 1}, f -> new int[]{f[1], f[0] + f[1]})
                .limit(count)
                .peek(v-> System.out.println(v[0]+"\t\t"+v[1]))
                .map(f -> f[0])
                .collect(Collectors.toList());
    }
}
