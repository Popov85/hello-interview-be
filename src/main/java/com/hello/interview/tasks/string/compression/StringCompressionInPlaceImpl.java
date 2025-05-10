package com.hello.interview.tasks.string.compression;

/**
 * MEDIUM
 * https://leetcode.com/problems/string-compression
 */
public class StringCompressionInPlaceImpl implements StringCompression {

    @Override
    public int compress(char[] chars) {

        int nextIndex = 1;
        int nextInsertIndex = 0;
        int sameCharCounter = 1;
        char currentChar = chars[0];

        while (nextIndex <= chars.length) {
            char nextChar = (nextIndex < chars.length) ? chars[nextIndex] : '\0'; // sentinel at the end

            if (currentChar != nextChar) {
                // Always write the currentChar
                chars[nextInsertIndex++] = currentChar;

                if (sameCharCounter > 1) {
                    // Write the count as characters
                    char[] nums = String.valueOf(sameCharCounter).toCharArray();
                    for (char c : nums) {
                        chars[nextInsertIndex++] = c;
                    }
                }

                // Reset tracking for next group
                sameCharCounter = 1;
                currentChar = nextChar;
            } else {
                sameCharCounter++;
            }

            nextIndex++;
        }

        return nextInsertIndex;
    }
}
