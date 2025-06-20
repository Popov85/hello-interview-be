package com.hello.interview.tasks.ratelimiter.fixedwindow;

import com.hello.interview.tasks.ratelimiter.UserRateLimiter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FixedWindowRateLimiterTest {

    @ParameterizedTest(name = "max={0}, intervalMs={1}, total={2} => allowed={3}")
    @CsvSource({
            "5, 200, 5, 5",    // OK
            "5, 200, 6, 6",    // Fixed: 6th request lands in new window
            "5, 50, 10, 5",    // Tight burst, all within same window
            "3, 500, 5, 5",    // Fixed: 2 requests land in next windows
            "10, 100, 7, 7"    // OK
    })
    void testRateLimiter(int maxRequests, int intervalMs, int totalRequests, int expectedAllowed) throws InterruptedException {
        long windowMillis = 1000;
        UserRateLimiter rateLimiter = new FixedWindowUserRateLimiter(maxRequests, windowMillis);

        String userId = "test-user";
        List<Boolean> results = new ArrayList<>();

        for (int i = 0; i < totalRequests; i++) {
            boolean isAllowed = rateLimiter.allowRequest(userId);
            results.add(isAllowed);
            Thread.sleep(intervalMs); // simulate delay between requests
        }

        long actualAllowed = results.stream().filter(b -> b).count();
        assertEquals(expectedAllowed, actualAllowed,
                "Expected " + expectedAllowed + " requests to be allowed, but got " + actualAllowed);
    }
}