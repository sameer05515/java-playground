// package com.coding.practice.interview.questions.ankith;
//
// public class Solution2 {}

package com.coding.practice.interview.questions.ankith;

import java.util.HashMap;
import java.util.Map;

public class Solution2 {
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

    // Maps to store total scores and occurrence counts
    Map<String, Integer> nameOccCount = new HashMap<>();
    Map<String, Integer> nameTotal = new HashMap<>();

    for (String entry : input) {
      String[] parts = entry.split(" ");
      String name = parts[0];
      int score = Integer.parseInt(parts[1]);

      // Update occurrence count
      nameOccCount.put(name, nameOccCount.getOrDefault(name, 0) + 1);

      // Update total score
      nameTotal.put(name, nameTotal.getOrDefault(name, 0) + score);
    }

    // Print averages
    for (String key : nameTotal.keySet()) {
      float average = (float) nameTotal.get(key) / nameOccCount.get(key);
      System.out.printf("%s: %.2f%n", key, average);
    }
  }
}
