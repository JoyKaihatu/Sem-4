package com.company;

import java.util.HashSet;
import java.util.Set;

class LongestSubstringWithoutRepeatingCharacters {
    public static int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        int left = 0;
        int right = 0;
        Set<Character> uniqueChars = new HashSet<>();

        while (right < s.length()) {
            if (uniqueChars.add(s.charAt(right))) {
                maxLength = Math.max(maxLength, uniqueChars.size());
                right++;
            } else {
                uniqueChars.remove(s.charAt(left));
                left++;
            }
        }

        return maxLength;
    }

    public static void main(String[] args) {
        String s1 = "abcabcbb";
        System.out.println("Panjang substring terpanjang tanpa karakter yang berulang: " + lengthOfLongestSubstring(s1));

        String s2 = "bbbbb";
        System.out.println("Panjang substring terpanjang tanpa karakter yang berulang: " + lengthOfLongestSubstring(s2));

        String s3 = "pwwkew";
        System.out.println("Panjang substring terpanjang tanpa karakter yang berulang: " + lengthOfLongestSubstring(s3));
    }
}