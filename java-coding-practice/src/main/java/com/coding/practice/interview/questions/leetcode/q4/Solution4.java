package com.coding.practice.interview.questions.leetcode.q4;

public class Solution4 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode l3 = new ListNode();
        ListNode l1Next = l1;
        ListNode l2Next = l2;
        ListNode current = l3;

        while (l1Next != null || l2Next != null) {
            int sum = (l1Next != null ? l1Next.val : 0) + (l2Next != null ? l2Next.val : 0) + carry;
            carry = sum / 10;
            current.next = new ListNode(sum % 10);
            l1Next = l1Next != null ? l1Next.next : null;
            l2Next = l2Next != null ? l2Next.next : null;
            current=current.next;
        }
        if(carry>0){
            current.next = new ListNode(carry);
        }

        return l3.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        System.out.println(l1.toString());
        System.out.println(l2.toString());
        ListNode l3=new Solution4().addTwoNumbers(l1,l2);
        System.out.println(l3.toString());
    }


    //Definition for singly-linked list.
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
            StringBuffer sb = new StringBuffer();

            sb.append(val + ", ");
            ListNode nextNode = next;
            while (nextNode != null) {
                sb.append(nextNode.val + ", ");
                nextNode = nextNode.next;
            }

            return sb.toString();
        }
    }

}
