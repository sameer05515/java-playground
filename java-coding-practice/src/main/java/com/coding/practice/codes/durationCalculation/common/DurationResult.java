package com.coding.practice.codes.durationCalculation.common;

public record DurationResult(long months, long days, long years, long weeks) {

    public DurationResult add(DurationResult other) {
        return new DurationResult(
                this.months + other.months,
                this.days + other.days,
                this.years + other.years,
                this.weeks + other.weeks
        );
    }
}