package com.coding.practice.codes.transform.treedata.common;

import com.coding.practice.codes.transform.treedata.common.LineInfoPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LineIndentationValidatorV2 {
    public static void main(String[] args) {
        String invalidIndentation = """
                    Two Tabs
                OneTab
                    Root no 2
                        Root ki aulad
            """;
        List<LineInfoPojo> s = getLineTextAndIndentationMap(List.of(invalidIndentation.split("\n")), 1);
        System.out.println(s);
    }

    private static List<LineInfoPojo> getLineTextAndIndentationMap(List<String> nonEmptyLines, int step) {
        char firstIndentationCharacter = '\0';
        boolean firstIndentationCharacterFound = false;

        // Determine the first indentation character
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

//        if (!firstIndentationCharacterFound) {
//            throw new IllegalArgumentException("No indentation character found in the input lines.");
//        }

        List<LineInfoPojo> lineInfoList = new ArrayList<>();
        for (String line : nonEmptyLines) {
            char[] chars = line.toCharArray();
            int indentationLevel = 0;

            for (char c : chars) {
                if (c == firstIndentationCharacter) {
                    // Treat tabs as 4 spaces (you can adjust this if needed)
                    indentationLevel += (c == '\t') ? 4 : 1;
                } else if (c == ' ' || c == '\t') {
                    throw new IllegalArgumentException("Mixed indentation found in the line: " + line);
                } else {
                    break;
                }
            }

            LineInfoPojo lineInfo = LineInfoPojo.builder()
                    .name(line.trim())
                    .indentationLevel(indentationLevel)
                    .uniqueId(UUID.randomUUID().toString())
                    .build();
            lineInfoList.add(lineInfo);
        }

        return lineInfoList;
    }

    private static boolean shouldLog = true;
    private static int allowedStep = 0;

    private static void log(String message, int step) {
        if (!shouldLog || step < allowedStep) return;
        System.out.println("Step: " + step + " , Message: " + message);
    }
}
