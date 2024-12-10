//package com.coding.practice.interview.questions.leetcode.q15;
//
//public class Optimized {
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

public class Optimized {

    ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        // Priority Queue to hold nodes in ascending order
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

        // Add the head of each list to the priority queue
        for (ListNode node : lists) {
            if (node != null) {
                pq.add(node);
            }
        }

        ListNode dummy = new ListNode();
        ListNode current = dummy;

        // Extract the smallest element from the queue and add the next node from the same list
        while (!pq.isEmpty()) {
            ListNode smallest = pq.poll();
            current.next = smallest;
            current = current.next;

            if (smallest.next != null) {
                pq.add(smallest.next);
            }
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        Optimized sol = new Optimized();
        run(sol, new int[][]{{1, 2, 4}, {1, 3, 4}});
        run(sol, new int[][]{});
        run(sol, new int[][]{{}});
    }

    private static void run(Optimized sol, int[][] arr) {
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

