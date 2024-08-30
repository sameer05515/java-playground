package com.coding.practice.codes.transform.treedata.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineIndentationValidator {

    private static List<Map<String, Object>> getLineTextAndIndentationMap(List<String> nonEmptyLines, int step) {
        log("""
                Validate if there is no mixed indentation:
                1. Find the first indentation character (space or tab)
                2. Check if all lines have indentation with the same 'first indentation character'
                3. If mixed indentation is found, throw an exception
                4. Else, return a list of maps containing line text and its corresponding indentation level
                """, step);

        char firstIndentationCharacter = '\0';
        boolean firstIndentationCharacterFound = false;

        // Find the first indentation character
        for (String line : nonEmptyLines) {
            char[] chars = line.toCharArray();
            for (char c : chars) {
                if (c != ' ' && c != '\t') {
                    break;
                } else {
                    firstIndentationCharacter = c;
                    firstIndentationCharacterFound = true;
                    break;
                }
            }
            if (firstIndentationCharacterFound) break;
        }

        if (!firstIndentationCharacterFound) {
            throw new IllegalArgumentException("No indentation character found in the input lines.");
        }

        // Validate and create the list of maps
        List<Map<String, Object>> lineInfoList = new ArrayList<>();
        for (String line : nonEmptyLines) {
            char[] chars = line.toCharArray();
            int indentationLevel = 0;

            for (char c : chars) {
                if (c == firstIndentationCharacter) {
                    indentationLevel++;
                } else if (c == ' ' || c == '\t') {
                    throw new IllegalArgumentException("Mixed indentation found in the line: " + line);
                } else {
                    break;
                }
            }

            Map<String, Object> lineInfo = new HashMap<>();
            lineInfo.put("text", line.trim());
            lineInfo.put("indentationLevel", indentationLevel);

            lineInfoList.add(lineInfo);
        }

        return lineInfoList;
    }

    // Dummy log method for demonstration purposes
    private static void log(String message, int step) {
        System.out.println("Step " + step + ": " + message);
    }

    public static void main(String[] args) {
        List<String> nonEmptyLines = List.of("    Line 1", "\tLine 2", "        Line 3");

        try {
            List<Map<String, Object>> result = getLineTextAndIndentationMap(nonEmptyLines, 1);
            result.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
