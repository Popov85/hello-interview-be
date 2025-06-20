package com.hello.interview.tasks.ratelimiter;

public interface UserRateLimiter {
    boolean allowRequest(String userId);
}
