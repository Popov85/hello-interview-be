package com.hello.interview.tasks.cache.ttl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    public static void main(String[] args) throws InterruptedException {
        SimpleTTLCache<String, String> cache = new SimpleTTLCacheImpl<>();
        cache.put("First", "Value", 2000);
        System.out.println("Size = "+ cache.size());
        Thread.sleep(2500);
        System.out.println("Size = "+ cache.size());
    }
}
