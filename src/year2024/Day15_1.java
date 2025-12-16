package year2024;

import java.util.Collections;
import java.util.List;

public class Day15_1 {
    public record Position(int x, int y) {}

    public String part1(List<String> input) {
        char[][] map = readMap(input);

        Position position = getStartPosition(map);
        for (String moves : input.subList(map.length + 1, input.size())) {
            for (char move : moves.toCharArray()) {
                switch (move) {
                    case '^' -> position = moveUp(position, map);
                    case 'v' -> position = moveDown(position, map);
                    case '<' -> position = moveLeft(position, map);
                    case '>' -> position = moveRight(position, map);
                }
            }
        }

        int result = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 'O') {
                    result += 100 * y + x;
                }
            }
        }

        return String.valueOf(result);
    }

    private static Position moveUp(Position position, char[][] map) {
        return switch (map[position.y()-1][position.x()]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()-1][position.x()] = '@';
                yield new Position(position.x(), position.y() - 1);
            }
            case 'O' -> pushUp(position, map);
            default -> new Position(position.x(), position.y());
        };
    }

    private static Position moveDown(Position position, char[][] map) {
        return switch (map[position.y()+1][position.x()]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()+1][position.x()] = '@';
                yield new Position(position.x(), position.y()+1);
            }
            case 'O' -> pushDown(position, map);
            default -> new Position(position.x(), position.y());
        };
    }

    private static Position moveLeft(Position position, char[][] map) {
        return switch (map[position.y()][position.x()-1]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()][position.x()-1] = '@';
                yield new Position(position.x()-1, position.y());
            }
            case 'O' -> pushLeft(position, map);
            default -> new Position(position.x(), position.y());
        };
    }

    private static Position moveRight(Position position, char[][] map) {
        return switch (map[position.y()][position.x()+1]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()][position.x()+1] = '@';
                yield new Position(position.x()+1, position.y());
            }
            case 'O' -> pushRight(position, map);
            default -> new Position(position.x(), position.y());
        };
    }

    private static Position pushUp(Position position, char[][] map) {
        int target = -1;
        for (int y = position.y()-1; y > 0; y--) {
            if (map[y][position.x()] == '.') {
                target = y;
                break;
            } else if (map[y][position.x()] == '#') {
                return position;
            }
        }

        if (target >= 0) {
            for (int y = target; y < position.y() - 1; y++) {
                map[y][position.x()] = 'O';
            }

            map[position.y()-1][position.x()] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x(), position.y()-1);
        }

        return position;
    }

    private static Position pushDown(Position position, char[][] map) {
        int target = -1;
        for (int y = position.y()+1; y < map.length; y++) {
            if (map[y][position.x()] == '.') {
                target = y;
                break;
            } else if (map[y][position.x()] == '#') {
                return position;
            }
        }

        if (target >= 0) {
            for (int y = target; y >= position.y() + 2; y--) {
                map[y][position.x()] = 'O';
            }

            map[position.y()+1][position.x()] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x(), position.y()+1);
        }

        return position;
    }

    private static Position pushLeft(Position position, char[][] map) {
        int target = -1;
        for (int x = position.x()-1; x > 0; x--) {
            if (map[position.y()][x] == '.') {
                target = x;
                break;
            } else if (map[position.y()][x] == '#') {
                return position;
            }
        }

        if (target >= 0) {
            for (int x = target; x < position.x() - 1; x++) {
                map[position.y()][x] = 'O';
            }

            map[position.y()][position.x()-1] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x()-1, position.y());
        }

        return position;
    }

    private static Position pushRight(Position position, char[][] map) {
        int target = -1;
        for (int x = position.x()+1; x < map[0].length; x++) {
            if (map[position.y()][x] == '.') {
                target = x;
                break;
            } else if (map[position.y()][x] == '#') {
                return position;
            }
        }

        if (target >= 0) {
            for (int x = target; x >= position.x() + 2; x--) {
                map[position.y()][x] = 'O';
            }

            map[position.y()][position.x()+1] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x()+1, position.y());
        }

        return position;
    }

    private static char[][] readMap(List<String> input) {
        int width = input.getFirst().length();
        int height = input.lastIndexOf(String.join("", Collections.nCopies(width, "#"))) + 1;

        char[][] map = new char[height][width];

        for (int i = 0; i < height; i++) {
            map[i] = input.get(i).toCharArray();
        }

        return map;
    }

    private static Position getStartPosition(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == '@') {
                    return new Position(x, y);
                }
            }
        }

        return new Position(0, 0);
    }
}
