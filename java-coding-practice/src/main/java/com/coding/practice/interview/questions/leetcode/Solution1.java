package com.coding.practice.interview.questions.leetcode;

public class Solution1 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;  // Pointer for nums1
        int j = n - 1;  // Pointer for nums2
        int k = m + n - 1;  // Pointer for the merged array in nums1

        // Merge the arrays starting from the end
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }

        // If there are any remaining elements in nums2, copy them
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }

        // No need to handle the leftover elements in nums1 because they are already in place
    }

    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        int[] nums1 = new int[] {1, 2, 3, 0, 0, 0};  // nums1 with enough space for nums2
        int[] nums2 = new int[] {2, 5, 6};
        solution.merge(nums1, 3, nums2, 3);

        // Print merged array
        for (int num : nums1) {
            System.out.print(num + " ");
        }
    }
}