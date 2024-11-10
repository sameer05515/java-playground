package com.coding.practice.codes.durationCalculation;

import com.coding.practice.codes.durationCalculation.common.DurationInfo;
import com.coding.practice.codes.durationCalculation.common.DurationInfoParser;
import com.coding.practice.codes.durationCalculation.common.DurationResultV2;
import com.coding.practice.codes.durationCalculation.common.DurationType;
import com.coding.practice.codes.durationCalculation.common.LogicV5;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class DurationCalculatorV7 {
    public static void main(String[] args) {
        String dateFormat = "dd MMM yyyy";

        List<DurationInfo> employmentDurations = DurationInfoParser.parse(new String[]{
                "01 Sep 2009 - 28 Feb 2011 - Employment - Worked in GreenApple",
                "01 Mar 2011 - 21 Mar 2011 - Gap - Awaiting Offer letter from HCL Infosystems",
                "22 Mar 2011 - 30 Apr 2012 - Employment - Worked in HCL Infosystems",
                "01 May 2012 - 08 May 2012 - Gap - Awaiting Offer letter from Novelvox",
                "09 May 2012 - 06 Dec 2013 - Employment - Worked in Novelvox",
                "07 Dec 2013 - 19 Jan 2014 - Gap - Awaiting Offer letter from Concentrix",
                "20 Jan 2014 - 27 Sep 2019 - Employment - Worked in Concentrix",
                "28 Sep 2019 - 07 Oct 2021 - Employment - Worked in Dhani stocks",
                "08 Oct 2021 - 10 Oct 2021 - Gap - Awaiting Offer letter from RSystems",
                "11 Oct 2021 - 02 Dec 2022 - Employment - Worked in RSystems",
                "03 Dec 2022 - 05 Dec 2022 - Gap - Awaiting Offer letter from EVC Ventures",
                "06 Dec 2022 - 17 Feb 2023 - Employment - Worked in EVC Ventures",
                "18 Feb 2023 - 06 Jun 2023 - Gap - Job search due to lay off from EVC Ventures",
                "07 Jun 2023 - 22 Feb 2024 - Employment - Worked in Zycus Infotech",
                "23 Feb 2024 - 30 Nov 2024 - Gap - Health regain and Job search",
                "01 Sep 2009 - 30 Nov 2024 - WHOLE_EMPLOYMENT - NA"
        }) ;

        System.out.println("# Normalized Gap Analysis");
        GrossCalculationV7.calcAndPrint(employmentDurations, dateFormat);
    }
}

class GrossCalculationV7 {

    public static void calcAndPrint(List<DurationInfo> durations, String dateFormat) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

        /*
          1. Calculate total months and days using Streams

          2. Display individual durations

          */
        System.out.println("""
                Gap/Employment \t\t\t| Duration \t\t\t| Reason/Remarks \t\t\t| Calculated duration
                ----------\t\t\t| ---------------\t\t\t| ----------------\t\t\t| ------------------""");
        List<DurationResultV2> durationResults = durations.stream()
                .map(duration -> {
                    DurationResultV2 result=  LogicV5.calculateDuration(duration, formatter);
                    System.out.printf("%s | %s to %s | %s | %d months, %d days, %d years, %d weeks%n",
                            duration.type(),
                            duration.startDate(), duration.endDate(),
                            duration.remarks(),
                            result.months(), result.days(), result.years(), result.weeks()
                    );
                    return  result;
                }).toList();



        DurationResultV2 totalEmploymentDuration = durationResults.stream()
                .filter(durationResult -> DurationType.EMPLOYMENT.equals(durationResult.durationInfo().type()))
                .reduce(new DurationResultV2(null,0, 0, 0, 0), DurationResultV2::add);

        DurationResultV2 totalGapDuration = durationResults.stream()
                .filter(durationResult -> DurationType.GAP.equals(durationResult.durationInfo().type()))
                .reduce(new DurationResultV2(null,0, 0, 0, 0), DurationResultV2::add);

        DurationResultV2 totalWEDuration = durationResults.stream()
                .filter(durationResult -> DurationType.WHOLE_EMPLOYMENT.equals(durationResult.durationInfo().type()))
                .reduce(new DurationResultV2(null,0, 0, 0, 0), DurationResultV2::add);

        System.out.println("# Total Duration");
        System.out.println("""
                Type \t\t\t| Duration \t\t\t| years (from months) \t\t\t| years (from days) \t\t\t| years (from weeks)
                ----------\t\t\t| ---------------\t\t\t| ----------------\t\t\t| ------------------\t\t\t| ------------------""");
        printTotalDuration(DurationType.EMPLOYMENT, totalEmploymentDuration);
        printTotalDuration(DurationType.GAP, totalGapDuration);
        printTotalDuration(DurationType.WHOLE_EMPLOYMENT, totalWEDuration);
    }

    // Utility method to round double values to 3 decimal places


    private static void printTotalDuration(DurationType type, DurationResultV2 result){

        // Calculate total years with 3 decimal precision
        double totalYearsFromMonths = roundTo3Decimals(result.months() / 12.0);
        double totalYearsFromDays = roundTo3Decimals(result.days() / 365.0);
        double totalYearsFromWeeks = roundTo3Decimals(result.weeks() / 52.0);

        System.out.printf(" %s | %d months or %d days or %d years or %d weeks | %.3f years | %.3f years | %.3f years %n",
                type,
                result.months(), result.days(), result.years(), result.weeks(),
                totalYearsFromMonths,
                totalYearsFromDays,
                totalYearsFromWeeks
                );
    }

    private static double roundTo3Decimals(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}




