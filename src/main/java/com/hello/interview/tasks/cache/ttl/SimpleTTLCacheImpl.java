package com.hello.interview.tasks.cache.ttl;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class SimpleTTLCacheImpl<K, V> implements SimpleTTLCache<K, V> {

    private final Map<K, V> cache = new ConcurrentHashMap<>();
    private final Map<K, ScheduledFuture<?>> expiryTasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /*@Override
    public void put(K key, V value, long ttlMillis) {
        cache.put(key, value);
        scheduler.schedule(() -> cache.remove(key), ttlMillis, TimeUnit.MILLISECONDS);
    }*/

    /**
     * Inserts or updates the cache with TTL.
     * If the same key already exists, its TTL and value will be replaced,
     * and the old expiration task will be cancelled.
     */
    @Override
    public void put(K key, V value, long ttlMillis) {
        // Cancel old scheduled removal task
        ScheduledFuture<?> oldTask = expiryTasks.remove(key);
        if (oldTask != null) {
            oldTask.cancel(false);
        }

        // Put the new value
        cache.put(key, value);

        // Schedule the removal after TTL
        ScheduledFuture<?> newTask = scheduler.schedule(() -> {
            cache.remove(key);
            expiryTasks.remove(key);
        }, ttlMillis, TimeUnit.MILLISECONDS);

        // Track the scheduled task
        expiryTasks.put(key, newTask);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public int size() {
        return cache.size();
    }

    // Optional cleanup if needed
    public void shutdown() {
        scheduler.shutdown();
    }
}
