package year2025;

import java.util.*;

public class Day7_1 {
    record Position(int x, int y) {}

    public String part1(List<String> input) {
        Position start = new Position(input.getFirst().indexOf("S"), 0);
        Set<Position> splitPositions = new HashSet<>();

        Queue<Position> queue = new LinkedList<>();
        queue.add(start);

        Set<Position> visited = new HashSet<>();
        visited.add(start);

        while (!queue.isEmpty()) {
            Position position = queue.poll();

            for (int i = position.y + 1; i < input.size(); i++) {
                if (input.get(i).charAt(position.x) == '^') {
                    splitPositions.add(new Position(position.x, i));
                    Position pos1 = new Position(position.x - 1, i);
                    Position pos2 = new Position(position.x + 1, i);

                    if (!visited.contains(pos1) && pos1.x >= 0 && pos1.x < input.getFirst().length()) {
                        visited.add(pos1);
                        queue.add(pos1);
                    }

                    if (!visited.contains(pos2) && pos2.x >= 0 && pos2.x < input.getFirst().length()) {
                        visited.add(pos2);
                        queue.add(pos2);
                    }

                    break;
                }
            }
        }

        return String.valueOf(splitPositions.size());
    }
}
