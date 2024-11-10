package com.coding.practice.codes.durationCalculation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

public class DurationCalculatorV4 {

    public static void main(String[] args) {
        // Specify the date format you are using
        String dateFormat = "dd MMM yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

        String[] durations = {"01 Sep 2009 - 28 Feb 2011", "22 Mar 2011 - 30 Apr 2012"};

        // Calculate total months and days using Streams
        DurationResult totalDuration = Stream.of(durations)
                .map(duration -> {
                    String[] dates = duration.split("-");
                    return LogicV4.calculateDuration(dates[0].trim(), dates[1].trim(), formatter);
                })
                .reduce(new DurationResult(0, 0), DurationResult::add);

        // Display individual durations
        Stream.of(durations).forEach(duration -> {
            String[] dates = duration.split("-");
            DurationResult result = LogicV4.calculateDuration(dates[0].trim(), dates[1].trim(), formatter);
            System.out.printf("From %s to %s: %d months, %d days%n", dates[0], dates[1], result.getMonths(), result.getDays());
        });

        // Display total duration
        System.out.println("Total Duration: " + totalDuration.getMonths() + " months, " + totalDuration.getDays() + " days");
    }
}

class LogicV4 {

    // Generalized method to calculate duration in months and days between two dates
    public static DurationResult calculateDuration(String startDate, String endDate, DateTimeFormatter formatter) {
        try {
            // Parse the dates
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            // Calculate differences in months and days
            long months = ChronoUnit.MONTHS.between(start, end);
            long days = ChronoUnit.DAYS.between(start, end);

            return new DurationResult(months, days);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return new DurationResult(0, 0);
        }
    }
}

class DurationResult {
    private final long months;
    private final long days;

    public DurationResult(long months, long days) {
        this.months = months;
        this.days = days;
    }

    public long getMonths() {
        return months;
    }

    public long getDays() {
        return days;
    }

    public DurationResult add(DurationResult other) {
        return new DurationResult(this.months + other.months, this.days + other.days);
    }
}
