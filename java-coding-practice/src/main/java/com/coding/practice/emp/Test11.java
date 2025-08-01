package com.coding.practice.emp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test11 {

  public static void main(String[] args) {
    List<Employee> employees = new ArrayList<>();
    employees.add(
        new Employee(1, "Prem", 39, "dept1", List.of(new Address("Faridabad", "Haryana"))));
    employees.add(new Employee(1, "Raju", 38, "dept2", List.of(new Address("Motihari", "Bihar"))));
    employees.add(
        new Employee(1, "Mangat", 37, "dept3", List.of(new Address("Amritsar", "Panjab"))));
    employees.add(new Employee(1, "Riya", 35, "dept1", List.of(new Address("New Delhi", "Delhi"))));
    employees.add(
        new Employee(1, "Prem1", 45, "dept1", List.of(new Address("Faridabad", "Haryana"))));
    employees.add(new Employee(1, "Raju1", 67, "dept2", List.of(new Address("Motihari", "Bihar"))));
    employees.add(
        new Employee(1, "Mangat1", 89, "dept3", List.of(new Address("Amritsar", "Panjab"))));
    employees.add(
        new Employee(1, "Riya1", 90, "dept3", List.of(new Address("New Delhi", "Delhi"))));

    //    employees.sort(Comparator.comparing(Employee::getAge).reversed());
    //    employees.forEach(s -> System.out.println(s));

    //    input - List<Employee>
    //            output - Map<String, List<String>
    //            key - deptId
    //    value - empName

    //    Map<String, List<String>> deptToNamesMap =
    var empMap =
        employees.stream()
            .collect(
                Collectors.groupingBy(
                    Employee::getDeptId,
                    Collectors.mapping(Employee::getName, Collectors.toList())));
    empMap.forEach((dept, names) -> System.out.println(dept + " -> " + names));

    employees.stream()
        .collect(
            Collectors.groupingBy(
                Employee::getDeptId, Collectors.mapping(Employee::getName, Collectors.toList())))
        .forEach((dept, names) -> System.out.println(dept + " -> " + names));

    employees.stream()
        .collect(
            Collectors.groupingBy(
                Employee::getDeptId, Collectors.mapping(e -> e, Collectors.toList())))
        .forEach((dept, names) -> System.out.println(dept + " -> " + names));

    employees.stream()
        .map(Employee::getName)
        .collect(Collectors.toList())
        .forEach(s -> System.out.println(s));

    employees.stream()
        .collect(
            Collectors.groupingBy(
                Employee::getDeptId,
                Collectors.mapping(Employee::getName, Collectors.joining(", "))))
        .forEach((dept, names) -> System.out.println(dept + " -> " + names));

    // Total ages grouped by dept
    employees.stream()
        .collect(
            Collectors.groupingBy(Employee::getDeptId, Collectors.summingInt(Employee::getAge)))
        .forEach((dept, totalAge) -> System.out.println(dept + " -> " + totalAge));

    // Average ages grouped by dept
    employees.stream()
        .collect(
            Collectors.groupingBy(Employee::getDeptId, Collectors.averagingInt(Employee::getAge)))
        .forEach((dept, averageAge) -> System.out.println(dept + " -> " + averageAge));
  }
}
