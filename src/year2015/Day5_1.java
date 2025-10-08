package year2015;

import java.util.List;

public class Day5_1 {

    public String part1(List<String> lines) {
        int niceStrings = 0;
        for (String line : lines) {
            if (getVowels(line) < 3) {
                continue;
            }

            if (!hasDoubleLetters(line)) {
                continue;
            }

            if (line.contains("ab") || line.contains("cd") || line.contains("pq") || line.contains("xy")) {
                continue;
            }

            niceStrings++;
        }

        return String.valueOf(niceStrings);
    }

    private int getVowels(String s) {
        int vowels = 0;
        for (char c : s.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowels++;
            }

            if (vowels == 3) {
                return vowels;
            }
        }

        return vowels;
    }

    private boolean hasDoubleLetters(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                return true;
            }
        }

        return false;
    }
}
