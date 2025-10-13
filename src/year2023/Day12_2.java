package year2023;

import java.util.*;

public class Day12_2 {
    record SpringInformation(String springs, List<Integer> damagedSprings) {
    }

    private final Map<SpringInformation, Long> memoization = new HashMap<>();

    public String part2(List<String> lines) {
        long result = 0;

        for (String line : lines) {
            String[] splitLine = line.split(" ");

            String springs = expandSprings(splitLine[0]);
            List<Integer> damagedSprings = expandDamagedSprings(getDamagedSprings(splitLine[1]));

            result += getCombinations(springs, damagedSprings);
        }

        return String.valueOf(result);
    }

    private List<Integer> getDamagedSprings(String damagedSprings) {
        return Arrays.stream(damagedSprings.split(","))
                .map(Integer::parseInt)
                .toList();
    }

    private String expandSprings(String springs) {
        StringBuilder expandedSprings = new StringBuilder(springs);

        for (int i = 0; i < 4; i++) {
            expandedSprings.append("?").append(springs);
        }

        return expandedSprings.toString();
    }

    private List<Integer> expandDamagedSprings(List<Integer> damagedSprings) {
        List<Integer> expandedDamagedSprings = new ArrayList<>(damagedSprings);

        for (int i = 0; i < 4; i++) {
            expandedDamagedSprings.addAll(damagedSprings);
        }

        return expandedDamagedSprings;
    }

    private long getCombinations(String springs, List<Integer> damagedSprings) {
        long combinations = 0;

        SpringInformation springInformation = new SpringInformation(springs, damagedSprings);
        if (memoization.containsKey(springInformation)) {
            return memoization.get(springInformation);
        }

        // reached end
        if (springs.isBlank()) {
            return damagedSprings.isEmpty() ? 1 : 0;
        }

        switch (springs.charAt(0)) {
            case '.' -> combinations = getCombinations(springs.substring(1), damagedSprings);

            case '?' -> combinations = getCombinations("." + springs.substring(1), damagedSprings)
                    + getCombinations('#' + springs.substring(1), damagedSprings);

            case '#' -> {
                if (damagedSprings.isEmpty()) {
                    break;
                }

                int damaged = damagedSprings.getFirst();
                if (damaged > springs.length()) {
                    break;
                }

                if (!springs.chars().limit(damaged).allMatch(spring -> spring == '#' || spring == '?')) {
                    break;
                }

                if (damaged == springs.length()) {
                    combinations = damagedSprings.size() == 1 ? 1 : 0;
                    break;
                }

                if (springs.charAt(damaged) == '#') {
                    break;
                }

                combinations = getCombinations(springs.substring(damaged + 1),
                        damagedSprings.subList(1, damagedSprings.size()));
            }
        }

        memoization.put(springInformation, combinations);
        return combinations;
    }
}
