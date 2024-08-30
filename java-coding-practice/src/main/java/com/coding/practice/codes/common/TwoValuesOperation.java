package com.coding.practice.codes.common;

import java.util.Optional;

@FunctionalInterface
public interface TwoValuesOperation<I, R> {
    R perform(I val1, I val2) throws InvalidInputException;

    default R performWithTitle(String title, I input1,I input2) throws InvalidInputException {
        if (title != null) {
            System.out.print(title + "\t\t");
        }
        if (input1 == null || input2 == null) {
            throw new InvalidInputException("Input is missing or empty");
        }
        return perform(input1, input2);
    }
}