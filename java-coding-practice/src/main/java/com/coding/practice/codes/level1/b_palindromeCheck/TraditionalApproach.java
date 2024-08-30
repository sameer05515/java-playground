package com.coding.practice.codes.level1.b_palindromeCheck;

import java.util.Arrays;

public class TraditionalApproach {
    public static void main(String[] args) {
        String[] valid={"madam", "racecar", "121", "A man, a plan, a canal, Panama", "Able was I ere I saw Elba"};
        String[] invalid={"hello", "apple", "12345", "Palindrome", "world"};

        String[] input=new String[valid.length+ invalid.length];
        // Fill the input array with valid values
        System.arraycopy(valid, 0, input, 0, valid.length);

        // Fill the input array with invalid values
        System.arraycopy(invalid, 0, input, valid.length, invalid.length);

        // Print the result
        for (String str : input) {
            System.out.println("Given string: "+str+" , is a palindrome: "+checkPalindrome(str));
        }
    }

    private static String normalizeString(String input) {
        // Remove spaces, punctuation, and make it lowercase
        return input.replaceAll("[\\W_]", "").toLowerCase();
    }

    private static boolean checkPalindrome(String str) {
        boolean result=false;
        if(str==null){
            return result;
        }
        char[] chArr= normalizeString(str).trim().toCharArray();
        for(int x=0; x<chArr.length/2;x++){
            if(chArr[x]!=chArr[chArr.length-x-1]){
                result=false;
                break;
            }else {
                result=true;
            }
        }

        return result;
    }


}
