package com.coding.practice.emp1;

import java.util.HashSet;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    Set<Employee> employees = new HashSet<>();
    employees.add(new Employee(1, "Prem"));
    employees.add(new Employee(1, "Prem"));
    System.out.println(employees.size());
  }
}
