package year2025;

import java.util.List;

public class Day4_2 {

    public String part2(List<String> input) {
        char[][] map = readInput(input);
        int height = input.size();
        int width = input.getFirst().length();

        int removedRolls = 0;
        boolean removed;
        do {
            removed = false;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    if (map[y][x] != '@') {
                        continue;
                    }

                    int rollCount = 0;
                    for (int y1 = Math.max(0, y - 1); y1 <= Math.min(height - 1, y + 1); y1++) {
                        for (int x1 = Math.max(0, x - 1); x1 <= Math.min(width - 1, x + 1); x1++) {

                            if (map[y1][x1] == '@') {
                                rollCount++;
                            }
                        }
                    }

                    if (rollCount <= 4) {
                        removedRolls++;
                        removed = true;
                        map[y][x] = '.';
                    }
                }
            }
        } while (removed);

        return String.valueOf(removedRolls);
    }

    public char[][] readInput(List<String> input) {
        char[][] map = new char[input.size()][];

        for (int i = 0; i < input.size(); i++) {
            map[i] = input.get(i).toCharArray();
        }

        return map;
    }
}

