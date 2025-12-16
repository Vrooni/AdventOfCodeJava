package year2024;

import java.util.*;

public class Day5_1 {
    public String part1(List<String> input) {
        int emptyLineIndex = input.indexOf("");

        Map<String, List<String>> rules = new HashMap<>();
        List<List<String>> updates = input
                .subList(emptyLineIndex + 1, input.size()).stream()
                .map(update -> Arrays.stream(update.split(",")).toList())
                .toList();

        for (String rule : input.subList(0, emptyLineIndex)) {
            String[] ruleParts = rule.split("\\|");
            rules.computeIfAbsent(ruleParts[0], _ -> new ArrayList<>()).add(ruleParts[1]);
        }

        int result = updates.stream()
                .filter(update -> isCorrect(update, rules))
                .mapToInt(update -> Integer.parseInt(update.get(update.size()/2)))
                .sum();
        
        return String.valueOf(result);
    }

    private boolean isCorrect(List<String> update, Map<String, List<String>> rules) {
        for (int i = 0; i < update.size(); i++) {
            String page = update.get(i);
            List<String> pagesAfter = rules.getOrDefault(page, new ArrayList<>());

            for (int j = 0; j < i; j++) {
                if (pagesAfter.contains(update.get(j))) {
                    return false;
                }
            }
        }

        return true;
    }
}
