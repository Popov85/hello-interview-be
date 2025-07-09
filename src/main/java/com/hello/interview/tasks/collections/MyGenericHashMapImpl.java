package com.hello.interview.tasks.collections;

public class MyGenericHashMapImpl<K, V> implements MyGenericHashMap<K, V> {

    private static final int SIZE = 1009; // Fixed map size, prime for fewer collisions

    private Node<K, V>[] buckets;

    public MyGenericHashMapImpl() {
        buckets = new Node[SIZE];
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    @Override
    public void put(K key, V value) {
        int index = hash(key);
        Node<K, V> curr = buckets[index];

        while (curr != null) {
            if (curr.key.equals(key)) {
                curr.value = value;
                return;
            }
            curr = curr.next;
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        Node<K, V> curr = buckets[index];

        while (curr != null) {
            if (curr.key.equals(key)) {
                return curr.value;
            }
            curr = curr.next;
        }

        return null;
    }

    @Override
    public void remove(K key) {
        int index = hash(key);
        Node<K, V> curr = buckets[index];

        // 🟡 Case 1: Empty bucket — key doesn't exist
        if (curr == null) {
            return;
        }

        // 🟢 Case 2: Key is at the head
        if (curr.key.equals(key)) {
            buckets[index] = curr.next;
            curr.next = null; // help GC
            return;
        }

        // 🔵 Case 3: Traverse and remove from middle or tail
        Node<K, V> prev = curr;
        curr = curr.next;

        while (curr != null) {
            if (curr.key.equals(key)) {
                prev.next = curr.next;
                curr.next = null; // help GC
                return;
            }
            prev = curr;
            curr = curr.next;
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % SIZE);
    }
}
