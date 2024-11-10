//package com.coding.practice.interview.questions.leetcode.q15;
//
//public class MoreOptimized {
//}


package com.coding.practice.interview.questions.leetcode.q15;

import java.util.PriorityQueue;

//class ListNode {
//    int val;
//    ListNode next;
//
//    ListNode() {}
//    ListNode(int val) {
//        this.val = val;
//    }
//    ListNode(int val, ListNode next) {
//        this.val = val;
//        this.next = next;
//    }
//}

public class MoreOptimized {

    ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

        // Add the head of each list to the priority queue
        for (ListNode node : lists) {
            if (node != null) pq.offer(node);
        }

        ListNode dummy = new ListNode();
        ListNode current = dummy;

        while (!pq.isEmpty()) {
            ListNode smallest = pq.poll();
            current.next = smallest;
            current = current.next;

            if (smallest.next != null) {
                pq.offer(smallest.next);
            }
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        MoreOptimized sol = new MoreOptimized();
        run(sol, new int[][]{{1, 2, 4}, {1, 3, 4}});
        run(sol, new int[][]{});
        run(sol, new int[][]{{}});
    }

    private static void run(MoreOptimized sol, int[][] arr) {
        ListNode[] lArr = new ListNode[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ListNode list1 = getHead(arr[i]);
            System.out.printf("list%s: ", i);
            printList(list1);
            lArr[i] = list1;
        }
        ListNode mergedList = sol.mergeKLists(lArr);
        System.out.print("Merged list: ");
        printList(mergedList);
        System.out.println("-------------------");
    }

    private static ListNode getHead(int[] l1) {
        if (l1 == null || l1.length == 0) return null;
        ListNode head = new ListNode(l1[0]);
        ListNode current = head;
        for (int i = 1; i < l1.length; i++) {
            current.next = new ListNode(l1[i]);
            current = current.next;
        }
        return head;
    }

    private static void printList(ListNode head) {
        if (head == null) {
            System.out.println("Empty list");
            return;
        }
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
}
