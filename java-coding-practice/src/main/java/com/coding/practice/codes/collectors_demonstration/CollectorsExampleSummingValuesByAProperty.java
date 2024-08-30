package com.coding.practice.codes.collectors_demonstration;





import com.coding.practice.codes.collectors_demonstration.pojo.Order;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsExampleSummingValuesByAProperty {
    public static void main(String[] args) {
        List<Order> orders = Arrays.asList(
                new Order("Laptop", 2),
                new Order("Mobile", 5),
                new Order("Laptop", 3),
                new Order("Headphones", 7)
        );

        Map<String, Integer> totalQuantityByProduct = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct, Collectors.summingInt(Order::getQuantity)));

        System.out.println(totalQuantityByProduct);
    }
}
