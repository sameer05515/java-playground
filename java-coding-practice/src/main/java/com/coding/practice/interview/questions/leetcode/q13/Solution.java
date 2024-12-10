package com.coding.practice.interview.questions.leetcode.q13;

import java.util.Collections;
import java.util.LinkedList;



public class Solution {
    ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy=new ListNode();
        ListNode current=dummy;
        LinkedList<Integer> unsorted=new LinkedList<>();

        while(list1!=null || list2!=null){
            if(list1!=null){
                unsorted.add(list1.val);
                list1=list1.next;
            }
            if(list2!=null){
                unsorted.add(list2.val);
                list2=list2.next;
            }
        }
        System.out.println("unsorted: "+unsorted);
        Collections.sort(unsorted);
        while(!unsorted.isEmpty()){
            current.next=new ListNode(unsorted.pop());
            current=current.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        Solution sol= new Solution();
        run(sol, new int[]{1,2,4}, new int[]{1,3,4});
        run(sol, new int[]{}, new int[]{});
        run(sol, new int[]{}, new int[]{0});
    }

    private static void run(Solution sol, int[] l1, int[] l2) {
        ListNode list1=getHead(l1);
        ListNode list2=getHead(l2);
        System.out.print("list1: ");
        printList(list1);
        System.out.print("list2: ");
        printList(list2);
        ListNode list3=sol.mergeTwoLists(list1,list2);
        System.out.print("list3: ");
        printList(list3);
        System.out.println("-------------------");
    }

    private static ListNode getHead(int[] l1) {
        ListNode dummy=new ListNode();
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
