package com.coding.practice.codes.durationCalculation.common;

public enum DurationType {
    EMPLOYMENT, GAP, WHOLE_EMPLOYMENT;

    public static DurationType getType(String type){
        if("Employment".equals(type)){
            return EMPLOYMENT;
        } else if ("WHOLE_EMPLOYMENT".equals(type)) {
            return WHOLE_EMPLOYMENT;
        } else {
            return GAP;
        }
    }
}
