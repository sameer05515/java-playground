package com.coding.practice.codes.durationCalculation.common;

public record DurationResultV2(DurationInfo durationInfo,long months, long days, long years, long weeks) {

    public DurationResultV2 add(DurationResultV2 other) {
        return new DurationResultV2(
                null,
                this.months + other.months,
                this.days + other.days,
                this.years + other.years,
                this.weeks + other.weeks
        );
    }
}
