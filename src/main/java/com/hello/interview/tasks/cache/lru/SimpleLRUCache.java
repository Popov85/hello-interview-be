package com.hello.interview.tasks.cache.lru;

public interface SimpleLRUCache<K, V> {

    void put(K key, V value);

    V get(K key);

    int size();
}
