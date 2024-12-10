package com.coding.practice.codes.durationCalculation.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
//LogicV4
public class LogicV4 {

    // Generalized method to calculate duration in months, days, years, and weeks between two dates
    public static DurationResult calculateDuration(String startDate, String endDate, DateTimeFormatter formatter) {
        try {
            // Parse the dates
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            // Calculate differences in various units
            long months = ChronoUnit.MONTHS.between(start, end);
            long days = ChronoUnit.DAYS.between(start, end);
            long years = ChronoUnit.YEARS.between(start, end);
            long weeks = ChronoUnit.WEEKS.between(start, end);

            return new DurationResult(months, days, years, weeks);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return new DurationResult(0, 0, 0, 0);
        }
    }
}
