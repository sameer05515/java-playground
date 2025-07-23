package com.prem.junit.test;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calc")
public class CalculatorController {

  private final Calculator calculator = new Calculator();

  @GetMapping("/add")
  public int add(@RequestParam int a, @RequestParam int b) {
    return calculator.add(a, b);
  }

  @GetMapping("/subtract")
  public int subtract(@RequestParam int a, @RequestParam int b) {
    return calculator.subtract(a, b);
  }

  @GetMapping("/multiply")
  public int multiply(@RequestParam int a, @RequestParam int b) {
    return calculator.multiply(a, b);
  }
}
