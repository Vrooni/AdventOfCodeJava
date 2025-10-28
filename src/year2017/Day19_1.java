package year2017;

import java.util.List;

public class Day19_1 {
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public String part1(List<String> map) {
        StringBuilder path = new StringBuilder();
        int x = map.getFirst().indexOf("|");
        int y = 0;

        Direction direction = Direction.DOWN;
        char current = charAt(map, x, y);

        while (current != ' ') {
            switch (current) {
                case '+' -> direction = nextDirection(x, y, direction, map);
                case '|', '-' -> { /* do nothing */ }
                default -> path.append(current);
            }

            switch (direction) {
                case UP -> y--;
                case DOWN -> y++;
                case LEFT -> x--;
                case RIGHT -> x++;
            }

            current = charAt(map, x, y);
        }

        return path.toString();
    }

    private Direction nextDirection(int x, int y, Direction direction, List<String> map) {
        if (direction != Direction.DOWN && charAt(map, x, y-1) != ' ') {
            return Direction.UP;
        }

        if (direction != Direction.UP && charAt(map, x, y+1) != ' ') {
            return Direction.DOWN;
        }

        if (direction != Direction.RIGHT && charAt(map, x-1, y) != ' ') {
            return Direction.LEFT;
        }

        if (direction != Direction.LEFT && charAt(map, x+1, y) != ' ') {
            return Direction.RIGHT;
        }

        throw new RuntimeException("No next direction");
    }

    private char charAt(List<String> map, int x, int y) {
        try {
            return map.get(y).charAt(x);
        } catch (IndexOutOfBoundsException e) {
            return ' ';
        }
    }
}
