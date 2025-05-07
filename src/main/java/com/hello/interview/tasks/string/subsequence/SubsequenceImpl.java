package com.hello.interview.tasks.string.subsequence;

public class SubsequenceImpl implements Subsequence {

    @Override
    public boolean isSubsequence(String substring, String target) {
        int subsequenceIndex = 0;
        int targetIndex = 0;

        while (subsequenceIndex < substring.length() && targetIndex < target.length()) {
            if (substring.charAt(subsequenceIndex) == target.charAt(targetIndex)) {
                subsequenceIndex++;
            }
            targetIndex++;
        }

        return subsequenceIndex == substring.length();
    }
}
