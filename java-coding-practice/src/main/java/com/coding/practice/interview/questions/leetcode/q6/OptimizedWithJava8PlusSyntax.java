//package com.coding.practice.interview.questions.leetcode.q6;
//
//public class OptimizedWithJava8PlusSyntax {
//}

package com.coding.practice.interview.questions.leetcode.q6;

import java.util.Arrays;
import java.util.stream.Collectors;

public class OptimizedWithJava8PlusSyntax {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Merge two arrays into one using Java 8 streams
        int[] mergedArray = Arrays.stream(new int[][]{nums1, nums2})
                .flatMapToInt(Arrays::stream)
                .sorted()
                .toArray();

        int n = mergedArray.length;

//        System.out.printf("nums1: %s%n", getIntArrayString(nums1));
//        System.out.printf("nums2: %s%n", getIntArrayString(nums2));
//        System.out.printf("Merged and Sorted Array: %s%n", getIntArrayString(mergedArray));
//        System.out.printf("nums1 length: %d, nums2 length: %d, merged length: %d%n", nums1.length, nums2.length, n);

        // Return median based on length
        return (n % 2 == 1)
                ? mergedArray[n / 2]
                : (mergedArray[(n - 1) / 2] + mergedArray[n / 2]) / 2.0;
    }

    public static void main(String[] args) {
        OptimizedWithJava8PlusSyntax sol = new OptimizedWithJava8PlusSyntax();

        // Test cases
        run(sol, new int[]{1, 3}, new int[]{2});
        run(sol, new int[]{2, 2, 4, 4}, new int[]{2, 2, 2, 4, 4});
    }

    public static void run(OptimizedWithJava8PlusSyntax sol, int[] nums1, int[] nums2) {
        System.out.println("\n--------------------");
        double res = sol.findMedianSortedArrays(nums1, nums2);
        System.out.printf("Median: %.1f%n", res);
    }

    public static String getIntArrayString(int[] intArr) {
        return Arrays.stream(intArr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}

