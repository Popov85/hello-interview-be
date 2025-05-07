package com.hello.interview.tasks.cache.ttl;

public interface SimpleTTLCache<K, V> {

    void put(K key, V value, long ttlMillis);

    V get(K key);

    int size();
}
