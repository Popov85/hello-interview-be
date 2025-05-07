package com.hello.interview.tasks.cache.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SimpleLRUCacheImpl<K, V> implements SimpleLRUCache<K, V> {

    private final int capacity;

    private final Map<K, V> map;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public SimpleLRUCacheImpl(int capacity) {
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > SimpleLRUCacheImpl.this.capacity;
            }
        };
    }

    @Override
    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            map.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Even though it's a get, it reorders the internal structure (access-order), so it's a mutation.
     * @param key K
     * @return V
     */
    @Override
    public V get(K key) {
        lock.writeLock().lock();
        try {
            return map.get(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public int size() {
        lock.readLock().lock();
        try {
            return map.size();
        } finally {
            lock.readLock().unlock();
        }
    }
}
