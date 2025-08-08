package com.coding.practice.emp1;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
public class Employee {
  private int id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  private String name;

  public Employee(int id, String name) {
    this.id = id;
    this.name = name;
  }
}
