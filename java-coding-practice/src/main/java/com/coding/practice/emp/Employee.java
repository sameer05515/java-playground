package com.coding.practice.emp;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
  private int id;
  private String name;
  private int age;
  private String deptId;
  List<Address> addresses;
}
