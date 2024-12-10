package com.coding.practice.codes.durationCalculation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DurationCalculatorV3 {

    public static void main(String[] args) {
        // Specify the date format you are using
        String dateFormat = "dd MMM yyyy";

        String[] durations={"01 Sep 2009 - 28 Feb 2011", "22 Mar 2011 - 30 Apr 2012"};

        long ttlMonths = 0;
        long ttlDays =0;


        for(String durationStr:durations){
            String[] durationArr=durationStr.split("-");
            Map<String, Long> duration = LogicV3.calculateDuration(durationArr[0].trim(), durationArr[1].trim(), dateFormat);
            System.out.println("From "+durationArr[0]+" to "+durationArr[1]+": " + duration.get("months") + " months, " + duration.get("days") + " days");
            ttlMonths+=duration.get("months");
            ttlDays+=duration.get("days");
        }

        System.out.println("Total Duration: " + ttlMonths + " months, " + ttlDays + " days");
    }
}

class LogicV3 {
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
