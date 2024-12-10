package com.coding.practice.codes.durationCalculation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GapDurationCalculatorV2 {

    public static void main(String[] args) {
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

        // Generate and print gap durations
        String[] gapDurations = calculateGapDurations(employmentDurations);
        System.out.println("Gap Durations:");
        for (String gap : gapDurations) {
            System.out.println(gap);
        }
    }

    public static String[] calculateGapDurations(String[] employmentDurations) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

        // Use IntStream to iterate over the indices and find gaps
        List<String> gapDurationsList = IntStream.range(0, employmentDurations.length)
                .mapToObj(i -> {
                    // Extract current end date
                    LocalDate currentEndDate = parseDate(employmentDurations[i].split("-")[1].trim(), formatter);

                    // Extract next start date or default to LocalDate.now() if it's the last record
                    LocalDate nextStartDate = (i == employmentDurations.length - 1)
                            ? LocalDate.now()
                            : parseDate(employmentDurations[i + 1].split("-")[0].trim(), formatter);

                    // Calculate and format the gap if it exists
                    return currentEndDate.isBefore(nextStartDate.minusDays(1))
                            ? formatGap(currentEndDate.plusDays(1), nextStartDate.minusDays(1), formatter)
                            : null;
                })
                .filter(Objects::nonNull) // Remove null values
                .toList();

        return gapDurationsList.toArray(new String[0]);
    }

    private static LocalDate parseDate(String date, DateTimeFormatter formatter) {
        return LocalDate.parse(date, formatter);
    }

    private static String formatGap(LocalDate startDate, LocalDate endDate, DateTimeFormatter formatter) {
        return String.format("%s - %s", startDate.format(formatter), endDate.format(formatter));
    }
}
