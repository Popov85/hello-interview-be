package com.hello.interview.tasks.map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumbersImplTest {

    private final Numbers numbers = new NumbersImpl();

    private final Numbers numbersOptimized = new NumbersOptimizedImpl();

    @ParameterizedTest(name="standard")
    @MethodSource("groupNumbersData")
    void groupNumbers(Integer[] source, List<List<Integer>> expected) {
        var actual = numbers.groupNumbers(source);

        Set<Set<Integer>> expectedNormalized = expected.stream()
                .map(HashSet::new)
                .collect(Collectors.toSet());

        Set<Set<Integer>> actualNormalized = actual.stream()
                .map(HashSet::new)
                .collect(Collectors.toSet());

        assertEquals(expectedNormalized, actualNormalized);
    }

    @ParameterizedTest(name="optimized")
    @MethodSource("groupNumbersData")
    void groupNumbersOptimized(Integer[] source, List<List<Integer>> expected) {
        var actual = numbersOptimized.groupNumbers(source);

        Set<Set<Integer>> expectedNormalized = expected.stream()
                .map(HashSet::new)
                .collect(Collectors.toSet());

        Set<Set<Integer>> actualNormalized = actual.stream()
                .map(HashSet::new)
                .collect(Collectors.toSet());

        assertEquals(expectedNormalized, actualNormalized);
    }


    private static Stream<Arguments> groupNumbersData() {
        return Stream.of(
                Arguments.of(
                        new Integer[]{1230, 99, 23001, 123, 111, 300021, 101010, 90000009, 9},
                        List.of(
                                List.of(1230, 23001, 123, 300021),
                                List.of(99, 90000009),
                                List.of(111, 101010),
                                List.of(9)
                        )
                ),
                Arguments.of(
                        new Integer[]{123, 321, 231},
                        List.of(List.of(123, 321, 231))
                ),
                Arguments.of(
                        new Integer[]{111, 222, 333},
                        List.of(List.of(111), List.of(222), List.of(333))
                ),
                Arguments.of(
                        new Integer[]{101, 110, 11},
                        List.of(List.of(101, 110, 11))
                ),
                Arguments.of(
                        new Integer[]{123, 456, 789},
                        List.of(List.of(123), List.of(456), List.of(789))
                )
        );
    }
}