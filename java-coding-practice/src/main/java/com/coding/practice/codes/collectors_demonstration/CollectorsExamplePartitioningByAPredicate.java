package com.coding.practice.codes.collectors_demonstration;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectorsExamplePartitioningByAPredicate {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(2, 5, 8, 11, 14, 17, 20);

        Map<Boolean, List<Integer>> partitionedByEvenOdd = numbers.stream()
            .collect(Collectors.partitioningBy(num -> num % 2 == 0));

        Map<Integer, List<Integer>> groupingByModulusOfFive = IntStream.rangeClosed(1, 30)
                .boxed() // Convert IntStream to Stream<Integer>
                .collect(Collectors.groupingBy(num -> num % 5));


        System.out.println(partitionedByEvenOdd);
        System.out.println(groupingByModulusOfFive);
    }


}