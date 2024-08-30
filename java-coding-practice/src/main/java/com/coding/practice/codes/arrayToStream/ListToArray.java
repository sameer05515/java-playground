package com.coding.practice.codes.arrayToStream;

import java.util.List;

public class ListToArray {
    public static String[] convertListToArray(List<String> list) {

        return list.toArray(new String[0]);
    }

    public static void main(String[] args) {
        List<String> exampleList = List.of("apple", "banana", "cherry");
        String[] array = convertListToArray(exampleList);

        // Example usage
        for (String item : array) {
            System.out.println(item);
        }
    }
}
