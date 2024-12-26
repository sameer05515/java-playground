package com.prem.junit.test;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calc")
public class CalculatorController1 {

  private final Calculator calculator = new Calculator();

  @PostMapping("/add")
  public int add(@RequestBody CalcRequest request) {
    return calculator.add(request.getA(), request.getB());
  }

  @PostMapping("/subtract")
  public int subtract(@RequestBody CalcRequest request) {
    return calculator.subtract(request.getA(), request.getB());
  }

  @PostMapping("/multiply")
  public int multiply(@RequestBody CalcRequest request) {
    return calculator.multiply(request.getA(), request.getB());
  }
}
