package com.coding.practice.interview.questions.leetcode;

import java.util.StringJoiner;

public class OptimizedSolution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode dummyNode = new ListNode(); // Dummy node to simplify handling head
        ListNode current = dummyNode;

        // Traverse through both lists
        while (l1 != null || l2 != null) {
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;

            int sum = val1 + val2 + carry;
            carry = sum / 10;

            // Create new node with sum's remainder and move current pointer
            current.next = new ListNode(sum % 10);
            current = current.next;

            // Move to the next nodes in l1 and l2
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        // If carry is left, add a new node
        if (carry > 0) {
            current.next = new ListNode(carry);
        }

        return dummyNode.next; // Return the actual head
    }

    public static void main(String[] args) {
        // Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        System.out.println("List 1: " + l1.toJava8String());
        System.out.println("List 2: " + l2.toJava8String());

        ListNode result = new OptimizedSolution().addTwoNumbers(l1, l2);
        System.out.println("Result: " + result.toJava8String());
    }

    // Definition for singly-linked list
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            ListNode current = this;
            while (current != null) {
                sb.append(current.val).append(" -> ");
                current = current.next;
            }
            sb.append("null");
            return sb.toString();
        }

        public String toJava8String() {
            StringJoiner joiner = new StringJoiner(" -> ");
            ListNode current = this;
            while (current != null) {
                joiner.add(String.valueOf(current.val));
                current = current.next;
            }
            return joiner.toString();
        }
    }
}
