package year2024;

import java.util.*;

public class Day15_2 {
    public record Position(int x, int y) {}

    public String part2(List<String> input) {
        char[][] map = readMap(input);
        map = extendMap(map);

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
                if (map[y][x] == '[') {
                    result += 100 * y + x;
                }
            }
        }

        return String.valueOf(result);
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

    private static Position moveUp(Position position, char[][] map) {
        return switch (map[position.y()-1][position.x()]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()-1][position.x()] = '@';
                yield new Position(position.x(), position.y() - 1);
            }
            case '#' -> new Position(position.x(), position.y());
            default -> pushUp(position, map);
        };
    }

    private static Position moveDown(Position position, char[][] map) {
        return switch (map[position.y()+1][position.x()]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()+1][position.x()] = '@';
                yield new Position(position.x(), position.y()+1);
            }
            case '#' -> new Position(position.x(), position.y());
            default -> pushDown(position, map);
        };
    }

    private static Position moveLeft(Position position, char[][] map) {
        return switch (map[position.y()][position.x()-1]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()][position.x()-1] = '@';
                yield new Position(position.x()-1, position.y());
            }
            case '#' -> new Position(position.x(), position.y());
            default -> pushLeft(position, map);
        };
    }

    private static Position moveRight(Position position, char[][] map) {
        return switch (map[position.y()][position.x()+1]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()][position.x()+1] = '@';
                yield new Position(position.x()+1, position.y());
            }
            case '#' -> new Position(position.x(), position.y());
            default -> pushRight(position, map);
        };
    }

    private static Position pushUp(Position position, char[][] map) {
        Queue<Position> toCheck = new LinkedList<>();
        List<Position> toMove = new ArrayList<>();

        boolean canMove = true;
        Position newPosition = new Position(position.x(), position.y() - 1);
        toCheck.add(newPosition);

        while (!toCheck.isEmpty()) {
            Position posToCheck = toCheck.poll();
            int x = posToCheck.x();
            int y = posToCheck.y();

            char c = map[y][x];

            if (c == '[') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x+1, y))) toMove.add(new Position(x+1, y));

                toCheck.add(new Position(x, y-1));
                toCheck.add(new Position(x+1, y-1));
            } else if (c == ']') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x-1, y))) toMove.add(new Position(x-1, y));

                toCheck.add(new Position(x, y-1));
                toCheck.add(new Position(x-1, y-1));
            } else if (c == '#') {
                canMove = false;
                break;
            }
        }


        if (canMove) {
            toMove.sort(Comparator.comparingInt(Position::y));
            for (Position box : toMove) {
                map[box.y()-1][box.x()] = map[box.y()][box.x()];
                map[box.y()][box.x()] = '.';
            }

            map[position.y()-1][position.x()] = '@';
            map[position.y()][position.x()] = '.';
            return newPosition;
        }

        return new Position(position.x(), position.y());
    }

    private static Position pushDown(Position position, char[][] map) {
        Queue<Position> toCheck = new LinkedList<>();
        List<Position> toMove = new ArrayList<>();

        boolean canMove = true;
        Position newPosition = new Position(position.x(), position.y() + 1);
        toCheck.add(newPosition);

        while (!toCheck.isEmpty()) {
            Position posToCheck = toCheck.poll();
            int x = posToCheck.x();
            int y = posToCheck.y();

            char c = map[y][x];

            if (c == '[') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x+1, y))) toMove.add(new Position(x+1, y));

                toCheck.add(new Position(x, y+1));
                toCheck.add(new Position(x+1, y+1));
            } else if (c == ']') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x-1, y))) toMove.add(new Position(x-1, y));

                toCheck.add(new Position(x, y+1));
                toCheck.add(new Position(x-1, y+1));
            } else if (c == '#') {
                canMove = false;
                break;
            }
        }


        if (canMove) {
            toMove.sort(Comparator.comparingInt(Position::y).reversed());
            for (Position box : toMove) {
                map[box.y()+1][box.x()] = map[box.y()][box.x()];
                map[box.y()][box.x()] = '.';
            }

            map[position.y()+1][position.x()] = '@';
            map[position.y()][position.x()] = '.';
            return newPosition;
        }

        return new Position(position.x(), position.y());
    }

    private static Position pushLeft(Position position, char[][] map) {
        Queue<Position> toCheck = new LinkedList<>();
        List<Position> toMove = new ArrayList<>();

        boolean canMove = true;
        Position newPosition = new Position(position.x() - 1, position.y());
        toCheck.add(newPosition);

        while (!toCheck.isEmpty()) {
            Position posToCheck = toCheck.poll();
            int x = posToCheck.x();
            int y = posToCheck.y();

            char c = map[y][x];

            if (c == ']') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x-1, y))) toMove.add(new Position(x-1, y));

                toCheck.add(new Position(x-2, y));
            } else if (c == '#') {
                canMove = false;
                break;
            }
        }


        if (canMove) {
            toMove.sort(Comparator.comparingInt(Position::x));
            for (Position box : toMove) {
                map[box.y()][box.x()-1] = map[box.y()][box.x()];
                map[box.y()][box.x()] = '.';
            }

            map[position.y()][position.x()-1] = '@';
            map[position.y()][position.x()] = '.';
            return newPosition;
        }

        return new Position(position.x(), position.y());
    }

    private static Position pushRight(Position position, char[][] map) {
        Queue<Position> toCheck = new LinkedList<>();
        List<Position> toMove = new ArrayList<>();

        boolean canMove = true;
        Position newPosition = new Position(position.x() + 1, position.y());
        toCheck.add(newPosition);

        while (!toCheck.isEmpty()) {
            Position posToCheck = toCheck.poll();
            int x = posToCheck.x();
            int y = posToCheck.y();

            char c = map[y][x];

            if (c == '[') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x+1, y))) toMove.add(new Position(x+1, y));

                toCheck.add(new Position(x+2, y));
            } else if (c == '#') {
                canMove = false;
                break;
            }
        }


        if (canMove) {
            toMove.sort(Comparator.comparingInt(Position::x).reversed());
            for (Position box : toMove) {
                map[box.y()][box.x()+1] = map[box.y()][box.x()];
                map[box.y()][box.x()] = '.';
            }

            map[position.y()][position.x()+1] = '@';
            map[position.y()][position.x()] = '.';
            return newPosition;
        }

        return new Position(position.x(), position.y());
    }

    private static char[][] extendMap(char[][] map) {
        char[][] extendedMap = new char[map.length][map[0].length * 2];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                char c = map[i][j];
                if (c == '#' || c == '.') {
                    extendedMap[i][j*2] = c;
                    extendedMap[i][j*2 + 1] = c;
                } else if (c == 'O') {
                    extendedMap[i][j*2] = '[';
                    extendedMap[i][j*2 + 1] = ']';
                } else if (c == '@') {
                    extendedMap[i][j*2] = c;
                    extendedMap[i][j*2 + 1] = '.';
                }
            }
        }

        return extendedMap;
    }
}
