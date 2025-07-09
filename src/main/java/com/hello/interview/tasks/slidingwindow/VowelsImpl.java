package com.hello.interview.tasks.slidingwindow;

import java.util.Set;

/**
 * Given a string s and an integer k, return the maximum number of vowel letters in any substring of s with length k.
 * Vowel letters in English are 'a', 'e', 'i', 'o', and 'u'.
 * https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length/description/
 */
public class VowelsImpl implements Vowels {

    private static final Set<Character> ENG_VOWELS = Set.of('a', 'e', 'i', 'o', 'u');

    @Override
    public int maxVowels(String s, int k) {

        int maxVowels = 0;
        int currentVowels = 0;

        // 1. Initialize the first window
        for (int i = 0; i < k; i++) {
            if (ENG_VOWELS.contains(s.charAt(i))) {
                currentVowels++;
            }
        }

        maxVowels = currentVowels;

        // 2. Slide the window
        for (int i = k; i < s.length(); i++) {

            char inChar = s.charAt(i);
            char outChar = s.charAt(i - k);

            if (ENG_VOWELS.contains(inChar)) {
                currentVowels++;
            }
            if (ENG_VOWELS.contains(outChar)) {
                currentVowels--;
            }
            maxVowels = Math.max(maxVowels, currentVowels);
        }

        return maxVowels;
    }
}
