package com.coding.practice.interview.questions.leetcode;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Solution2 {
    public int removeElement(int[] nums, int val) {
        int writeIndex = 0; // Index where we place the next non-val element

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[writeIndex] = nums[i]; // Place non-val element at writeIndex
                writeIndex++; // Move to the next position
            }
            System.out.println(Arrays.stream(nums)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(",")));
        }

        return writeIndex; // Number of non-val elements
    }

    public static void main(String[] args) {
        int[] nums=new int[]{1, 2,3,4,5,1,3,1,1};
//        int expected=5;
        Solution2 sol=new Solution2();

//        int actual= sol.removeElement(nums,1);

        test(5, sol.removeElement(new int[]{1, 2,3,4,5,1,3,1,1},1));
//        test(2, sol.removeElement(new int[]{3,2,2,3},2));

    }

    public static void test(int expected, int actual){
        if(expected==actual){
            System.out.println("success");
        }else {
            System.out.println("fail");
        }
    }
}