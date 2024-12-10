package com.coding.practice.codes.lastCtcCalculation;

import com.coding.practice.codes.lastCtcCalculation.common.Company;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;
import static com.coding.practice.codes.lastCtcCalculation.common.Constants.companyData;

public class LastCTCCalculatorV2 {

    private static final String REMARKS = "TBD";

    public static void main(String[] args) {
        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Convert JSON array to List of Company objects
            List<Company> companies = objectMapper.readValue(companyData, new TypeReference<>() {
            });

            // Calculate the percentage hike for each company, starting from the second entry
            IntStream.range(1, companies.size()).forEach(i -> {
                double prevCompCTC = companies.get(i - 1).getLastCTC();
                double currentCTC = companies.get(i).getLastCTC();
                double hike = (currentCTC - prevCompCTC) * 100 / prevCompCTC;
                companies.get(i).setPercentageHike(roundTo3Decimals(hike));
            });

            // Print the result
            System.out.println("# Last CTC and Hike calculation");
            printHeader();
            companies.forEach(LastCTCCalculatorV2::printDetails);

        } catch (IOException e) {
            System.err.println("Error while parsing company data: " + e.getMessage());
        }
    }

    private static double roundTo3Decimals(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }

    private static void printHeader() {
        System.out.println("""
                Company                                        | Last Designation                          | Last CTC (LPA)     | Percentage Hike (%) | Remarks
                -----------------------------------------------|------------------------------------------|--------------------|---------------------|----------------""");
    }

    private static void printDetails(Company company) {
        System.out.printf("%-47s | %-40s | %-18.3f | %-19.3f | %s%n",
                company.getName(),
                company.getLastDesignation() != null ? company.getLastDesignation() : "N/A",
                company.getLastCTC(),
                company.getPercentageHike(),
                REMARKS
        );
    }
}
