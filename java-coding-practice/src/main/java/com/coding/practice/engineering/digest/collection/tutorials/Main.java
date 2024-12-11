package com.coding.practice.engineering.digest.collection.tutorials;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Main {
  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);

    list.add(2);
    list.add(1);
    //    Collections.sort(list);
    //    list.sort(null);
    Collections.sort(
        list,
        new Comparator<Integer>() {
          @Override
          public int compare(Integer o1, Integer o2) {
            return o2 - o1;
          }
        });
    //    System.out.println(list);

    List<Student> students = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      students.add(new StudentChild("Student_" + i, Math.random() * 5, 05501 + i));
    }
    System.out.println(students);

    Collections.sort(students);
  }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Student implements Comparable<Student> {
  private String name;
  private double grade;

  @Override
  public int compareTo(Student o) {
    return Double.compare(this.grade, o.grade);
  }
}

class StudentChild extends Student {
  private int rollNo;

  public StudentChild(String name, double grade, int rollNo) {
    super(name, grade);
    this.rollNo = rollNo;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
