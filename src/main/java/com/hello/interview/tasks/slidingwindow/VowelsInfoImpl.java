package com.hello.interview.tasks.slidingwindow;

import java.util.*;

public class VowelsInfoImpl {

    private static final Set<Character> ENG_VOWELS = Set.of('a', 'e', 'i', 'o', 'u');

    public static class WindowInfo {
        String window;
        int vowelCount;
        Map<Character, Integer> vowelFreqMap;

        public WindowInfo(String window, int vowelCount, Map<Character, Integer> vowelFreqMap) {
            this.window = window;
            this.vowelCount = vowelCount;
            this.vowelFreqMap = vowelFreqMap;
        }
    }

    public List<WindowInfo> getVowelWindows(String s, int k) {
        List<WindowInfo> result = new ArrayList<>();
        if (s.length() < k) return result;

        int vowelCount = 0;
        Map<Character, Integer> freqMap = new HashMap<>();

        // First window
        for (int i = 0; i < k; i++) {
            char ch = s.charAt(i);
            if (ENG_VOWELS.contains(ch)) {
                freqMap.compute(ch, (key, val) -> val == null ? 1 : val + 1);
                vowelCount++;
            }
        }
        result.add(new WindowInfo(s.substring(0, k), vowelCount, new HashMap<>(freqMap)));

        // Slide the window
        for (int i = k; i < s.length(); i++) {
            char inChar = s.charAt(i);
            char outChar = s.charAt(i - k);

            if (ENG_VOWELS.contains(inChar)) {
                freqMap.compute(inChar, (key, val) -> val == null ? 1 : val + 1);
                vowelCount++;
            }

            if (ENG_VOWELS.contains(outChar)) {
                int count = freqMap.get(outChar);
                if (count == 1) {
                    freqMap.remove(outChar);
                } else {
                    freqMap.put(outChar, count - 1);
                }
                vowelCount--;
            }

            String window = s.substring(i - k + 1, i + 1);
            result.add(new WindowInfo(window, vowelCount, new HashMap<>(freqMap)));
        }

        return result;
    }

}
