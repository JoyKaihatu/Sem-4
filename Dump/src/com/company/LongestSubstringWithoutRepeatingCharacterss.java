package com.company;

import java.util.Arrays;

public class LongestSubstringWithoutRepeatingCharacterss {
    public static int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        int[] charIndices = new int[128]; // Menggunakan array untuk melacak indeks karakter

        Arrays.fill(charIndices, -1); // Inisialisasi semua indeks dengan -1
        int left = 0, right = 0;

        while (right < s.length()) {
            char currentChar = s.charAt(right);

            if (charIndices[currentChar] >= left) {
                left = charIndices[currentChar] + 1; // Geser 'left' ke indeks setelah kemunculan karakter sebelumnya
            }

            charIndices[currentChar] = right; // Simpan indeks karakter saat ini

            int currentLength = right - left + 1;
            maxLength = Math.max(maxLength, currentLength);

            right++;
        }

        return maxLength;
    }

    public static void main(String[] args) {
        String s1 = "abcdabcdbb";
        System.out.println(lengthOfLongestSubstring(s1)); // Output: 3

        String s2 = "babbbb";
        System.out.println(lengthOfLongestSubstring(s2)); // Output: 1

        String s3 = "pwwkew";
        System.out.println(lengthOfLongestSubstring(s3)); // Output: 3
    }
}