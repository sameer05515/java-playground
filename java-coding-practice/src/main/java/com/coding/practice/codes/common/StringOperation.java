package com.coding.practice.codes.common;

import java.util.Optional;

@FunctionalInterface
public interface StringOperation<R> {
    R perform(Optional<String> input) throws InvalidInputException;

    default R performWithTitle(String title, String input) throws InvalidInputException {
        if (title != null) {
            System.out.print(title + "\t\t");
        }
        if (input == null || input.isEmpty()) {
            throw new InvalidInputException("Input is missing or empty");
        }
        return perform(Optional.of(input));
    }
}