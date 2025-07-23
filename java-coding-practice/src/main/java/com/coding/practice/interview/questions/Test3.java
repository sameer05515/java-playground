package com.coding.practice.interview.questions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class Test3 {
  public static void main(String[] args) {
    new Test3().meth1();

    Optional<Integer> res =
        Arrays.stream(new int[] {100, 200, 300, 119})
            .boxed()
            .sorted(Comparator.reverseOrder())
            .skip(0)
            .findFirst();

    res.ifPresentOrElse(i -> System.out.println(i), () -> System.out.println("No value found"));
  }

  public void meth1() {
    String s = "abcde";

    char[] ch = s.toCharArray();
    String res = "";
    for (int i = ch.length - 1; i >= 0; i--) {
      res += ch[i];
    }

    System.out.println(res);
  }
}
