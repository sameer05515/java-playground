package com.coding.practice.codes.intTohhMMssFormat;

import java.util.Arrays;

public class JavaSolution {

  private static final boolean debug = false;

  public static void main(String[] args) {
    long[] initialArr = new long[] {3632, 2432, ((60 * 60 + 13) * 60) + 13};
    long[] finalArr = new long[initialArr.length];
    int index = 0;
    for (long input : initialArr) {
      String s = formatToHHMMss(input);
      long[] intermediateArr = Arrays.stream(s.split(":")).mapToLong(Long::parseLong).toArray();
      finalArr[index++] = seconds(intermediateArr, debug);
    }
    System.out.println(Arrays.equals(initialArr, finalArr) ? "Success" : "Fail");
  }

  private static String formatToHHMMss(long input) {
    StringBuilder formattedTime = new StringBuilder();
    long multiple = input;

    // Extract hours, minutes, and seconds
    for (int i = 0; i < 3; i++) {
      formattedTime.insert(0, ":" + String.format("%02d", (multiple % 60)));
      multiple = multiple / 60;
    }

    // Remove leading colon and print the result
    String s = formattedTime.substring(1);
    System.out.println(s);
    return s;
  }

  public static long seconds(long[] timeArr, boolean debug) {
    long result = 0;

    for (int i = 2; i >= 0; i--) {
      long arrPrevValue = timeArr[2 - i];
      log(
          String.format(
              "[Before]: Iteration# %d , result= %d, arrCurrentValue= %d %n",
              (i - 2), result, arrPrevValue),
          debug);
      result = (result * 60) + arrPrevValue;
      log(
          String.format(
              "[After]: Iteration# %d , result= %d, arrCurrentValue= %d %n",
              (i - 2), result, arrPrevValue),
          debug);
    }
    System.out.println(result);
    return result;
  }

  public static void log(String message, boolean debug) {
    if (debug) {
      System.out.print(message);
    }
  }
}
