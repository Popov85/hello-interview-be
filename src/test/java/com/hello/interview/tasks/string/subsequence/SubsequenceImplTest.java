package com.hello.interview.tasks.string.subsequence;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubsequenceImplTest {

    private final SubsequenceImpl subsequence = new SubsequenceImpl();

    @ParameterizedTest
    @CsvSource({
            "abc, ahbgdc, true",
            "axc, ahbgdc, false",
            "'', abc, true",          // Empty string is a subsequence of any string
            "abc, '', false",         // Non-empty string cannot be a subsequence of empty string
            "abc, abc, true",         // Exact match
            "abc, aabbcc, true",      // Characters in order, even if repeated
            "abc, acb, false",        // Characters in wrong order
            "a, a, true",             // Single character match
            "a, b, false",            // Single character mismatch
            "longstring, longsubstringexample, true"
    })
    void isSubsequenceTest(String substring, String target, boolean expected) {
        boolean result = subsequence.isSubsequence(substring, target);
        assertEquals(expected, result);
    }
}