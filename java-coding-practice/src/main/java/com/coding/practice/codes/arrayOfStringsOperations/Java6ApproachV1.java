package com.coding.practice.codes.arrayOfStringsOperations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Java6ApproachV1 {
    public static void main(String[] args) {
        List<Integer> jj=Arrays
                .asList(1, 2, 3, 1, 4)
                .stream()
                .filter(i-> i>1)
                .collect(Collectors.toList());
        System.out.println(jj);
    }
}
