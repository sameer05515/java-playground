package com.prem.junit.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
  com.prem.junit.test.CalculatorTest.class,
  com.prem.junit.test.CsvSourceExampleTest.class
})
public class MyTestSuite {}
