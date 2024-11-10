package com.coding.practice.codes.durationCalculation.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class LogicV5 {

    // Generalized method to calculate duration in months, days, years, and weeks between two dates
    public static DurationResultV2 calculateDuration(DurationInfo durationInfo, DateTimeFormatter formatter) {
        try {
            // Parse the dates
            LocalDate start = LocalDate.parse(durationInfo.startDate(), formatter);
            LocalDate end = LocalDate.parse(durationInfo.endDate(), formatter);

            // Calculate differences in various units
            long months = ChronoUnit.MONTHS.between(start, end);
            long days = ChronoUnit.DAYS.between(start, end)+1;
            long years = ChronoUnit.YEARS.between(start, end);
            long weeks = ChronoUnit.WEEKS.between(start, end);

            return new DurationResultV2(durationInfo,months, days, years, weeks);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return new DurationResultV2(durationInfo,0, 0, 0, 0);
        }
    }
}
