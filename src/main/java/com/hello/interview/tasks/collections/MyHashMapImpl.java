package com.hello.interview.tasks.collections;

public class MyHashMapImpl implements MyHashMap {

    private static final int SIZE = 1009; // Fixed map size, not way to increase; A prime number reduces collision probability

    private Node[] buckets;

    public MyHashMapImpl() {
        buckets = new Node[SIZE];
    }

    private static class Node {
        int key, value;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void put(int key, int value) {
        // 1. Calc array index
        int index = hash(key);
        // 2. Fetch Node at that index
        Node head = buckets[index];

        // 3.Iterate over the linked list and try to find existing key, if found replace value
        Node curr = head;
        while (curr != null) {
            if (curr.key == key) {
                curr.value = value;
                return;
            }
            curr = curr.next;
        }

        // 4. If the key is not found — prepend new node to the bucket, to the head!
        Node newNode = new Node(key, value);
        newNode.next=head;
        // 5. Make new Node the new head!
        buckets[index] = newNode;
    }

    @Override
    public int get(int key) {
        int index = hash(key);
        Node curr = buckets[index];

        while (curr != null) {
            if (curr.key == key) {
                return curr.value;
            }
            curr = curr.next;
        }

        return -1;
    }

    @Override
    public void remove(int key) {
        int index = hash(key);
        Node curr = buckets[index];

        // 🟡 Case 1: Empty bucket — key doesn't exist
        if (curr == null) {
            return;
        }

        // 🟢 Case 2: Key is at the head
        if (curr.key == key) {
            buckets[index] = curr.next; // could be null (single node) or another node
            curr.next = null;           // help GC
            return;
        }

        // 🔵 Case 3: Traverse from next after head and remove from middle or tail
        Node prev = curr;
        curr = curr.next;

        while (curr != null) {
            if (curr.key == key) {
                prev.next = curr.next;
                curr.next = null; // help GC
                return;
            }
            prev = curr;
            curr = curr.next;
        }
    }

    private int hash(int key) {
        return Math.abs(key % SIZE);
    }
}
