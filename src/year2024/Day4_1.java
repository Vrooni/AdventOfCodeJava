package year2024;

import java.util.List;

public class Day4_1 {
    public String part1(List<String> input) {
        int width = input.getFirst().length();
        int height = input.size();
        int count = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Horizontal forwards
                if (j <= width - 4 && input.get(i).startsWith("XMAS", j)) {
                    count++;
                }

                // Horizontal backwards
                if (j >= 3 && input.get(i).substring(j - 3, j + 1).equals("SAMX")) {
                    count++;
                }

                // Vertical forwards
                if (i <= height - 4
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i + 1).charAt(j) == 'M'
                        && input.get(i + 2).charAt(j) == 'A'
                        && input.get(i + 3).charAt(j) == 'S') {
                    count++;
                }

                // Vertical backwards
                if (i >= 3
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i - 1).charAt(j) == 'M'
                        && input.get(i - 2).charAt(j) == 'A'
                        && input.get(i - 3).charAt(j) == 'S') {
                    count++;
                }

                // Diagonal down-right
                if (i <= height - 4 && j <= width - 4
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i + 1).charAt(j + 1) == 'M'
                        && input.get(i + 2).charAt(j + 2) == 'A'
                        && input.get(i + 3).charAt(j + 3) == 'S') {
                    count++;
                }

                // Diagonal up-left
                if (i >= 3 && j >= 3
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i - 1).charAt(j - 1) == 'M'
                        && input.get(i - 2).charAt(j - 2) == 'A'
                        && input.get(i - 3).charAt(j - 3) == 'S') {
                    count++;
                }

                // Diagonal down-left
                if (i <= height - 4 && j >= 3
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i + 1).charAt(j - 1) == 'M'
                        && input.get(i + 2).charAt(j - 2) == 'A'
                        && input.get(i + 3).charAt(j - 3) == 'S') {
                    count++;
                }

                // Diagonal up-right
                if (i >= 3 && j <= width - 4
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i - 1).charAt(j + 1) == 'M'
                        && input.get(i - 2).charAt(j + 2) == 'A'
                        && input.get(i - 3).charAt(j + 3) == 'S') {
                    count++;
                }
            }
        }

        return String.valueOf(count);
    }
}
