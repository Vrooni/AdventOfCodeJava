package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day1_2 {
    private enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    private record Point(int x, int y) {}

public String part2(List<String> input)  {
        String moves = input.getFirst();

        int x = 0;
        int y = 0;
        Direction facing = Direction.NORTH;

        List<Point> visited = new ArrayList<>();

        for (String move : moves.split(", ")) {
            switch (move.charAt(0)) {
                case 'R' -> facing = Direction.values()[facing.ordinal() + 1 > 3 ? 0 : facing.ordinal() + 1];
                case 'L' -> facing = Direction.values()[facing.ordinal() - 1 < 0 ? 3 : facing.ordinal() - 1];
            }

            int steps = Integer.parseInt(move.substring(1));

            switch (facing) {
                case NORTH -> {
                    for (int i = y; i > y - steps; i--) {
                        if (visited.contains(new Point(x, i))) {
                            return String.valueOf(Math.abs(x) + Math.abs(i));
                        }
                        visited.add(new Point(x, i));
                    }

                    y -= steps;
                }
                case SOUTH -> {
                    for (int i = y; i < y + steps; i++) {
                        if (visited.contains(new Point(x, i))) {
                            return String.valueOf(Math.abs(x) + Math.abs(i));
                        }
                        visited.add(new Point(x, i));
                    }

                    y += steps;
                }
                case EAST -> {
                    for (int i = x; i < x + steps; i++) {
                        if (visited.contains(new Point(i, y))) {
                            return String.valueOf(Math.abs(i) + Math.abs(y));
                        }
                        visited.add(new Point(i, y));
                    }

                    x += steps;
                }
                case WEST -> {
                    for (int i = x; i > x - steps; i--) {
                        if (visited.contains(new Point(i, y))) {
                            return String.valueOf(Math.abs(i) + Math.abs(y));
                        }
                        visited.add(new Point(i, y));
                    }

                    x -= steps;
                }
            }
        }

        return "-1";
    }
}
