package com.hello.interview.tasks.string.subsequence;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubsequencePreProcessImplTest {

    private static final String TARGET_STRING = "abcdefghijabcdefghijabcdefghij";

    private final SubsequencePreProcess subsequence = new SubsequencePreProcessImpl(TARGET_STRING);

    @ParameterizedTest
    @CsvSource({
            // ✅ Valid subsequences
            "abc, true",                  // simple match at the beginning
            "aaa, true",                  // repeated 'a' found
            "aei, true",                  // skip characters in between
            "ajafj, true",                // jumpy pattern with repeated letters
            "abcdefghijabcdefghijabcdefghij, true",  // exact match
            "jji, true",                  // repeated character match (j appears 3 times)
            // ❌ Invalid subsequences
            "zxy, false",                 // z does not exist in target
            "aaaa, false"                 // a 4 times fut only 3 exist in target
    })
    void isSubsequenceTest(String substring, boolean expected) {
        boolean result = subsequence.isSubsequence(substring);
        assertEquals(expected, result);
    }
}