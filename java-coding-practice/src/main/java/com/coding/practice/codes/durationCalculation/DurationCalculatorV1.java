package com.coding.practice.codes.durationCalculation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Map;

public class DurationCalculatorV1 {

    public static void main(String[] args) {
        // Durations for the given date ranges
        Map<String, Integer> duration1 = LogicV1.calculateDuration("01 Sep 2009", "28 Feb 2011");
        Map<String, Integer> duration2 = LogicV1.calculateDuration("22 Mar 2011", "30 Apr 2012");

        // Extracting individual results
        int duration1Months = duration1.get("durationMonths");
        int duration1Days = duration1.get("durationDays");
        int duration2Months = duration2.get("durationMonths");
        int duration2Days = duration2.get("durationDays");

        // Calculate total duration
        int totalMonths = duration1Months + duration2Months;
        int totalDays = duration1Days + duration2Days;

        // Display results
        System.out.println("From 01 Sep 2009 to 28 Feb 2011: " + duration1Months + " months, " + duration1Days + " days");
        System.out.println("From 22 Mar 2011 to 30 Apr 2012: " + duration2Months + " months, " + duration2Days + " days");
        System.out.println("Total Duration: " + totalMonths + " months, " + totalDays + " days");
    }
}

class LogicV1{
    // Method to calculate duration in months and days between two dates
    public static Map<String, Integer> calculateDuration(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

        try {
            // Parse the dates
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            // Calculate the difference in days
            long diffInDays = ChronoUnit.DAYS.between(start, end);

            // Calculate the difference in months
            long diffInMonths = ChronoUnit.MONTHS.between(start, end);

//            return new int[]{(int) diffInMonths, (int) diffInDays};
            return Map.of("durationMonths",(int) diffInMonths,"durationDays",(int) diffInDays);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
//            return new int[]{0, 0};
            return Map.of("durationMonths",0,"durationDays",0);
        }
    }
}
