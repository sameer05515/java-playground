package com.coding.practice.codes.linked_list_operations;

public class SinglyLinkedList {
    // Node class representing each element in the list
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;

    // Insert a new node at the end of the list
    public void insert(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Delete the first occurrence of a node with specified data
    public void delete(int data) {
        if (head == null) return;

        // If the node to be deleted is the head node
        if (head.data == data) {
            head = head.next;
            return;
        }

        Node current = head;
        while (current.next != null && current.next.data != data) {
            current = current.next;
        }

        // If the node was found
        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    // Search for a node with specified data
    public boolean search(int data) {
        Node current = head;
        while (current != null) {
            if (current.data == data) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Print the linked list
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();
        
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.insert(4);

        System.out.println("Linked List:");
        list.printList(); // Output: 1 2 3 4

        System.out.println("Searching for 3: " + list.search(3)); // Output: true
        System.out.println("Searching for 5: " + list.search(5)); // Output: false

        list.delete(3);
        System.out.println("Linked List after deleting 3:");
        list.printList(); // Output: 1 2 4

        list.delete(1);
        System.out.println("Linked List after deleting 1:");
        list.printList(); // Output: 2 4
    }
}
