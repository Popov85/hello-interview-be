package com.hello.interview.tasks.map;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumbersOptimizedImpl implements Numbers {
    @Override
    public List<List<Integer>> groupNumbers(Integer[] numbers) {

        Map<String, List<Integer>> groups = new HashMap<>();

        for (int num : numbers) {
            int[] freq = new int[10]; // Only digits 0-9

            for (char c : String.valueOf(num).toCharArray()) {
                int digit = Integer.valueOf(String.valueOf(c)); // clean and explicit
                if (digit != 0) {
                    freq[digit]++;
                }
            }

            // Build key in fixed order (1 to 9), ignoring zero
            String key = Arrays.stream(IntStream.rangeClosed(1, 9)
                            .mapToObj(i -> freq[i] > 0 ? i + ":" + freq[i] : null)
                            .filter(Objects::nonNull)
                            .toArray(String[]::new))
                    .collect(Collectors.joining(","));

            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(num);
        }

        return new ArrayList<>(groups.values());
    }
}
