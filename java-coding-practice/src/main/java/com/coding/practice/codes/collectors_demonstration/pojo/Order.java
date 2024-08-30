package com.coding.practice.codes.collectors_demonstration.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String product;
    private int quantity;
}