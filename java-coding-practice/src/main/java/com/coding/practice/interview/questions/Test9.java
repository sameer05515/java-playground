package com.coding.practice.interview.questions;

import java.util.TreeSet;

public class Test9 {
  public static void main(String[] args) {
    TreeSet<Employee> ts = new TreeSet<>();
    ts.add(new Employee(1, "manoj"));
    ts.add(new Employee(1, "manoj"));
    System.out.println(ts.size());
  }

  static class Employee implements Comparable<Employee> {
    private int id;
    private String name;

    public Employee(int id, String name) {
      this.id = id;
      this.name = name;
    }

    @Override
    public int compareTo(Employee o) {
      int idCompare = Integer.compare(this.id, o.id);
      if (idCompare != 0) {
        return idCompare;
      }
      return this.name.compareTo(o.name);
    }
  }
}

// @Data
