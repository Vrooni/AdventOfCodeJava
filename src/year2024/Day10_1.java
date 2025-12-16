package year2024;

import java.util.*;

public class Day10_1 {
    public record Position(int x, int y) {}

    public String part1(List<String> input) {
        int height = input.size();
        int width = input.getFirst().length();

        int[][] map = new int[height][width];
        List<Position> positionsTrailHead = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
               int currentHeight = Character.getNumericValue(input.get(y).charAt(x));
                map[y][x] = currentHeight;

                if (currentHeight == 0) {
                    positionsTrailHead.add(new Position(x, y));
                }
            }
        }

        return String.valueOf(positionsTrailHead.stream().mapToInt(trailHead -> getScore(map, trailHead)).sum());
    }

    private int getScore(int[][] map, Position start) {
        Set<Position> visited = new HashSet<>();
        Queue<Position> queue = new LinkedList<>();
        visited.add(start);
        queue.add(start);

        Set<Position> positionsHeight9 = new HashSet<>();
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            int height = map[current.y()][current.x()];

            if (height == 9) {
                positionsHeight9.add(current);
                continue;
            }

            List<Position> neighbours = getNeighbours(map, current, height);
            for (Position neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }

        return positionsHeight9.size();
    }

    private List<Position> getNeighbours(int[][] map, Position position, int height) {
        List<Position> neighbours = new ArrayList<>();
        int x = position.x();
        int y = position.y();

        if (x > 0 && map[y][x-1] == height + 1) {
            neighbours.add(new Position(x-1, y));
        }

        if (y > 0 && map[y-1][x] == height + 1) {
            neighbours.add(new Position(x, y-1));
        }

        if (x < map[0].length - 1 && map[y][x+1] == height + 1) {
            neighbours.add(new Position(x+1, y));
        }

        if (y < map.length - 1 && map[y+1][x] == height + 1) {
            neighbours.add(new Position(x, y+1));
        }

        return neighbours;
    }
}
