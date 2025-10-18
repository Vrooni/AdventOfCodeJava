package year2016;

import java.util.List;

public class Day1_1 {
    private enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    private record Point(int x, int y) {}

public String part1(List<String> input)  {
        String moves = input.getFirst();

        int x = 0;
        int y = 0;
        Direction facing = Direction.NORTH;

        for (String move : moves.split(", ")) {
            switch (move.charAt(0)) {
                case 'R' -> facing = Direction.values()[facing.ordinal() + 1 > 3 ? 0 : facing.ordinal() + 1];
                case 'L' -> facing = Direction.values()[facing.ordinal() - 1 < 0 ? 3 : facing.ordinal() - 1];
            }

            int steps = Integer.parseInt(move.substring(1));

            switch (facing) {
                case NORTH -> y -= steps;
                case SOUTH -> y += steps;
                case EAST -> x += steps;
                case WEST -> x -= steps;
            }
        }

        return String.valueOf(Math.abs(x) + Math.abs(y));
    }
}
