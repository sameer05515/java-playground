package com.coding.practice.codes.lastCtcCalculation.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Company {
    private String name;
    private double lastCTC;
    private String lastDesignation;
    private double percentageHike;
}
