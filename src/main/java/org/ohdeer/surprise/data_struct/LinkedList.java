package org.ohdeer.surprise.data_struct;

import java.util.ArrayList;
import java.util.List;


public class LinkedList {
    private Node head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }

    public int size() {
        return size;
    }

    public List<Integer> findDuplicates() {
        List<Integer> duplicates = new ArrayList<>();
        List<Integer> seen = new ArrayList<>();

        Node current = head;
        while (current != null) {
            int data = current.getData();
            if (seen.contains(data) && !duplicates.contains(data)) {
                duplicates.add(data);
            }
            seen.add(data);
            current = current.getNext();
        }
        return duplicates;
    }
}