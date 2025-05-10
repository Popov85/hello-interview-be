package com.hello.interview.tasks.map;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Time: O(n · k log k)
 * Space: O(n · k)
 * n = number of elements in numbers;
 * k = max number of digits in any number (at most 10 for integers ≤ Integer.MAX_VALUE);
 *  Since k ≤ 10, this is practically O(n) for all real-world usage.
 */
public class NumbersImpl implements Numbers {

    @Override
    public List<List<Integer>> groupNumbers(Integer[] numbers) {
        Map<String, List<Integer>> groups = new HashMap<>();

        for (int num : numbers) {
            // Remove zeros and sort digits
            String key = Arrays.stream(String.valueOf(num).split(""))
                    .filter(d -> !d.equals("0"))
                    .sorted()
                    .collect(Collectors.joining());

            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(num);
        }

        return new ArrayList<>(groups.values());
    }
}
