package year2024;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6_2 {
    public record Position(int x, int y) {}
    private record Move(Position position, Direction direction) {}

    public enum Direction {
        UP, RIGHT, DOWN, LEFT;

        private static final Direction[] values = values();

        public Direction next() {
            return values[(this.ordinal() + 1) % values.length];
        }
    }

    public String part2(List<String> input) {
        char[][] map = input.stream().map(String::toCharArray).toArray(char[][]::new);

        int height = map.length;
        int width = map[0].length;
        Position position = getPosition(height, width, map);
        Direction direction = switch (map[position.y()][position.x()]) {
            case '^' -> Direction.UP;
            case 'v' -> Direction.DOWN;
            case '<' -> Direction.LEFT;
            default -> Direction.RIGHT;
        };

        int possibleSolutions = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (map[i][j] == '.') {
                    map[i][j] = '#';

                    if (causeLoop(height, width, map, position, direction)) {
                        possibleSolutions++;
                    }

                    map[i][j] = '.';
                }
            }
        }

        return String.valueOf(possibleSolutions);
    }

    private Position getPosition(int height, int width, char[][] map) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char object = map[i][j];

                if (object == '^' || object == 'v' || object == '<' || object == '>') {
                    return new Position(j, i);
                }
            }
        }

        return new Position(-1, -1);
    }

    private boolean causeLoop(int height, int width, char[][] map, Position position, Direction direction) {
        Set<Move> visited = new HashSet<>();
        while (true) {
            if (visited.contains(new Move(position, direction))) {
                return true;
            }

            visited.add(new Move(position, direction));

            Position target = new Position(-1, -1);
            switch (direction) {
                case UP -> target = new Position(position.x(), position.y() - 1);
                case DOWN -> target = new Position(position.x(), position.y() + 1);
                case LEFT -> target = new Position(position.x() - 1, position.y());
                case RIGHT -> target = new Position(position.x() + 1, position.y());
            }

            if (target.x() < 0 || target.y() < 0 || target.x() >= width || target.y() >= height) {
                return false;
            } else if (map[target.y()][target.x()] == '#') {
                direction = direction.next();
            } else {
                position = target;
            }
        }
    }
}
