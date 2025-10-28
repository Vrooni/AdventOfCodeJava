package year2018;

import java.util.List;

public class Day5_1 {
    public String part1(List<String> input) {
        String polymers = input.getFirst();
        return String.valueOf(react(polymers).length());
    }

    private static String react(String polymers) {
        boolean reacts;

        do {
            StringBuilder result = new StringBuilder();
            reacts = false;
            for (int i = 0; i < polymers.length(); i++) {
                char polymer = polymers.charAt(i);
                char nextPolymer = i == polymers.length() - 1 ? ' ' : polymers.charAt(i + 1);

                if (polymer == (char) (nextPolymer - 32) || polymer == (char) (nextPolymer + 32)) {
                    i++;
                    reacts = true;
                    continue;
                }

                result.append(polymer);
            }

            polymers = result.toString();
        } while (reacts);

        return polymers;
    }
}
