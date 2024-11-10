package com.coding.practice.codes.durationCalculation;

import com.coding.practice.codes.durationCalculation.common.DurationInfo;
import com.coding.practice.codes.durationCalculation.common.DurationInfoParser;
import com.coding.practice.codes.durationCalculation.common.DurationResult;
import com.coding.practice.codes.durationCalculation.common.LogicV4;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class DurationCalculatorV6 {
    public static void main(String[] args) {
        String dateFormat = "dd MMM yyyy";

        List<DurationInfo> employmentDurations = DurationInfoParser.parse(new String[]{
                "01 Sep 2009 - 28 Feb 2011 - Employment - Worked in GreenApple",
                "22 Mar 2011 - 30 Apr 2012 - Employment - Worked in HCL Infosystems",
                "09 May 2012 - 06 Dec 2013 - Employment - Worked in Novelvox",
                "20 Jan 2014 - 27 Sep 2019 - Employment - Worked in Concentrix",
                "28 Sep 2019 - 07 Oct 2021 - Employment - Worked in Dhani stocks",
                "11 Oct 2021 - 02 Dec 2022 - Employment - Worked in RSystems",
                "06 Dec 2022 - 17 Feb 2023 - Employment - Worked in EVC Ventures",
                "07 Jun 2023 - 22 Feb 2024 - Employment - Worked in Zycus Infotech"
        }) ;

        System.out.println("Employment durations");
        GrossCalculationV6.calcAndPrint(employmentDurations, dateFormat);

        List<DurationInfo> gapDurations =DurationInfoParser.parse(new String[]{
                "01 Mar 2011 - 21 Mar 2011 - Gap - Awaiting Offer letter from HCL Infosystems",
                "01 May 2012 - 08 May 2012 - Gap - Awaiting Offer letter from Novelvox",
                "07 Dec 2013 - 19 Jan 2014 - Gap - Awaiting Offer letter from Concentrix",
                "08 Oct 2021 - 10 Oct 2021 - Gap - Awaiting Offer letter from RSystems",
                "03 Dec 2022 - 05 Dec 2022 - Gap - Awaiting Offer letter from EVC Ventures",
                "18 Feb 2023 - 06 Jun 2023 - Gap - Job search due to lay off from EVC Ventures",
                "23 Feb 2024 - 30 Nov 2024 - Gap - Health regain and Job search"
        }) ;

        System.out.println("\n\n\nGap durations");
        GrossCalculationV6.calcAndPrint(gapDurations, dateFormat);

        System.out.println("\n\n\nTotal Employment life");
        GrossCalculationV6.calcAndPrint(DurationInfoParser.parse(new String[]{"01 Sep 2009 - 30 Nov 2024 - WHOLE_EMPLOYMENT - NA"}), dateFormat);


    }
}

class GrossCalculationV6 {

    public static void calcAndPrint(List<DurationInfo> durations, String dateFormat) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

        // Calculate total months and days using Streams
        DurationResult totalDuration = durations.stream()
                .map(duration -> {
                    //String[] dates = duration.split("-");
                    return LogicV4.calculateDuration(duration.startDate(), duration.endDate(), formatter);
                })
                .reduce(new DurationResult(0, 0, 0, 0), DurationResult::add);

        // Display individual durations
        System.out.println("""
                Gap/Employment \t| Duration \t| Reason/Remarks \t| Calculated duration
                ----------\t|---------------\t|----------------\t|------------------""");
        durations.stream().forEach(duration -> {
            //String[] dates = duration.split("-");
            DurationResult result = LogicV4.calculateDuration(duration.startDate(), duration.endDate(), formatter);
//            System.out.printf("From %s to %s: %d months, %d days, %d years, %d weeks%n",
//                    duration.startDate(), duration.endDate(), result.months(), result.days(), result.years(), result.weeks());
            System.out.printf("%s | %s to %s | %s | %d months, %d days, %d years, %d weeks%n",
                    duration.type(),
                    duration.startDate(), duration.endDate(),
                    duration.remarks(),
                    result.months(), result.days(), result.years(), result.weeks()
                    );
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




