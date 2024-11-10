package com.coding.practice.codes.durationCalculation;

import com.coding.practice.codes.durationCalculation.common.DurationResult;
import com.coding.practice.codes.durationCalculation.common.LogicV4;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Stream;

public class DurationCalculatorV4 {

    public static void main(String[] args) {
        // Specify the date format you are using
        String dateFormat = "dd MMM yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

        String[] durations = {
                "01 Sep 2009 - 28 Feb 2011",
                "22 Mar 2011 - 30 Apr 2012",
                "09 May 2012 - 06 Dec 2013",
                "20 Jan 2014 - 27 Sep 2019",
                "11 Oct 2021 - 02 Dec 2022",
                "06 Dec 2022 - 17 Feb 2023",
                "07 Jun 2023 - 22 Feb 2024",
//                "01 Sep 2009 - 30 Nov 2024"
        };

        // Calculate total months and days using Streams
        DurationResult totalDuration = Stream.of(durations)
                .map(duration -> {
                    String[] dates = duration.split("-");
                    return LogicV4.calculateDuration(dates[0].trim(), dates[1].trim(), formatter);
                })
                .reduce(new DurationResult(0, 0,0,0), DurationResult::add);

        // Display individual durations
        Stream.of(durations).forEach(duration -> {
            String[] dates = duration.split("-");
            DurationResult result = LogicV4.calculateDuration(dates[0].trim(), dates[1].trim(), formatter);
            System.out.printf("From %s to %s: %d months, %d days, %d years, %d weeks%n", dates[0], dates[1], result.months(), result.days(), result.years(), result.weeks());
        });

        // Display total duration
        System.out.printf("Total Duration: %d months or %d days or %d years or %d weeks %n", totalDuration.months(), totalDuration.days(), totalDuration.years(), totalDuration.weeks());
        System.out.println("Total years: derived from total months"+(totalDuration.months()/12));
        System.out.println("Total years: derived from total days"+(totalDuration.days()/365));
        System.out.println("Total years: derived from total weeks"+(totalDuration.weeks()/52));

    }
}

//class LogicV4 {
//
//    // Generalized method to calculate duration in months and days between two dates
//    public static DurationResult calculateDuration(String startDate, String endDate, DateTimeFormatter formatter) {
//        try {
//            // Parse the dates
//            LocalDate start = LocalDate.parse(startDate, formatter);
//            LocalDate end = LocalDate.parse(endDate, formatter);
//
//            // Calculate differences in months and days
//            long months = ChronoUnit.MONTHS.between(start, end);
//            long days = ChronoUnit.DAYS.between(start, end);
//            long years = ChronoUnit.YEARS.between(start, end);
//            long weeks = ChronoUnit.WEEKS.between(start, end);
//
//            return new DurationResult(months, days,years,weeks);
//        } catch (DateTimeParseException e) {
//            System.err.println("Error parsing date: " + e.getMessage());
//            return new DurationResult(0, 0,0,0);
//        }
//    }
//}


//record DurationResult(long months, long days, long years, long weeks) {
//
//    public DurationResult add(DurationResult other) {
//        return new DurationResult(
//                this.months + other.months,
//                this.days + other.days,
//                this.years + other.years,
//                this.weeks + other.weeks
//        );
//    }
//}
