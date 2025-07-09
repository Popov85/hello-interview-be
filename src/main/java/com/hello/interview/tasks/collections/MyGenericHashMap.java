package com.hello.interview.tasks.collections;

public interface MyGenericHashMap<K, V> {

    void put(K key, V value);

    V get(K key);

    void remove(K key);
}
