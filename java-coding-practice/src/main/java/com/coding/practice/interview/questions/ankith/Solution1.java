package com.coding.practice.interview.questions.ankith;

import java.util.HashMap;
import java.util.Map;

public class Solution1 {
  public static void main(String[] args) {
    String[] input = {
      "Akash 78",
      "Bharat 89",
      "Akash 90",
      "Akash 70",
      "Chetan 67",
      "Bharat 88",
      "Bharat 94",
      "Chetan 84"
    };

    //    Arrays.stream(input)
    //            .map(s->s.split(" "))
    //            .
    //      Output:
    //      Akash : (78 + 90 + 70)/3
    //      Bharat: (89 + 88 + 94)/3
    //      Chetan: (67 + 84)/2

    String[][] str = new String[input.length][2];
    for (int i = 0; i < input.length; i++) {

      str[i] = input[i].split(" ");
    }

    Map<String, Integer> nameOccCount = new HashMap<>();
    for (String[] s : str) {
      if (nameOccCount.containsKey(s[0])) {
        nameOccCount.put(s[0], nameOccCount.get(s[0]) + 1);

      } else {
        nameOccCount.put(s[0], 1);
      }
    }

    //    System.out.println(nameOccCount);

    Map<String, Integer> nameTotal = new HashMap<>();
    for (String[] s : str) {
      if (nameTotal.containsKey(s[0])) {
        nameTotal.put(s[0], nameTotal.get(s[0]) + Integer.parseInt(s[1]));

      } else {
        nameTotal.put(s[0], Integer.parseInt(s[1]));
      }
    }

    for (String key : nameTotal.keySet()) {
      System.out.printf("%s = %f%n", key, (float) (nameTotal.get(key)) / nameOccCount.get(key));
    }
  }
}
