package com.coding.practice.codes.durationCalculation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GapDurationCalculator {

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

        // Create gapDurations array
        String[] gapDurations = calculateGapDurations(employmentDurations);
        
        // Print the gapDurations
        System.out.println("Gap Durations:");
        for (String gap : gapDurations) {
            System.out.println(gap);
        }
    }

    public static String[] calculateGapDurations(String[] employmentDurations) {
        List<String> gapDurationsList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

        for (int i = 0; i <= employmentDurations.length - 1; i++) {
            // Extract the end date of the current duration
            String currentEndDateStr = employmentDurations[i].split("-")[1].trim();
            LocalDate currentEndDate = LocalDate.parse(currentEndDateStr, formatter);

            // Extract the start date of the next duration

            LocalDate nextStartDate = null;
            if(i == employmentDurations.length - 1){
                nextStartDate = LocalDate.now();

            }else {
                String nextStartDateStr = employmentDurations[i + 1].split("-")[0].trim();
                nextStartDate = LocalDate.parse(nextStartDateStr, formatter);
            }



            // Calculate the gap
            if (currentEndDate.isBefore(nextStartDate.minusDays(1))) {
                String gapDuration = String.format("%s - %s", currentEndDate.plusDays(1).format(formatter), nextStartDate.minusDays(1).format(formatter));
                gapDurationsList.add(gapDuration);
            }
        }

        // Convert List to Array
        return gapDurationsList.toArray(new String[0]);
    }
}
