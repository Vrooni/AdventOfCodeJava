package year2017;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4_2 {

    private record Word(char[] letters) {
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Word other = (Word) obj;
            return Arrays.equals(getSortedLetters(), other.getSortedLetters());
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(getSortedLetters());
        }

        @Override
        public String toString() {
            return Arrays.toString(letters);
        }

        private char[] getSortedLetters() {
            char[] sortedLetters = letters.clone();
            Arrays.sort(sortedLetters);
            return sortedLetters;
        }
    }

    public String part2(List<String> input) {
        int valid = 0;

        for (String line : input) {
            Map<Word, Integer> wordCounts = new HashMap<>();

            for (String word : line.split(" ")) {
                Word w = new Word(word.toCharArray());
                wordCounts.put(w, wordCounts.getOrDefault(w, 0) + 1);
            }

            if (wordCounts.entrySet().stream().allMatch(entry -> entry.getValue() <= 1)) {
                valid++;
            }
        }

        return String.valueOf(valid);
    }
}
