package year2024;

import java.util.*;

public class Day19_2 {
    private static final Map<String, Long> memory = new HashMap<>();

    public String part2(List<String> input) {
        String[] towels = input.getFirst().split(", ");

        long combinations = 0L;
        for (int i = 2; i < input.size(); i++) {
            combinations += getCombinations(input.get(i), towels);
        }

        return String.valueOf(combinations);
    }

    private long getCombinations(String design, String[] towels) {
        if (design.isEmpty()) {
            return 1;
        }

        Long combinations = memory.get(design);
        if (combinations != null) {
            return combinations;
        }

        combinations = 0L;
        List<String> possibleTowels = Arrays.stream(towels)
                .filter(design::startsWith)
                .toList();

        for (String possibleTowel : possibleTowels) {
            combinations += getCombinations(design.replaceFirst(possibleTowel, ""), towels);
        }

        memory.put(design, combinations);
        return combinations;
    }
}
