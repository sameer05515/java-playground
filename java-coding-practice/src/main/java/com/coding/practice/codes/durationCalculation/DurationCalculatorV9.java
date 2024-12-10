package com.coding.practice.codes.durationCalculation;

import com.coding.practice.codes.durationCalculation.common.DurationInfo;
import com.coding.practice.codes.durationCalculation.common.DurationInfoParser;
import com.coding.practice.codes.durationCalculation.common.DurationResultV2;
import com.coding.practice.codes.durationCalculation.common.DurationType;
import com.coding.practice.codes.durationCalculation.common.LogicV5;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DurationCalculatorV9 {

    public static void main(String[] args) {
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
        });

        System.out.println("# Normalized Gap Analysis");
        GrossCalculationV9.calculateAndPrintResults(employmentDurations);
    }
}

class GrossCalculationV9 {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
    private static final int MONTHS_IN_YEAR = 12;
    private static final int DAYS_IN_YEAR = 365;
    private static final int WEEKS_IN_YEAR = 52;

    /**
     * Calculates and prints the detailed duration results and total summaries for different duration types.
     */
    public static void calculateAndPrintResults(List<DurationInfo> durations) {
        printHeader();

        // Calculate and display individual durations
        List<DurationResultV2> durationResults = durations.stream()
                .map(duration -> {
                    DurationResultV2 result = LogicV5.calculateDuration(duration, FORMATTER);
                    printDuration(duration, result);
                    return result;
                })
                .collect(Collectors.toList());

        // Calculate totals
        DurationResultV2 totalEmployment = calculateTotal(durationResults, DurationType.EMPLOYMENT);
        DurationResultV2 totalGap = calculateTotal(durationResults, DurationType.GAP);
        DurationResultV2 totalWholeEmployment = calculateTotal(durationResults, DurationType.WHOLE_EMPLOYMENT);

        // Print total summary
        System.out.println("\n# Total Duration");
        printTotalHeader();
        printTotalDurationSummary(DurationType.EMPLOYMENT, totalEmployment);
        printTotalDurationSummary(DurationType.GAP, totalGap);
        printTotalDurationSummary(DurationType.WHOLE_EMPLOYMENT, totalWholeEmployment);
    }

    private static void printHeader() {
        System.out.println("""
                Type              | Start Date - End Date                | Remarks                          | Duration
                ------------------|-------------------------------------|---------------------------------|----------------""");
    }

    private static void printTotalHeader() {
        System.out.println("""
                Type              | Duration                           | years (from months)             | years (from days)               | years (from weeks)
                ------------------|------------------------------------|---------------------------------|---------------------------------|------------------""");
    }

    private static void printDuration(DurationInfo duration, DurationResultV2 result) {
        System.out.printf("%-18s | %-35s | %-30s | %d months, %d days, %d years, %d weeks%n",
                duration.type(),
                duration.startDate() + " to " + duration.endDate(),
                duration.remarks(),
                result.months(), result.days(), result.years(), result.weeks()
        );
    }

    private static DurationResultV2 calculateTotal(List<DurationResultV2> durationResults, DurationType type) {
        return durationResults.stream()
                .filter(result -> type.equals(result.durationInfo().type()))
                .reduce(new DurationResultV2(null, 0, 0, 0, 0), DurationResultV2::add);
    }

    private static void printTotalDurationSummary(DurationType type, DurationResultV2 result) {
        double totalYearsFromMonths = convertToYears(result.months(), MONTHS_IN_YEAR);
        double totalYearsFromDays = convertToYears(result.days(), DAYS_IN_YEAR);
        double totalYearsFromWeeks = convertToYears(result.weeks(), WEEKS_IN_YEAR);

        System.out.printf("%-18s | %4d months or %5d days or %3d years or %4d weeks | %.3f years | %.3f years | %.3f years%n",
                type,
                result.months(), result.days(), result.years(), result.weeks(),
                totalYearsFromMonths, totalYearsFromDays, totalYearsFromWeeks
        );
    }

    private static double convertToYears(long value, int conversionFactor) {
        return roundTo3Decimals((double) value / conversionFactor);
    }

    private static double roundTo3Decimals(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}
