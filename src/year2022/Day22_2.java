package year2022;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22_2 {
    /**
     * The cube net looks as follows (As far as I know the cube net is the same for every input):
     *     1,0 2,0
     *     1,1
     * 0,2 1,2
     * 0,3
     * These represent the cube faces. And in connections.txt it is defined as follows:
     * fromFace–direction–>toFace–new-direction
     * So for each direction it is defined where I end up when I cross an edge.
     * The file is called in #createEdges().
     **/

    private static final int WIDTH = 50;
    private record Position(int x, int y) {}
    private enum Element { NONE, EMPTY, WALL }
    private enum Direction {
        RIGHT ,
        LEFT,
        DOWN,
        UP;

        public static Direction parse(String s) {
            switch (s) {
                case "U" -> {
                    return UP;
                }
                case "D" -> {
                    return DOWN;
                }
                case "L" -> {
                    return LEFT;
                }
                case "R" -> {
                    return RIGHT;
                }
            }

            throw new RuntimeException("No Direction parseable");
        }
    }
    private enum Side {
        RIGHT, LEFT, TOP, BOTTOM;

        public static Side same(Direction direction) {
            return switch (direction) {
                case LEFT -> LEFT;
                case RIGHT -> RIGHT;
                case UP -> TOP;
                case DOWN -> BOTTOM;
            };
        }

        public static Side invert(Direction direction) {
            return switch (direction) {
                case LEFT -> RIGHT;
                case RIGHT -> LEFT;
                case UP -> BOTTOM;
                case DOWN -> TOP;
            };
        }
    }

    private record Edge(Position position, Direction direction) {}

    private static class MyPosition {
        int x;
        int y;
        Direction direction;

        MyPosition(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        MyPosition(MyPosition position) {
            this.x = position.x;
            this.y = position.y;
            this.direction = position.direction;
        }
    }

    public String part2(List<String> lines) throws IOException {
        List<List<Element>> map = createMap(lines);
        Map<Edge, Edge> edges = createEdges();
        String moves = lines.getLast();

        MyPosition position = moveThrowMap(map, edges, moves);

        int directionPoint = -1;
        switch (position.direction) {
            case RIGHT -> directionPoint = 0;
            case DOWN -> directionPoint = 1;
            case LEFT -> directionPoint = 2;
            case UP -> directionPoint = 3;
        }

        return String.valueOf(1000*(position.y+1) + 4*(position.x+1) + directionPoint);
    }

    private static List<List<Element>> createMap(List<String> lines) {
        List<List<Element>> map = new ArrayList<>();
        List<String> mapLines = lines.subList(0, lines.size() - 2);

        for (String mapLine : mapLines) {
            List<Element> row = new ArrayList<>();

            for (int x = 0; x < mapLine.length(); x++) {
                switch (mapLine.charAt(x)) {
                    case '#' -> row.add(x, Element.WALL);
                    case '.' -> row.add(x, Element.EMPTY);
                    default -> row.add(x, Element.NONE);
                }
            }

            map.add(row);
        }

        return map;
    }

    private static Map<Edge, Edge> createEdges() throws IOException {
        Map<Edge, Edge> edges = new HashMap<>();
        List<String> lines = Files.readAllLines(
                Path.of("src/year2022/connections.txt"),
                StandardCharsets.UTF_8
        );
        Pattern pattern = Pattern.compile("(\\d),(\\d)-(\\D)->(\\d),(\\d)-(\\D)");

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                int fromX = Integer.parseInt(matcher.group(1));
                int fromY = Integer.parseInt(matcher.group(2));
                Direction fromDir = Direction.parse(matcher.group(3));

                int toX = Integer.parseInt(matcher.group(4));
                int toY = Integer.parseInt(matcher.group(5));
                Direction toDir = Direction.parse(matcher.group(6));

                Position fromPosition = new Position(fromX, fromY);
                Position toPosition = new Position(toX, toY);

                List<Position> fromEdge = createEdge(fromPosition, Side.same(fromDir));
                List<Position> toEdge = createEdge(toPosition, Side.invert(toDir));

                if (!isSame(fromDir, toDir)) {
                    Collections.reverse(toEdge);
                }

                for (int i = 0; i < WIDTH; i++) {
                    Edge from = new Edge(fromEdge.get(i), fromDir);
                    Edge to = new Edge(toEdge.get(i), toDir);
                    edges.put(from, to);
                }
            }
        }

        return edges;
    }

    private static List<Position> createEdge(Position position, Side side) {
        List<Position> edge = new ArrayList<>();

        for (int i = 0; i < WIDTH; i++) {
            switch (side) {
                case LEFT -> edge.add(new Position(0, i));
                case RIGHT -> edge.add(new Position(WIDTH-1, i));
                case TOP -> edge.add(new Position(i, 0));
                case BOTTOM -> edge.add(new Position(i, WIDTH-1));
            }
        }

        return new ArrayList<>(edge.stream().map(p -> new Position(p.x + position.x*WIDTH, p.y + position.y*WIDTH)).toList());
    }

    private static boolean isSame(Direction from, Direction to) {
        return switch (from) {
            case UP -> switch (to) {
                case UP, RIGHT -> true;
                case DOWN, LEFT -> false;
            };
            case DOWN -> switch (to) {
                case UP, RIGHT -> false;
                case DOWN, LEFT -> true;
            };
            case LEFT -> switch (to) {
                case LEFT, DOWN -> true;
                case RIGHT, UP -> false;
            };
            case RIGHT -> switch (to) {
                case LEFT, DOWN -> false;
                case RIGHT, UP -> true;
            };
        };

    }

    private static MyPosition moveThrowMap(List<List<Element>> map, Map<Edge, Edge> edges, String moves) {
        MyPosition currentPosition = new MyPosition(getStartX(map), 0, Direction.RIGHT);

        while (true) {
            if (moves.isEmpty()) {
                break;
            }

            //steps
            StringBuilder stepCount = new StringBuilder();
            for (int i = 0; i < moves.length(); i++) {
                if (!Character.isDigit(moves.charAt(i))) {
                    moves = moves.substring(i);
                    break;
                }
                stepCount.append(moves.charAt(i));
            }

            currentPosition = doSteps(map, edges, currentPosition, Integer.parseInt(String.valueOf(stepCount)));

            if (onlyDigits(moves)) {
                break;
            }

            //direction
            char turn = moves.charAt(0);
            moves = moves.substring(1);
            switch (currentPosition.direction) {
                case RIGHT -> currentPosition.direction = turn == 'L' ? Direction.UP : Direction.DOWN;
                case LEFT -> currentPosition.direction = turn == 'L' ? Direction.DOWN : Direction.UP;
                case DOWN -> currentPosition.direction = turn == 'L' ? Direction.RIGHT : Direction.LEFT;
                case UP -> currentPosition.direction = turn == 'L' ? Direction.LEFT : Direction.RIGHT;
            }
        }

        return currentPosition;
    }

    private static int getStartX(List<List<Element>> map) {
        for (int i = 0; i < map.getFirst().size(); i++) {
            if (map.getFirst().get(i) == Element.EMPTY) {
                return i;
            }
        }

        return -1;
    }

    private static MyPosition doSteps(List<List<Element>> map, Map<Edge, Edge> edges, MyPosition myPosition, int steps) {
        MyPosition newPosition = new MyPosition(myPosition);

        for (int i = 1; i <= steps; i++) {
            //new position
            switch (myPosition.direction) {
                case RIGHT -> newPosition.x += 1;
                case LEFT -> newPosition.x -= 1;
                case DOWN -> newPosition.y += 1;
                case UP -> newPosition.y -= 1;
            }

            Element el = getElementOrNone(map, newPosition.x, newPosition.y);

            if (el == Element.NONE) {
                Edge from = new Edge(new Position(myPosition.x, myPosition.y), myPosition.direction);
                Edge to = edges.get(from);

                newPosition.x = to.position.x;
                newPosition.y = to.position.y;
                newPosition.direction = to.direction;
            }

            //evaluate new position
            el = map.get(newPosition.y).get(newPosition.x);
            if (el == Element.WALL) {
                return myPosition;
            } else if (el == Element.EMPTY) {
                myPosition = new MyPosition(newPosition);
                //printMap(map, myPosition);
            }
        }

        return myPosition;
    }

    private static boolean onlyDigits(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static Element getElementOrNone(List<List<Element>> map, int x, int y) {
        if (y < 0 || y >= map.size()) {
            return Element.NONE;
        }
        if (x < 0 || x >= map.get(y).size()) {
            return Element.NONE;
        }

        return map.get(y).get(x);
    }
}
