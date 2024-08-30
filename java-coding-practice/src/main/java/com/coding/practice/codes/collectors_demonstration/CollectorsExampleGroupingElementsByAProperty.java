package com.coding.practice.codes.collectors_demonstration;

import com.coding.practice.codes.collectors_demonstration.pojo.Product;

import java.util.*;
import java.util.stream.Collectors;
public class CollectorsExampleGroupingElementsByAProperty {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
                new Product("Laptop", "Electronics"),
                new Product("Shirt", "Clothing"),
                new Product("Mobile", "Electronics"),
                new Product("Jeans", "Clothing")
        );

        Map<String, List<Product>> productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        System.out.println(productsByCategory);
    }
}
