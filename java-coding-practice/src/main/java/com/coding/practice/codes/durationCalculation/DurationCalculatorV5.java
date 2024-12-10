package com.coding.practice.codes.durationCalculation;

import com.coding.practice.codes.durationCalculation.common.DurationResult;
import com.coding.practice.codes.durationCalculation.common.LogicV4;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Stream;

public class DurationCalculatorV5 {
    public static void main(String[] args) {
        String dateFormat = "dd MMM yyyy";

        String[] employmentDurations = {
                "01 Sep 2009 - 28 Feb 2011",
                "22 Mar 2011 - 30 Apr 2012",
                "09 May 2012 - 06 Dec 2013",
                "20 Jan 2014 - 27 Sep 2019",
                "28 Sep 2019 - 07 Oct 2021",
                "11 Oct 2021 - 02 Dec 2022",
                "06 Dec 2022 - 17 Feb 2023",
                "07 Jun 2023 - 22 Feb 2024"
        };

        System.out.println("Employment durations");
        GrossCalculation.calcAndPrint(employmentDurations, dateFormat);

        String[] gapDurations = {
                "01 Mar 2011 - 21 Mar 2011",
                "01 May 2012 - 08 May 2012",
                "07 Dec 2013 - 19 Jan 2014",
                "08 Oct 2021 - 10 Oct 2021",
                "03 Dec 2022 - 05 Dec 2022",
                "18 Feb 2023 - 06 Jun 2023",
                "23 Feb 2024 - 30 Nov 2024"
        };

        System.out.println("\n\n\nGap durations");
        GrossCalculation.calcAndPrint(gapDurations, dateFormat);

        System.out.println("\n\n\nTotal Employment life");
        GrossCalculation.calcAndPrint(new String[]{"01 Sep 2009 - 30 Nov 2024"}, dateFormat);


    }
}

 class GrossCalculation {

    public static void calcAndPrint(String[] durations, String dateFormat) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

        // Calculate total months and days using Streams
        DurationResult totalDuration = Stream.of(durations)
                .map(duration -> {
                    String[] dates = duration.split("-");
                    return LogicV4.calculateDuration(dates[0].trim(), dates[1].trim(), formatter);
                })
                .reduce(new DurationResult(0, 0, 0, 0), DurationResult::add);

        // Display individual durations
        System.out.println("---- Individual Durations ----");
        Stream.of(durations).forEach(duration -> {
            String[] dates = duration.split("-");
            DurationResult result = LogicV4.calculateDuration(dates[0].trim(), dates[1].trim(), formatter);
            System.out.printf("From %s to %s: %d months, %d days, %d years, %d weeks%n",
                    dates[0], dates[1], result.months(), result.days(), result.years(), result.weeks());
        });

        // Display total duration
        System.out.println("---- Total Duration ----");
        System.out.printf("Total Duration: %d months or %d days or %d years or %d weeks %n",
                totalDuration.months(), totalDuration.days(), totalDuration.years(), totalDuration.weeks());

        // Calculate total years with 3 decimal precision
        double totalYearsFromMonths = roundTo3Decimals(totalDuration.months() / 12.0);
        double totalYearsFromDays = roundTo3Decimals(totalDuration.days() / 365.0);
        double totalYearsFromWeeks = roundTo3Decimals(totalDuration.weeks() / 52.0);

        // Display derived years with floating-point accuracy
        System.out.printf("Total years (from months): %.3f years%n", totalYearsFromMonths);
        System.out.printf("Total years (from days): %.3f years%n", totalYearsFromDays);
        System.out.printf("Total years (from weeks): %.3f years%n", totalYearsFromWeeks);
    }

    // Utility method to round double values to 3 decimal places
    private static double roundTo3Decimals(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}

//class LogicV5 {
//
//    // Generalized method to calculate duration in months, days, years, and weeks between two dates
//    public static DurationResult calculateDuration(String startDate, String endDate, DateTimeFormatter formatter) {
//        try {
//            // Parse the dates
//            LocalDate start = LocalDate.parse(startDate, formatter);
//            LocalDate end = LocalDate.parse(endDate, formatter);
//
//            // Calculate differences in various units
//            long months = ChronoUnit.MONTHS.between(start, end);
//            long days = ChronoUnit.DAYS.between(start, end);
//            long years = ChronoUnit.YEARS.between(start, end);
//            long weeks = ChronoUnit.WEEKS.between(start, end);
//
//            return new DurationResult(months, days, years, weeks);
//        } catch (DateTimeParseException e) {
//            System.err.println("Error parsing date: " + e.getMessage());
//            return new DurationResult(0, 0, 0, 0);
//        }
//    }
//}


