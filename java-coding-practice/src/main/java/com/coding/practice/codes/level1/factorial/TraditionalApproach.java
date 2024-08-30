package com.coding.practice.codes.level1.factorial;

public class TraditionalApproach {
    public static void main(String[] args) {
        int[] arr={0, 1, 2, 3, 10};

        System.out.println("Recursive Approach");
        for(int val:arr){
            System.out.println(factorialRecursive(val));
        }

        System.out.println("Iterative Approach");
        for(int val:arr){
            System.out.println(factorialIterative(val));
        }
    }

    private static int factorialRecursive(int val) {
        if(val<1) {
            return 1;
        }else {
            return val*factorialRecursive(val-1);
        }
    }

    private static int factorialIterative(int val) {
        if(val<1){
            return 1;
        }
        int result=val;
        while(val>1){
            result=result*(--val);
        }
        return result;
    }
}
