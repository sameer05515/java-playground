package com.coding.practice.codes.durationCalculation.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DurationInfoParser {
    public static List<DurationInfo> parse(String[] raw){
        if(raw == null || raw.length == 0){
            return new ArrayList<>();
        }

        return Stream.of(raw).map(duration -> {
            String[] info = duration.split("-");
            return new DurationInfo(info[0].trim(), info[1].trim(), DurationType.getType(info[2].trim()),info[3].trim());
        }).toList();
    }
}
