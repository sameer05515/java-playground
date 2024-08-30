package com.coding.practice.codes.transform.treedata.common;

public class IndentationUtil {

    /**
     * Finds the indentation level for a given string.
     * The method counts the number of leading spaces or tabs in the string.
     *
     * @param input the string to analyze
     * @return the number of leading spaces or tabs
     */
    public static int findIndentationLevel(String input) {
        if (input == null || input.isEmpty()) {
            return 0; // No indentation for null or empty strings
        }

        int indentationLevel = 0;
        char[] chars = input.toCharArray();

        // Count the number of leading spaces or tabs
        for (char c : chars) {
            if (c == ' ' || c == '\t') {
                indentationLevel++;
            } else {
                break; // Stop counting when a non-space/non-tab character is encountered
            }
        }

        return indentationLevel;
    }

    public static void main(String[] args) {
        // Test cases
        String test1 = "    This is a test string"; // 4 spaces
        String test2 = "\t\tAnother test string";    // 2 tabs
        String test3 = "NoIndent";                  // No indentation
        String test4 = "  \tMixed indent";          // 2 spaces followed by 1 tab
        String test5 = "";                          // Empty string
        String test6 = null;                        // Null string

        System.out.println(findIndentationLevel(test1)); // Output: 4
        System.out.println(findIndentationLevel(test2)); // Output: 2
        System.out.println(findIndentationLevel(test3)); // Output: 0
        System.out.println(findIndentationLevel(test4)); // Output: 2 (stops at the first tab)
        System.out.println(findIndentationLevel(test5)); // Output: 0
        System.out.println(findIndentationLevel(test6)); // Output: 0
    }
}
