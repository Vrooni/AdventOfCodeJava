package year2024;

import java.util.*;

public class Day12_2 {
    public record Position(int x, int y) {}

    public String part2(List<String> input) {
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
                List<Position> region = getRegion(current, visited, map);
                result += getPrice(region, map);
            }
        }

        return String.valueOf(result);
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

    private List<Position> getRegion(Position start, Set<Position> visited, char[][] map) {
        List<Position> region = new ArrayList<>();
        Queue<Position> queue = new LinkedList<>();
        queue.add(start);
        region.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            List<Position> neighbours = getNeighbours(current, map);
            for (Position neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    queue.add(neighbour);
                    visited.add(neighbour);
                    region.add(neighbour);
                }
            }
        }

        return region;
    }

    private int getPrice(List<Position> region, char[][] map) {
        return region.size() * (getPerimeterTopAndBottom(region, map) + getPerimeterLeftAndRight(region, map));
    }

    private int getPerimeterTopAndBottom(List<Position> region, char[][] map) {
        int fromX = region.stream().mapToInt(Position::x).min().getAsInt();
        int fromY = region.stream().mapToInt(Position::y).min().getAsInt();
        int toX = region.stream().mapToInt(Position::x).max().getAsInt();
        int toY = region.stream().mapToInt(Position::y).max().getAsInt();

        int perimeter = 0;
        char label = map[region.getFirst().y()][region.getFirst().x()];

        for (int y = fromY; y <= toY; y++) {
            boolean chainTop = false;
            boolean chainBottom = false;

            for (int x = fromX; x <= toX; x++) {

                if (!region.contains(new Position(x, y))) {
                    perimeter += getPerimeterOfChain(chainTop);
                    perimeter += getPerimeterOfChain(chainBottom);
                    chainTop = false;
                    chainBottom = false;
                    continue;
                }

                if (isSameRegionAbove(x, y, label, map)) {
                    perimeter += getPerimeterOfChain(chainTop);
                    chainTop = false;
                } else {
                    chainTop = true;
                }

                if (isSameRegionBelow(x, y, label, map)) {
                    perimeter += getPerimeterOfChain(chainBottom);
                    chainBottom = false;
                } else {
                    chainBottom = true;
                }
            }

            perimeter += getPerimeterOfChain(chainTop);
            perimeter += getPerimeterOfChain(chainBottom);
        }

        return perimeter;
    }

    private int getPerimeterLeftAndRight(List<Position> region, char[][] map) {
        int fromX = region.stream().mapToInt(Position::x).min().getAsInt();
        int fromY = region.stream().mapToInt(Position::y).min().getAsInt();
        int toX = region.stream().mapToInt(Position::x).max().getAsInt();
        int toY = region.stream().mapToInt(Position::y).max().getAsInt();

        int perimeter = 0;
        char label = map[region.getFirst().y()][region.getFirst().x()];

        for (int x = fromX; x <= toX; x++) {
            boolean chainLeft = false;
            boolean chainRight = false;

            for (int y = fromY; y <= toY; y++) {

                if (!region.contains(new Position(x, y))) {
                    perimeter += getPerimeterOfChain(chainLeft);
                    perimeter += getPerimeterOfChain(chainRight);
                    chainLeft = false;
                    chainRight = false;
                    continue;
                }

                if (isSameRegionLeft(x, y, label, map)) {
                    perimeter += getPerimeterOfChain(chainLeft);
                    chainLeft = false;
                } else {
                    chainLeft = true;
                }

                if (isSameRegionRight(x, y, label, map)) {
                    perimeter += getPerimeterOfChain(chainRight);
                    chainRight = false;
                } else {
                    chainRight = true;
                }
            }

            perimeter += getPerimeterOfChain(chainLeft);
            perimeter += getPerimeterOfChain(chainRight);
        }

        return perimeter;
    }

    private int getPerimeterOfChain(boolean chain) {
        return chain ? 1 : 0;
    }

    private boolean isSameRegionAbove(int x, int y, char wantedRegion, char[][] map) {
        return y > 0 && map[y-1][x] == wantedRegion;
    }

    private boolean isSameRegionBelow(int x, int y, char wantedRegion, char[][] map) {
        return y < map.length-1 && map[y+1][x] == wantedRegion;
    }

    private boolean isSameRegionLeft(int x, int y, char wantedRegion, char[][] map) {
        return x > 0 && map[y][x-1] == wantedRegion;
    }

    private boolean isSameRegionRight(int x, int y, char wantedRegion, char[][] map) {
        return x < map[0].length-1 && map[y][x+1] == wantedRegion;
    }
}
