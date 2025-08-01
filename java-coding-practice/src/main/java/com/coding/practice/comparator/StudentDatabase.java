package com.coding.practice.comparator;

import java.util.ArrayList;
import java.util.List;

public class StudentDatabase {
  public static List<Student> getStudents() {
    List<Student> students = new ArrayList<>();

    students.add(new Student("Prem", 4, 3.5));
    students.add(new Student("Priya", 3, 3.9));
    students.add(new Student("Akshay", 2, 3.6));
    students.add(new Student("Rishabh", 1, 3.4));
    students.add(new Student("Chandan", 3, 3.7));
    students.add(new Student("Mohan", 2, 3.1));
    students.add(new Student("Ramesh", 1, 3.9));

    return students;
  }
}
