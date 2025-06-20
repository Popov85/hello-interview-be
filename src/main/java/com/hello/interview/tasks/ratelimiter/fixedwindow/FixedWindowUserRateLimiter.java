package com.hello.interview.tasks.ratelimiter.fixedwindow;

import com.hello.interview.tasks.ratelimiter.UserRateLimiter;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class FixedWindowUserRateLimiter implements UserRateLimiter {

    // Maximum number of allowed requests per user per window
    private final int maxRequests;

    // Length of the time window in milliseconds (e.g., 1000 ms = 1 sec)
    private final long windowMillis;

    // Constructor to initialize the rate limiter with a request limit and window size
    public FixedWindowUserRateLimiter(int maxRequests, long windowMillis) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    // Inner class to hold request count and window start time per user
    private static class Counter {
        Instant windowStart; // Timestamp indicating when the current window started
        int count;           // Number of requests made by the user in the current window

        Counter(Instant windowStart) {
            this.windowStart = windowStart;
            this.count = 1; // First request starts with count 1
        }
    }

    // Map storing the current request counter for each user
    private final Map<String, Counter> userRequestCounts = new HashMap<>();

    private final ReentrantLock lock = new ReentrantLock();


    @Override
    public boolean allowRequest(String userId) {

        // current time
        Instant now = Instant.now();

        lock.lock();

        try {
            // Try to get the existing counter for this user
            Counter currentCounter = userRequestCounts.get(userId);

            // Case 1: First request from this user OR old window has expired
            if (currentCounter == null || Duration.between(currentCounter.windowStart, now).toMillis() >= windowMillis) {
                // New window starts now
                Counter newCounter = new Counter(now);
                userRequestCounts.put(userId, newCounter);
                return true; // allow the request
            }

            // Case 2: User is within the same active window
            if (currentCounter.count < maxRequests) {
                currentCounter.count++; // increment request count
                return true; // allow the request
            }

            // Case 3: User exceeded the limit within the current window
            return false; // reject the request
        } finally {
            lock.unlock();
        }

    }

}
