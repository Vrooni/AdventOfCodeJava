package year2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day17_1 {
    private static final char SAND = '.';
    private static final char CLAY = '#';
    private static final char FLOWING_WATER = '|';
    private static final char STILL_WATER = '~';

    public record Position(int x, int y) {}

    public String part1(List<String> input) {
        List<Position> positions = readInput(input);

        int fromX = positions.stream().map(Position::x).min(Integer::compareTo).get() - 1;
        int toX = positions.stream().map(Position::x).max(Integer::compareTo).get() + 1;
        int fromY = positions.stream().map(Position::y).min(Integer::compareTo).get();
        int toY = positions.stream().map(Position::y).max(Integer::compareTo).get();

        char[][] map = getMap(positions, toX + 1, toY + 1);
        flow(map, 500, 0, fromX, toX, toY);

        return String.valueOf(getWater(map, fromX, toX, fromY, toY));
    }

    private List<Position> readInput(List<String> input) {
        List<Position> positions = new ArrayList<>();

        for (String line : input) {
            String[] splitLine = line.split(", ");

            if (splitLine[0].charAt(0) == 'x') {
                int x = Integer.parseInt(splitLine[0].replace("x=", ""));
                int[] splitCoordinate = getRange(splitLine[1], "y=");
                int[] yRange = IntStream.rangeClosed(splitCoordinate[0], splitCoordinate[1]).toArray();

                for (int y : yRange) {
                    positions.add(new Position(x, y));
                }
            } else {
                int y = Integer.parseInt(splitLine[0].replace("y=", ""));
                int[] splitCoordinate = getRange(splitLine[1], "x=");
                int[] xRange = IntStream.rangeClosed(splitCoordinate[0], splitCoordinate[1]).toArray();

                for (int x : xRange) {
                    positions.add(new Position(x, y));
                }
            }
        }

        return positions;
    }

    private int[] getRange(String coordinate, String replace) {
        return Arrays.stream(coordinate
                        .replace(replace, "")
                        .split("\\.\\."))
                .mapToInt(Integer::parseInt).toArray();
    }

    private char[][] getMap(List<Position> positions, int width, int height) {
        char[][] map = new char[height][width];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                map[y][x] = SAND;
            }
        }

        for (Position position : positions) {
            map[position.y()][position.x()] = CLAY;
        }

        return map;
    }

    private void flow(char[][] map, int x, int y, int fromX, int toX, int toY) {
        if (y >= toY) {
            return;
        }

        //water flows down
        if (map[y+1][x] == SAND) {
            map[y+1][x] = FLOWING_WATER;
            flow(map, x, y+1, fromX, toX, toY);
        }

        //water flows to left
        if (map[y][x-1] == SAND && (map[y+1][x] == STILL_WATER || map[y+1][x] == CLAY)) {
            map[y][x-1] = FLOWING_WATER;
            flow(map, x-1, y, fromX, toX, toY);
        }

        //water flows to left
        if (map[y][x+1] == SAND && (map[y+1][x] == STILL_WATER || map[y+1][x] == CLAY)) {
            map[y][x+1] = FLOWING_WATER;
            flow(map, x+1, y, fromX, toX, toY);
        }

        if (map[y][x] == STILL_WATER) {
            return;
        }

        //water fills
        int left = getLeftWall(map, fromX, x, y);
        int right = getRightWall(map, toX, x, y);
        if (left != -1 && right != -1 && hasFloor(map, left, right, y)) {
            for (x = left; x <= right; x++) {
                map[y][x] = STILL_WATER;
            }
        }
    }

    private int getLeftWall(char[][] map, int fromX, int x, int y) {
        for (; x > fromX; x--) {
            if (map[y][x-1] == CLAY) {
                return x;
            }
        }

        return -1;
    }

    private int getRightWall(char[][] map, int toX, int x, int y) {
        for (; x < toX; x++) {
            if (map[y][x+1] == CLAY) {
                return x;
            }
        }

        return -1;
    }

    private boolean hasFloor(char[][] map, int fromX, int toX, int y) {
        for (int x = fromX; x <= toX; x++) {
            if (map[y+1][x] == SAND) {
                return false;
            }
        }

        return true;
    }

    private int getWater(char[][] map, int fromX, int toX, int fromY, int toY) {
        int water = 0;

        for (int y = fromY; y <= toY; y++) {
            for (int x = fromX; x <= toX; x++) {
                if (map[y][x] == STILL_WATER || map[y][x] == FLOWING_WATER) {
                    water++;
                }
            }
        }

        return water;
    }
}
