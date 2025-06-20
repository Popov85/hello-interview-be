package com.hello.interview.tasks.ratelimiter.slidingwindowlog;

import com.hello.interview.tasks.ratelimiter.UserRateLimiter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class SlidingWindowLogRateLimiter implements UserRateLimiter {

    private final int maxRequests;

    private final long windowMillis ;

    public SlidingWindowLogRateLimiter(int maxRequests, long windowMillis) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    // Grows linearly with active users and their request frequency. Not scalable for many users.
    private final Map<String, Deque<Long>> requestTimes = new HashMap<>();

    @Override
    public synchronized boolean allowRequest(String userId) {

        // Current time
        long now = System.currentTimeMillis();

        requestTimes.putIfAbsent(userId, new ArrayDeque<>());
        Deque<Long> times = requestTimes.get(userId);

        // Remove requests older than 1 second
        while (!times.isEmpty() && (now - times.peekFirst()) >= windowMillis) {
            times.pollFirst();
        }

        if (times.size() < maxRequests) {
            times.addLast(now);
            return true;
        } else {
            return false; // rate limit exceeded
        }
    }
}
