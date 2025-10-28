package year2017;

import java.util.*;

public class Day4_1 {

    public String part1(List<String> input) {
        int valid = 0;

        for (String line : input) {
            Map<String, Integer> wordCounts = new HashMap<>();

            for (String word : line.split(" ")) {
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            }

            if (wordCounts.entrySet().stream().allMatch(entry -> entry.getValue() <= 1)) {
                valid++;
            }
        }

        return String.valueOf(valid);
    }
}
