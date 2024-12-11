package com.coding.practice.codes.intTohhMMssFormat;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Test {
  public static void main(String[] args) {
    long totalSeconds = ((100 * 60 + 13) * 60) + 13;
    //    long totalSeconds = JavaSolution.seconds(new long[] {1000000000, 59, 13}, false);
    long hours, minutes, seconds;
    seconds = totalSeconds % 60;
    minutes = (totalSeconds / 60) % 60;
    hours = totalSeconds / 3600;

    System.out.println(
        formatTime.apply(Map.of("HOURS", hours, "MINUTES", minutes, "SECONDS", seconds)));
  }

  static Function<Map<String, Long>, String> formatTime =
      (map) -> {
        if (map != null
            && map.size() == 3
            && map.keySet().containsAll(List.of("HOURS", "MINUTES", "SECONDS"))) {
          return String.format(
              "%02d:%02d:%02d", map.get("HOURS"), map.get("MINUTES"), map.get("SECONDS"));
        }
        return String.format("Invalid map: `%s` ", map);
      };
}
