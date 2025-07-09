package com.hello.interview.tasks.slidingwindow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VowelsImplTest {

    private final VowelsImpl vowels = new VowelsImpl();

    @ParameterizedTest(name = "maxVowels(\"{0}\", {1}) = {2}")
    @CsvSource({
            "abciiidef, 3, 3",     // 'iii'
            "aeiou, 2, 2",         // all vowels
            "leetcode, 3, 2",      // 'eet'
            "rhythm, 2, 0",        // no vowels
            "a, 1, 1",             // single char, vowel
            "b, 1, 0",             // single char, not vowel
            "aabaa, 2, 2"          // multiple equal windows
    })
    void testMaxVowels(String s, int k, int expected) {
        assertEquals(expected, vowels.maxVowels(s, k));
    }
}