package year2024;

import java.util.*;

public class Day12_1 {
    public record Position(int x, int y) {}

    public String part1(List<String> input) {
        char[][] map = input.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        int height = map.length;
        int width = map[0].length;
        Queue<Position> queue = new LinkedList<>();
        Set<Position> visited = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                queue.add(new Position(x, y));
            }
        }

        int result = 0;
        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (!visited.contains(current)) {
                result += getPrice(current, visited, map);
            }
        }

        return String.valueOf(result);
    }

    private int getPrice(Position start, Set<Position> visited, char[][] map) {
        Queue<Position> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        int area = 0;
        int perimeter = 0;
        while (!queue.isEmpty()) {
            Position current = queue.poll();

            List<Position> neighbours = getNeighbours(current, map);
            for (Position neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    queue.add(neighbour);
                    visited.add(neighbour);
                }
            }

            area++;
            perimeter += (4 - neighbours.size());
        }

        return area * perimeter;
    }

    private List<Position> getNeighbours(Position current, char[][] map) {
        int x = current.x();
        int y = current.y();
        char region = map[y][x];

        List<Position> neighbours = new ArrayList<>();
        if (x > 0 && map[y][x-1] == region) {
            neighbours.add(new Position(x-1, y));
        }

        if (y > 0 && map[y-1][x] == region) {
            neighbours.add(new Position(x, y-1));
        }

        if (x < map[0].length-1 && map[y][x+1] == region) {
            neighbours.add(new Position(x+1, y));
        }

        if (y < map.length-1 && map[y+1][x] == region) {
            neighbours.add(new Position(x, y+1));
        }

        return neighbours;
    }
}
