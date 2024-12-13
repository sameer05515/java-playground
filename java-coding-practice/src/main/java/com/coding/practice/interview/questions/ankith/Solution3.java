package com.coding.practice.interview.questions.ankith;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Solution3 {
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

    // Reduce to a map of students
    Map<String, Student> students =
        Arrays.stream(input)
            .reduce(
                new HashMap<>(),
                (acc, entry) -> {
                  String[] parts = entry.split(" ");
                  String name = parts[0];
                  int score = Integer.parseInt(parts[1]);

                  // Update or create the student entry
                  acc.merge(
                      name,
                      Student.builder().name(name).total(score).courseCount(1).build(),
                      (existing, newStudent) -> {
                        existing.setTotal(existing.getTotal() + newStudent.getTotal());
                        existing.setTotalStr(
                            existing.getTotalStr() + " + " + newStudent.getTotal());
                        existing.setCourseCount(existing.getCourseCount() + 1);
                        return existing;
                      });

                  return acc;
                },
                (map1, map2) -> {
                  // Combine maps (if using parallel streams)
                  map2.forEach(
                      (key, value) ->
                          map1.merge(
                              key,
                              value,
                              (s1, s2) -> {
                                s1.setTotal(s1.getTotal() + s2.getTotal());
                                s1.setCourseCount(s1.getCourseCount() + s2.getCourseCount());
                                return s1;
                              }));
                  return map1;
                });

    // Print student averages
    students
        .values()
        .forEach(
            student -> {
              float average = (float) student.getTotal() / student.getCourseCount();
              student.setAverage(average);
              System.out.printf(
                  "Name: %s, %s Total: %d, Average: %.2f%n",
                  student.getName(),
                  student.getTotalStr(),
                  student.getTotal(),
                  student.getAverage());
            });
  }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Student {
  private String name;
  private int total;
  private String totalStr = "";
  private float average;
  private int courseCount;
}
