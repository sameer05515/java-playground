package com.coding.practice.optional;

import java.util.Optional;

public class OptionalExample {
  public static String defaultSupplier(String val) {
    return "defaultSupplier : " + val;
  }

  public static void main(String[] args) {
    String name = null;
    Optional<String> optionalString = Optional.ofNullable(name);
    String output =
        optionalString.map(s -> s.toUpperCase()).orElseGet(() -> defaultSupplier("Null value"));
    System.out.println(output);
  }
}
