package com.hello.interview.tasks.ratelimiter.fixedwindow;

import com.hello.interview.tasks.ratelimiter.SystemRateLimiter;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

public class FixedWindowSystemRateLimiter implements SystemRateLimiter {

    // Fixed max requests per window
    private final int maxRequests;

    // Fixed window
    private final long windowMillis;

    public FixedWindowSystemRateLimiter(int maxRequests, long windowMillis) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    private static class Counter {
        Instant windowStart;
        int count;

        Counter(Instant windowStart) {
            this.windowStart = windowStart;
            this.count = 1;
        }
    }

    private final ReentrantLock lock = new ReentrantLock();

    private Counter counter = new Counter(Instant.now());

    @Override
    public boolean allowRequest() {

        Instant now = Instant.now();

        lock.lock();
        try {
            Duration elapsed = Duration.between(counter.windowStart, now);

            if (elapsed.toMillis() >= windowMillis) {
                counter = new Counter(now);
                return true;
            }

            if (counter.count < maxRequests) {
                counter.count++;
                return true;
            }

            return false;
        } finally {
            lock.unlock();
        }
    }
}
