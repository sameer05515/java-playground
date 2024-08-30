package com.coding.practice.codes.arrayOfStringsOperations;

import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String str = "example";

        // Convert char[] to Character[] and then create a stream
        Stream<Character> charStream = Arrays.stream(toCharacterArray(str.toCharArray()));

        // Print the characters
        charStream.forEach(System.out::println);

        Arrays.stream("""
                Selection Sort
                Insertion Sort
                Merge Sort
                Quick Sort
                Heap Sort
                Radix Sort
                Counting Sort
                Shell Sort
                Bucket Sort
                """.split("\\n"))
                .map(s -> "what is "+s+"? please write sample program for it.")
                .forEach(System.out::println);
    }

    // Helper method to convert char[] to Character[]
    private static Character[] toCharacterArray(char[] charArray) {
        Character[] characterArray = new Character[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            characterArray[i] = charArray[i];
        }
        return characterArray;
    }
}
