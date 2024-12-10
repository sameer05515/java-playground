package com.coding.practice.interview.questions.leetcode.q5;

public class Solution5 {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;
        if (s.length() == 1) return 1;

        int resultLength = 0;
        String currentLongestStr = "";

        char[] chars = s.toCharArray();
        StringBuilder subStr = new StringBuilder();
        subStr.append(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (subStr.toString().contains(c + "")) {
//                subStr.setLength(0);
                subStr.append(subStr.substring(subStr.indexOf(c+"")));
            }
            subStr.append(c);
            if (currentLongestStr.length() < subStr.length()) {
                currentLongestStr = subStr.toString();
            }
            System.out.printf("lastLongestStr: %s,index: %s, character: %s, subStr: %s %n", currentLongestStr, i, c, subStr);
        }
        resultLength = currentLongestStr.length();
        System.out.println("currentLongestStr: "+currentLongestStr);
        return resultLength;
    }

    public static void main(String[] args) {
        Solution5 sol = new Solution5();
        String[] arr = new String[]{
                "dvdf",
                "au",
                "abcabcbb",
                "bbbbb",
                "pwwkew"
        };
        for (String s : arr) {
            System.out.println("\n\n----------------------\n" + s + "\n" + sol.lengthOfLongestSubstring(s));
        }
    }


}
