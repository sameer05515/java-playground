package com.coding.practice.comparator;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class StudentComparator {
  static Consumer<Student> studentConsumer = s -> System.out.println(s);

  public static void sortByName(List<Student> studentList) {
    System.out.println("After sort: ");
    Comparator<Student> studentComparator =
        Comparator.comparing(Student::getName).thenComparing(Student::getGrade);
    studentList.sort(studentComparator);
    studentList.forEach(studentConsumer);
  }

  public static void main(String[] args) {
    List<Student> studentList = StudentDatabase.getStudents();

    var hhh = List.of(1, 2, 3);
    System.out.println(hhh);

    System.out.println("Before sort: ");
    studentList.forEach(studentConsumer);
    sortByName(studentList);
  }
}
