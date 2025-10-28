package year2023;

import java.util.*;
import java.util.stream.Collectors;

public class Day18_1 {
    private record Wall(int from, int to) {}
    private record Position(int x, int y) {}

    public String part1(List<String> input) {
        Set<Position> lagoon = followInstructions(input);
        lagoon = fill(lagoon);

        return String.valueOf(lagoon.size());
    }

    private Set<Position> followInstructions(List<String> input) {
        int x = 0;
        int y = 0;

        Set<Position> lagoon = new HashSet<>();
        lagoon.add(new Position(0, 0));

        for (String instruction : input) {
            String[] splitInstruction = instruction.split(" ");
            String direction = splitInstruction[0];
            int meters = Integer.parseInt(splitInstruction[1]);

            for (int i = 0; i < meters; i++) {
                switch (direction) {
                    case "U" -> y--;
                    case "D" -> y++;
                    case "L" -> x--;
                    case "R" -> x++;
                }

                lagoon.add(new Position(x, y));
            }
        }

        return lagoon;
    }

    private Set<Position> fill(Set<Position> lagoon) {
        int minX = lagoon.stream().mapToInt(pos -> pos.x).min().getAsInt();
        int maxX = lagoon.stream().mapToInt(pos -> pos.x).max().getAsInt();
        int minY = lagoon.stream().mapToInt(pos -> pos.y).min().getAsInt();
        int maxY = lagoon.stream().mapToInt(pos -> pos.y).max().getAsInt();

        Set<Position> filledLagoon = new HashSet<>(lagoon);
        for (int y = minY; y <= maxY; y++) {
            boolean inside = false;

            for (int x = minX; x <= maxX; ) {
                Wall wall = findNextWall(x, maxX, y, lagoon);
                if (wall == null) {
                    break;
                }

                if (inside) {
                    for (int x2 = x; x2 < wall.from; x2++) {
                        filledLagoon.add(new Position(x2, y));
                    }
                }

                // switches inside/outside
                // 1. wall is vertical (width of 1) OR
                // 2. wall is horizontal and vertical wall of start and end has different directions
                if (wall.from == wall.to ||
                        lagoon.contains(new Position(wall.from, y-1)) && lagoon.contains(new Position(wall.to, y+1)) ||
                        lagoon.contains(new Position(wall.from, y+1)) && lagoon.contains(new Position(wall.to, y-1))
                ) {
                    inside = !inside;
                }

                x = wall.to + 1;
            }
        }

        return filledLagoon;
    }

    private Wall findNextWall(int fromX, int toX, int y, Set<Position> lagoon) {
        boolean start = false;
        Integer from = null;
        int to;

        for (int x = fromX; x <= toX; x++) {
            if (lagoon.contains(new Position(x, y))) {
                if (!start) {
                    start = true;
                    from = x;
                }

                if (!lagoon.contains(new Position(x+1, y))){
                    to = x;
                    return new Wall(from, to);
                }
            }
        }

        return null;
    }
}

