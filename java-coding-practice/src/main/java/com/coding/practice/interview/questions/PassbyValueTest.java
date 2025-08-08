package com.coding.practice.interview.questions;

import java.util.ArrayList;
import java.util.List;

public class PassbyValueTest {
  void change(int x) {
    x = 50;
  }

  void modify(List<String> list) {
    list.add("Java");
  }

  void reassign(List<String> list) {
    list = new ArrayList<>();
    list.add("Python");
  }

  public static void main(String[] args) {
    int num = 10;
    PassbyValueTest passbyValueTest = new PassbyValueTest();
    passbyValueTest.change(num);
    System.out.println(num); // 👉 10 (unchanged)

    List<String> names = new ArrayList<>();
    passbyValueTest.modify(names);
    System.out.println(names); // 👉 [Java] ✅

    List<String> names1 = new ArrayList<>();
    passbyValueTest.reassign(names1);
    System.out.println(names1); // 👉 [] ❌ (no change)
  }
}
