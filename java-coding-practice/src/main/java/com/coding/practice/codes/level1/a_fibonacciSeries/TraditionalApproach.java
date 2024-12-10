package com.coding.practice.codes.level1.a_fibonacciSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TraditionalApproach {
    public static void main(String[] args) {
        List<Integer> series= getFibonacciSeries(10);
        System.out.println(series);
    }

    private static List<Integer> getFibonacciSeries(int i) {
        List<Integer> result=new ArrayList<>();
        if(i<0){
            System.out.println("Invalid number: "+i);
            return new ArrayList<>();
        }else if(i==0){
            return Arrays.asList(0);
        } else if (i==1) {
            return Arrays.asList(1);
        }else {
            int counter=i;
            result.add(0);
            result.add(1);
            while(counter>1){
                result.add(result.get(result.size()-2)+result.get(result.size()-1));
                counter--;
            }
        }
        return result;
    }
}
