//package com.coding.practice.interview.questions.leetcode.q6;
//
//public class OptimizedV1 {
//}

package com.coding.practice.interview.questions.leetcode.q6;

import java.util.Arrays;
import java.util.stream.Collectors;

public class OptimizedV1 {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] nums3 = new int[nums1.length + nums2.length];
        int index = 0;

        // Merge nums1 into nums3
        for (int i : nums1) {
            nums3[index++] = i;
        }

        // Merge nums2 into nums3
        for (int i : nums2) {
            nums3[index++] = i;
        }

        // Sort the merged array
        Arrays.sort(nums3);

        // Calculate and return the median
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
        OptimizedV1 sol = new OptimizedV1();

        // Test cases
        run(sol, new int[]{1, 3}, new int[]{2}); // Median: 2.0
        run(sol, new int[]{2, 2, 4, 4}, new int[]{2, 2, 2, 4, 4}); // Median: 3.0
    }

    public static void run(OptimizedV1 sol, int[] nums1, int[] nums2) {
        System.out.println("--------------------\n");
        System.out.printf("nums1: %s%n", getIntArrayString(nums1));
        System.out.printf("nums2: %s%n", getIntArrayString(nums2));
        double res = sol.findMedianSortedArrays(nums1, nums2);
        System.out.printf("Median: %.1f%n", res);
    }

    public static String getIntArrayString(int[] intArr) {
        return Arrays.stream(intArr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
