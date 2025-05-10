package com.hello.interview.tasks.string.compression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class StringCompressionTest {

    static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of("aabbccc", "a2b2c3"),
                Arguments.of("a", "a"),
                Arguments.of("abbbbbbbbbbbb", "ab12"),
                Arguments.of("abc", "abc"),
                Arguments.of("aa", "a2"),
                Arguments.of("aabbaa", "a2b2a2"),
                Arguments.of("aabbaaacc", "a2b2a3c2"),
                Arguments.of("aaaaa", "a5")
                //Arguments.of("", ""),
                //Arguments.of("a2a2a2", "a123a123a123") // input includes digits
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void testCompress(String input, String expected) {
        StringCompressionInPlaceImpl sc = new StringCompressionInPlaceImpl();
        char[] chars = input.toCharArray();
        int newLength = sc.compress(chars);
        String actual = new String(chars, 0, newLength);
        Assertions.assertEquals(expected, actual);
    }
}