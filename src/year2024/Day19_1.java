package year2024;

import java.util.*;

public class Day19_1 {
    public String part1(List<String> input) {
        String[] towels = input.getFirst().split(", ");

        int possibleDesigns = 0;
        for (int i = 2; i < input.size(); i++) {
            if (isPossible(input.get(i), towels)) {
                possibleDesigns++;
            }
        }

        return String.valueOf(possibleDesigns);
    }

    private boolean isPossible(String design, String[] towels) {
        Queue<String> queue = new LinkedList<>();
        queue.add(design);
        Set<String> seenDesigns = new HashSet<>();
        seenDesigns.add(design);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.isEmpty()) {
                return true;
            }

            List<String> possibleTowels = Arrays.stream(towels)
                    .filter(current::startsWith)
                    .toList();

            for (String possibleTowel : possibleTowels) {
                String remainingDesign = current.replaceFirst(possibleTowel, "");
                if (!seenDesigns.contains(remainingDesign)) {
                    queue.add(remainingDesign);
                    seenDesigns.add(remainingDesign);
                }
            }
        }

        return false;
    }
}
