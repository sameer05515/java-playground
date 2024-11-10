//package com.coding.practice.interview.questions.leetcode.q6;
//
//public class MoreOptimizedSolution {
//}

package com.coding.practice.interview.questions.leetcode.q6;

public class MoreOptimizedSolution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int totalLength = nums1.length + nums2.length;
        boolean isEven = totalLength % 2 == 0;

        int midIndex1 = (totalLength - 1) / 2; // First median index (for both odd and even cases)
        int midIndex2 = totalLength / 2; // Second median index (only used for even cases)

        int pointer1 = 0, pointer2 = 0, currentIndex = 0;
        int median1 = 0, median2 = 0;

        // Traverse arrays using two pointers
        while (pointer1 < nums1.length || pointer2 < nums2.length) {
            int currentValue;
            if (pointer1 < nums1.length && (pointer2 >= nums2.length || nums1[pointer1] <= nums2[pointer2])) {
                currentValue = nums1[pointer1++];
            } else {
                currentValue = nums2[pointer2++];
            }

            // Check if currentIndex matches midIndex1 or midIndex2
            if (currentIndex == midIndex1) median1 = currentValue;
            if (currentIndex == midIndex2) {
                median2 = currentValue;
                break; // We don't need to process further once we find both medians
            }

            currentIndex++;
        }

        // Return median based on even or odd total length
        return isEven ? (median1 + median2) / 2.0 : median2;
    }

    public static void main(String[] args) {
        MoreOptimizedSolution sol = new MoreOptimizedSolution();

        // Test cases
        run(sol, new int[]{1, 3}, new int[]{2});
        run(sol, new int[]{2, 2, 4, 4}, new int[]{2, 2, 2, 4, 4});
        run(sol, new int[]{1, 2}, new int[]{3, 4});
        run(sol, new int[]{}, new int[]{1});
        run(sol, new int[]{1, 2}, new int[]{-1, 3});
    }

    public static void run(MoreOptimizedSolution sol, int[] nums1, int[] nums2) {
        System.out.println("\n--------------------");
        double res = sol.findMedianSortedArrays(nums1, nums2);
        System.out.printf("Median: %.1f%n", res);
    }
}
