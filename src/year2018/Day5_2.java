package year2018;

import java.util.List;

public class Day5_2 {
    public String part2(List<String> input) {
        String polymers = input.getFirst();

        int minLength = Integer.MAX_VALUE;
        for (char c = 'A'; c <= 'Z'; c++) {
            minLength = Math.min(minLength, react(polymers, c).length());
        }

        return String.valueOf(minLength);
    }

    private static String react(String polymers, char toDelete) {
        StringBuilder result = new StringBuilder();

        for (char polymer : polymers.toCharArray()) {
            if (polymer != toDelete && polymer != (char) (toDelete + 32)) {
                result.append(polymer);

                if (result.length() >= 2) {
                    char previousPolymer = result.charAt(result.length() - 2);
                    if (Math.abs(polymer - previousPolymer) == 32) {
                        result = new StringBuilder(result.substring(0, result.length() - 2));
                    }
                }
            }
        }

        return result.toString();
    }
}
