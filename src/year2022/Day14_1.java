package year2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day14_1 {
    record Position(int x, int y) { }

    public String part1(List<String> lines) {
        Set<Position> cave = new HashSet<>();

        for (String line : lines) {
            String[] positions = line.split(" -> ");

            for (int i = 1; i < positions.length; i++) {
                int x1 = Integer.parseInt(positions[i-1].split(",")[0]);
                int y1 = Integer.parseInt(positions[i-1].split(",")[1]);

                int x2 = Integer.parseInt(positions[i].split(",")[0]);
                int y2 = Integer.parseInt(positions[i].split(",")[1]);

                for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                    for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                        cave.add(new Position(x, y));
                    }
                }
            }
        }

        int fallenSand = 0;
        sand: while(true) {
            Position currentPosition = new Position(500, 0);

            while(true) {
                if (currentPosition.y + 1 > cave.stream().mapToInt(point -> point.y).max().getAsInt()) {
                    break sand;
                }

                if (!cave.contains(new Position(currentPosition.x, currentPosition.y + 1))) {
                    currentPosition = new Position(currentPosition.x, currentPosition.y + 1);
                } else if (!cave.contains(new Position(currentPosition.x - 1, currentPosition.y + 1))) {
                    currentPosition = new Position(currentPosition.x - 1, currentPosition.y + 1);
                } else if (!cave.contains(new Position(currentPosition.x + 1, currentPosition.y + 1))) {
                    currentPosition = new Position(currentPosition.x + 1, currentPosition.y + 1);
                } else {
                    cave.add(currentPosition);
                    fallenSand++;
                    break;
                }
            }
        }

        return String.valueOf(fallenSand);
    }
}
