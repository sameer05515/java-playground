//package com.coding.practice.interview.questions.leetcode.q11;
//
//public class Optimized {
//}


package com.coding.practice.interview.questions.leetcode.q13;

//class ListNode {
//    int val;
//    ListNode next;
//
//    ListNode() {}
//    ListNode(int val) { this.val = val; }
//    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
//}

public class Optimized {
    ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Dummy node to simplify merging
        ListNode dummy = new ListNode();
        ListNode current = dummy;

        // Merge the two lists
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // Append the remaining nodes
        if (list1 != null) {
            current.next = list1;
        } else if (list2 != null) {
            current.next = list2;
        }

        // Return the merged list
        return dummy.next;
    }

    public static void main(String[] args) {
        Optimized sol = new Optimized();
        run(sol, new int[]{1, 2, 4}, new int[]{1, 3, 4});
        run(sol, new int[]{}, new int[]{});
        run(sol, new int[]{}, new int[]{0});
    }

    private static void run(Optimized sol, int[] l1, int[] l2) {
        ListNode list1 = getHead(l1);
        ListNode list2 = getHead(l2);
        System.out.print("list1: ");
        printList(list1);
        System.out.print("list2: ");
        printList(list2);
        ListNode list3 = sol.mergeTwoLists(list1, list2);
        System.out.print("list3: ");
        printList(list3);
        System.out.println("-------------------");
    }

    private static ListNode getHead(int[] arr) {
        ListNode dummy = new ListNode();
        ListNode current = dummy;
        for (int i : arr) {
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
