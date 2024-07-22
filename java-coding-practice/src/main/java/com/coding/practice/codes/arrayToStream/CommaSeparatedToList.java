package com.coding.practice.codes.arrayToStream;

import java.util.Arrays;
import java.util.List;

public class CommaSeparatedToList {
    public static List<String> createListFromCSV(String csv) {
        return Arrays.asList(csv.split(","));
    }

    public static void main(String[] args) {
        String csv = "apple,banana,cherry,orange";
        List<String> list = createListFromCSV(csv);
        
        // Example usage
        list.forEach(System.out::println);
    }
}
