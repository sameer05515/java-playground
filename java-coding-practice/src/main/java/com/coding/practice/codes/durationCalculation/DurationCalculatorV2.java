package com.coding.practice.codes.durationCalculation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DurationCalculatorV2 {

    public static void main(String[] args) {
        // Specify the date format you are using
        String dateFormat = "dd MMM yyyy";

        // Durations for the given date ranges
        Map<String, Long> duration1 = LogicV2.calculateDuration("01 Sep 2009", "28 Feb 2011", dateFormat);
        Map<String, Long> duration2 = LogicV2.calculateDuration("22 Mar 2011", "30 Apr 2012", dateFormat);

        // Extracting individual results
        long duration1Months = duration1.get("months");
        long duration1Days = duration1.get("days");
        long duration2Months = duration2.get("months");
        long duration2Days = duration2.get("days");

        // Calculate total duration
        long totalMonths = duration1Months + duration2Months;
        long totalDays = duration1Days + duration2Days;

        // Display results
        System.out.println("From 01 Sep 2009 to 28 Feb 2011: " + duration1Months + " months, " + duration1Days + " days");
        System.out.println("From 22 Mar 2011 to 30 Apr 2012: " + duration2Months + " months, " + duration2Days + " days");
        System.out.println("Total Duration: " + totalMonths + " months, " + totalDays + " days");
    }
}

class LogicV2 {
    // Generalized method to calculate duration in years, months, weeks, and days between two dates
    public static Map<String, Long> calculateDuration(String startDate, String endDate, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);
        Map<String, Long> durationMap = new HashMap<>();

        try {
            // Parse the dates
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            // Calculate differences in various units
            long diffInYears = ChronoUnit.YEARS.between(start, end);
            long diffInMonths = ChronoUnit.MONTHS.between(start, end);
            long diffInWeeks = ChronoUnit.WEEKS.between(start, end);
            long diffInDays = ChronoUnit.DAYS.between(start, end);

            // Populate the results in a map
            durationMap.put("years", diffInYears);
            durationMap.put("months", diffInMonths);
            durationMap.put("weeks", diffInWeeks);
            durationMap.put("days", diffInDays);
            
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            // If parsing fails, return all units as 0
            durationMap.put("years", 0L);
            durationMap.put("months", 0L);
            durationMap.put("weeks", 0L);
            durationMap.put("days", 0L);
        }
        
        return durationMap;
    }
}
