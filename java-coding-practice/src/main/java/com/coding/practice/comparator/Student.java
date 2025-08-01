package com.coding.practice.comparator;

import java.util.Objects;

public class Student {
  private String name;
  private int grade;
  private double cgpa;

  public Student() {}

  public Student(String name, int grade, double cgpa) {
    this.name = name;
    this.grade = grade;
    this.cgpa = cgpa;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getGrade() {
    return grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  public double getCgpa() {
    return cgpa;
  }

  public void setCgpa(double cgpa) {
    this.cgpa = cgpa;
  }

  @Override
  public String toString() {
    return "Student{" + "name='" + name + '\'' + ", grade=" + grade + ", cgpa=" + cgpa + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Student student = (Student) o;
    return grade == student.grade
        && Double.compare(cgpa, student.cgpa) == 0
        && Objects.equals(name, student.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, grade, cgpa);
  }
}
