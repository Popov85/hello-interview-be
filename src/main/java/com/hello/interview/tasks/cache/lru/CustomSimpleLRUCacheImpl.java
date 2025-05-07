package com.hello.interview.tasks.cache.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * Warn: thread safety is out of scope.
 * Cases:
 * Get when key not exist. Return null;
 * Get when key exists. Return value. Remove node with given key. Insert node with given key to head;
 * Put when key exists. Remove node with given key. Insert node with given key to head; Update value in map.
 * Put when key does not exists, capacity is less than MAX. Insert node to map. Insert node to head.
 * Put when key does not exists, capacity is equal MAX. Remove last node (before tail). Insert new node after head. Insert node to map.
 *
 * @param <K>
 * @param <V>
 */
public class CustomSimpleLRUCacheImpl<K, V> implements SimpleLRUCache<K, V> {


    private final int capacity;

    private final Map<K, Node<K, V>> cache = new HashMap<>();

    private final Node<K, V> head = new Node(null, null);

    private final Node<K, V> tail = new Node(null, null);

    {
        head.next = tail;
        tail.prev = head;
    }

    public CustomSimpleLRUCacheImpl(int capacity) {
        this.capacity = capacity;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void put(K key, V value) {
        Node<K, V> result = cache.get(key);
        if (result != null) {
            // Put for present case
            // Update result node
            // Cache map vaue is updated automatically
            result.value = value;
            removeNode(result);
            insertAtHead(result);

        } else {
            // Put for abcent case
            if (size() >= capacity) {
                // Full: remove LRU before inserting new node
                var lruNode = removeLastNode();
                cache.remove(lruNode.key);
            }
            var newNode = new Node(key, value);
            insertAtHead(newNode);
            cache.put(key, newNode);
        }

    }

    @Override
    public V get(K key) {
        Node<K, V> result = cache.get(key);
        if (result == null) return null;
        // Node is present
        // Delete result node from list;
        // Insert result node to the head;
        removeNode(result);
        insertAtHead(result);
        return result.value;
    }

    @Override
    public int size() {
        return cache.size();
    }

    private void insertAtHead(Node<K, V> node) {
        var oldHead = this.head.next;

        this.head.next = node;
        node.prev = this.head;
        node.next = oldHead;
        oldHead.prev = node;
    }

    private void removeNode(Node<K, V> node) {
        var prevNode = node.prev;
        var nextNode = node.next;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        node.prev = null;
        node.next = null;
    }

    private Node<K, V> removeLastNode() {

        var lastNode = this.tail.prev;
        var preLastNode = lastNode.prev;
        preLastNode.next = this.tail;
        this.tail.prev = preLastNode;

        lastNode.next = null;
        lastNode.prev = null;

        return lastNode;
    }

}
