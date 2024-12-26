package com.prem.junit.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CalculatorController1.class)
public class CalculatorController1Test {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void testAddPost() throws Exception {
    CalcRequest request = new CalcRequest();
    request.setA(3);
    request.setB(4);

    mockMvc
        .perform(
            post("/api/calc/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().string("7"));
  }

  @Test
  void testSubtractPost() throws Exception {
    CalcRequest request = new CalcRequest();
    request.setA(10);
    request.setB(6);

    mockMvc
        .perform(
            post("/api/calc/subtract")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().string("4"));
  }

  @Test
  void testMultiplyPost() throws Exception {
    CalcRequest request = new CalcRequest();
    request.setA(5);
    request.setB(5);

    mockMvc
        .perform(
            post("/api/calc/multiply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().string("25"));
  }
}
