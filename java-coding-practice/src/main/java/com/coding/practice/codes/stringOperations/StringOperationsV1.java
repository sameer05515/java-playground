package com.coding.practice.codes.stringOperations;

public class StringOperationsV1 {

    public static final PerformStringOperations toUpperCase=(String str)-> str.toUpperCase();

    public static void main(String[] args) {
        try{
            String sampleString= "Premendra Kumar";
            System.out.println(toUpperCase.perform(sampleString));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}

interface PerformStringOperations{
    String perform (String input) throws InvalidInputException;
}

class InvalidInputException extends Exception{

}


