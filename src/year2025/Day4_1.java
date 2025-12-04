package year2025;

import java.util.List;

public class Day4_1 {

    public String part1(List<String> input) {
        int height = input.size();
        int width = input.getFirst().length();
        int reachable = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (input.get(y).charAt(x) != '@') {
                    continue;
                }

                int rollCount = 0;
                for (int y1 = Math.max(0, y - 1); y1 <= Math.min(height - 1, y + 1); y1++) {
                    for (int x1 = Math.max(0, x - 1); x1 <= Math.min(width - 1, x + 1); x1++) {

                        if (input.get(y1).charAt(x1) == '@') {
                            rollCount++;
                        }
                    }
                }

                if (rollCount <= 4) {
                    reachable++;
                }
            }
        }

        return String.valueOf(reachable);
    }
}

