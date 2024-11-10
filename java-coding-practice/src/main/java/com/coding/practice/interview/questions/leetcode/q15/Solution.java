package com.coding.practice.interview.questions.leetcode.q15;


import java.util.Collections;
import java.util.LinkedList;

//class ListNode {
//    int val;
//    ListNode next;
//
//    ListNode() {}
//    ListNode(int val) { this.val = val; }
//    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
//}

public class Solution {

    ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode();
        ListNode current = dummy;
        LinkedList<Integer> unsorted = new LinkedList<>();

        while (hasNextNodes(lists)) {
            for (int i = 0; i < lists.length; i++) {
                ListNode l = lists[i];
                if (l != null) {
                    unsorted.add(l.val);
                    lists[i] = l.next;
                }
            }
        }
        System.out.println("unsorted: " + unsorted);
        Collections.sort(unsorted);
        while (!unsorted.isEmpty()) {
            current.next = new ListNode(unsorted.pop());
            current = current.next;
        }
        return dummy.next;
    }

    private boolean hasNextNodes(ListNode[] lists) {
        if (lists == null) return false;
        for (ListNode l : lists) {
            if (l != null) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        run(sol, new int[][]{{1, 2, 4}, {1, 3, 4}});
        run(sol, new int[][]{});
        run(sol, new int[][]{{}});
    }

    private static void run(Solution sol, int[][] arr) {
        if (arr == null) return;
        ListNode[] lArr = new ListNode[arr.length];
        int index = 0;
        for (int[] l1 : arr) {
            ListNode list1 = getHead(l1);
            System.out.printf("list%s: ", index);
            printList(list1);
            lArr[index++] = list1;
        }
        ListNode list3 = sol.mergeKLists(lArr);
        System.out.print("list3: ");
        printList(list3);
        System.out.println("-------------------");
    }


    private static ListNode getHead(int[] l1) {
        ListNode dummy = new ListNode();
        ListNode current = dummy;
        for (int i : l1) {
            current.next = new ListNode(i);
            current = current.next;
        }
        return dummy.next;
    }

    private static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
}
