package com.coding.practice.interview.questions;

import java.util.ArrayList;
import java.util.List;

public class Test2 {

  static void reassign(List<String> list) {
    list = new ArrayList<>();
    list.add("Python");
  }

  public static void main(String[] args) {
    List<String> names = new ArrayList<>();
    reassign(names);
    System.out.println(names); // 👉 [] ❌ (no change)
  }
}
