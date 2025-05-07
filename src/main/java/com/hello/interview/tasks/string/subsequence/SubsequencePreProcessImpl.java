package com.hello.interview.tasks.string.subsequence;

import java.util.*;

public class SubsequencePreProcessImpl implements SubsequencePreProcess {

    // For each char in target, store a list of indexes at which they appear in target;
    private final Map<Character, List<Integer>> indexMap = new HashMap<>();

    public SubsequencePreProcessImpl(String target) {
        // Populate the inverted index in the constructor
        for (int i = 0; i < target.length(); i++) {
            char c = target.charAt(i);
            indexMap.computeIfAbsent(c, k -> new ArrayList<>()).add(i);
        }
    }

    @Override
    public boolean isSubsequence(String substring) {
        int prevIndex = -1;
        for (char c : substring.toCharArray()) {
            // Early return as next char is not even present in the target!
            if (!indexMap.containsKey(c)) return false;
            // Get all locations of the next char in the target string
            List<Integer> indices = indexMap.get(c);
            // (Binary) search to find the smallest index in 'indices' > 'prevIndex'
            var nextIndex = findNextIndex2(indices, prevIndex);
            if (nextIndex.isEmpty()) return false;
            prevIndex = nextIndex.get();
        }
        return true;
    }

    private Optional<Integer> findNextIndex(List<Integer> indices, int prevIndex) {
        return indices.stream()
                .filter(i -> i > prevIndex)
                .findFirst();
    }

    /*
     In a sorted list:
      - Check the middle element.
      - If it matches the target → ✅ done.
      - If the target is less → search in the left half.
      - If the target is greater → search in the right half.
      - Repeat until found or the range is empty.
     */
    private Optional<Integer> findNextIndex2(List<Integer> indices, int target) {
        int left = 0, right = indices.size() - 1;
        Integer result = null;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int valueAtMid = indices.get(mid);

            if (valueAtMid > target) {
                result = valueAtMid;   // 🎯 valid candidate
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return Optional.ofNullable(result);
    }

}
