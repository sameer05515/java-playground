package com.coding.practice.interview.questions.leetcode.q6;

import com.coding.practice.interview.questions.leetcode.Solution1;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Solution6 {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] nums3=new int[nums1.length+nums2.length];
        int index=0;
        for(int i:nums1){
            nums3[index]=i;
            index++;
        }
        for(int i:nums2){
            nums3[index]=i;
            index++;
        }
        Arrays.sort(nums3);


        System.out.println(getIntArrayString(nums1));
        System.out.println(getIntArrayString(nums2));
        System.out.println(getIntArrayString(nums3));
        System.out.printf("num1 length: %s, num2 length: %s, num3 length: %s %n", nums1.length, nums2.length, nums3.length);

        int n = nums3.length;
        if (n % 2 == 1) {
            // Odd length: Median is the middle element
            return nums3[n / 2];
        } else {
            // Even length: Median is the average of the two middle elements
            return (nums3[(n - 1) / 2] + nums3[n / 2]) / 2.0;
        }

    }


    public static void main(String[] args) {
        Solution6 sol=new Solution6();

        run(sol,new int[]{1,3}, new int[]{2});
        run(sol,new int[]{2,2,4,4}, new int[]{2,2,2,4,4});
    }

    public static void run(Solution6 sol, int[] nums1, int[] nums2){
        System.out.println("--------------------\n");
        double res= sol.findMedianSortedArrays(nums1,nums2);
        System.out.println(res);
    }

    public static String getIntArrayString(int[] intArr){
        return Arrays.stream(intArr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
