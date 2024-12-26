package com.coding.practice.interview.questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test5 {

  //    {
  //        1:[apple, banana]
  //        2:[avocado]
  //        3[kiwi, orange]
  //    }
  public static void main(String[] args) {
    List<String> words =
        List.of(
            "avocado", "apple", "banana", "apple", "apple", "orange", "banana", "apple", "banana",
            "kiwi", "avocado", "avocado", "banana");

    Map<String, Long> map =
        words.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    System.out.println(map);
    Map<Long, List<String>> map1 = new HashMap<>();

    map.forEach(
        (k, v) -> {
          map1.computeIfAbsent(v, x -> new ArrayList<>()).add(k);
        });

    System.out.println(map1);
  }
}
