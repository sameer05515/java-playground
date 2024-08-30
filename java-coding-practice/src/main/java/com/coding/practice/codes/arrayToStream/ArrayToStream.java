package com.coding.practice.codes.arrayToStream;

import java.util.Arrays;
import java.util.stream.Stream;

public class ArrayToStream {
    public static Stream<String> convertArrayToStream(String[] array) {
        return Arrays.stream(array);
    }

    public static void main(String[] args) {
        String[] exampleArray = {"apple", "banana", "cherry"};
        Stream<String> stream = convertArrayToStream(exampleArray);

        // Example usage
        stream.forEach(System.out::println);
    }
}

