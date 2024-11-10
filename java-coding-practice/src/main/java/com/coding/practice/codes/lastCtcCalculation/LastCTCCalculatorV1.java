package com.coding.practice.codes.lastCtcCalculation;

import com.coding.practice.codes.lastCtcCalculation.common.Company;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import static com.coding.practice.codes.lastCtcCalculation.common.Constants.companyData;

public class LastCTCCalculatorV1 {
    public static void main(String[] args) throws IOException {
        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert JSON array to List of Company objects
        List<Company> companies = objectMapper.readValue(companyData, new TypeReference<List<Company>>() {
        });
        for (int i = 1; i < companies.size(); i++) {
            double prevCompCTC = companies.get(i - 1).getLastCTC();
            double currentCTC = companies.get(i).getLastCTC();
            double hike = (currentCTC - prevCompCTC) * 100 / prevCompCTC;
            companies.get(i).setPercentageHike(roundTo3Decimals(hike));
        }

        // Print the result
//        companies.forEach(System.out::println);
        System.out.println("# Last CTC and Hike calculation");
        printHeader();
        companies.forEach(LastCTCCalculatorV1::printDetails);
    }

    private static double roundTo3Decimals(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }

    private static void printHeader() {
        System.out.println("""
                Company              | Last Designation                | Last CTC                | Percentage Hike                          | Remarks
                ------------------|-------------------------------------|-------------------------------------|---------------------------------|----------------""");
    }

    private static void printDetails(Company company) {
        System.out.printf("%-50s |%-50s | %.3f LPA | %.3f percent | %s %n",
                company.getName(),
                company.getLastDesignation(),
                company.getLastCTC(),
                company.getPercentageHike(),
                "TBD"
        );
    }
}
