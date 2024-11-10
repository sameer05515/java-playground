package com.coding.practice.codes.durationCalculation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class DurationCalculator {

    // Method to calculate duration in months and days between two dates
    public static int[] calculateDuration(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        
        try {
            // Parse the dates
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            // Calculate the difference in days
            long diffInDays = ChronoUnit.DAYS.between(start, end);

            // Calculate the difference in months
            long diffInMonths = ChronoUnit.MONTHS.between(start, end);

            return new int[]{(int) diffInMonths, (int) diffInDays};
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return new int[]{0, 0};
        }
    }

    public static void main(String[] args) {
        // Durations for the given date ranges
        int[] duration1 = calculateDuration("01 Sep 2009", "28 Feb 2011");
        int[] duration2 = calculateDuration("22 Mar 2011", "30 Apr 2012");

        // Extracting individual results
        int duration1Months = duration1[0];
        int duration1Days = duration1[1];
        int duration2Months = duration2[0];
        int duration2Days = duration2[1];

        // Calculate total duration
        int totalMonths = duration1Months + duration2Months;
        int totalDays = duration1Days + duration2Days;

        // Display results
        System.out.println("From 01 Sep 2009 to 28 Feb 2011: " + duration1Months + " months, " + duration1Days + " days");
        System.out.println("From 22 Mar 2011 to 30 Apr 2012: " + duration2Months + " months, " + duration2Days + " days");
        System.out.println("Total Duration: " + totalMonths + " months, " + totalDays + " days");
    }
}
