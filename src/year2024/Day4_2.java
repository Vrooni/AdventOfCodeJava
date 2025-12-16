package year2024;

import java.util.List;

public class Day4_2 {
    public String part2(List<String> input) {
        int width = input.getFirst().length();
        int height = input.size();
        int count = 0;

        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width-1; j++) {
                if ((diagonalDownLeft(input, i, j) || diagonalUpRight(input, i, j))
                    && (diagonalDownRight(input, i, j) || diagonalUpLeft(input, i, j))) {
                    count++;
                }
            }
        }

        return String.valueOf(count);
    }

    private boolean diagonalDownLeft(List<String> input, int i, int j) {
        return input.get(i-1).charAt(j+1) == 'M'
                && input.get(i).charAt(j) == 'A'
                && input.get(i+1).charAt(j-1) == 'S';
    }

    private boolean diagonalUpRight(List<String> input, int i, int j) {
        return input.get(i+1).charAt(j-1) == 'M'
                && input.get(i).charAt(j) == 'A'
                && input.get(i-1).charAt(j+1) == 'S';
    }

    private boolean diagonalDownRight(List<String> input, int i, int j) {
        return input.get(i-1).charAt(j-1) == 'M'
                && input.get(i).charAt(j) == 'A'
                && input.get(i+1).charAt(j+1) == 'S';
    }

    private boolean diagonalUpLeft(List<String> input, int i, int j) {
        return input.get(i+1).charAt(j+1) == 'M'
                && input.get(i).charAt(j) == 'A'
                && input.get(i-1).charAt(j-1) == 'S';
    }
}
