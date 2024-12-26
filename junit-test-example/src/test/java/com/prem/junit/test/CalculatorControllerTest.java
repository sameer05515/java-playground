package com.prem.junit.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void testAddEndpoint() throws Exception {
    mockMvc
        .perform(get("/api/calc/add?a=2&b=3"))
        .andExpect(status().isOk())
        .andExpect(content().string("5"));
  }

  @Test
  void testSubtractEndpoint() throws Exception {
    mockMvc
        .perform(get("/api/calc/subtract?a=5&b=3"))
        .andExpect(status().isOk())
        .andExpect(content().string("2"));
  }

  @Test
  void testMultiplyEndpoint() throws Exception {
    mockMvc
        .perform(get("/api/calc/multiply?a=4&b=5"))
        .andExpect(status().isOk())
        .andExpect(content().string("20"));
  }
}
