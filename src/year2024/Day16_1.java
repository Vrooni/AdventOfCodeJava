package year2024;

import java.util.*;

public class Day16_1 {
    public record Position(int x, int y) {}
    public enum Direction {
        UP, RIGHT, DOWN, LEFT;

        private static final Direction[] values = values();

        public Direction next() {
            return values[(this.ordinal() + 1) % values.length];
        }

        public Direction prev() {
            return values[(this.ordinal() - 1  + values.length) % values.length];
        }
    }

    private record Node(int x, int y, int score, Direction direction) implements Comparable<Node> {
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Node other = (Node) obj;
            return x == other.x && y == other.y && direction == other.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, direction);
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.score, o.score);
        }
    }

    public String part1(List<String> input) {
        char[][] map = input.stream().map(String::toCharArray).toArray(char[][]::new);

        Position start = getStart(map);
        int minScore = getMinScore(start, map);

        return String.valueOf(minScore);
    }

    private int getMinScore(Position start, char[][] map) {
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new PriorityQueue<>();
        Node startNode = new Node(start.x(), start.y(), 0, Direction.RIGHT);
        visited.add(startNode);
        queue.add(startNode);

        int minScore = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (map[current.y][current.x] == 'E' && current.score <= minScore) {
                minScore = current.score;
            }

            List<Node> neighbours = getNeighbours(current, map);
            for (Node neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    queue.add(neighbour);
                    visited.add(neighbour);
                }
            }
        }

        return minScore;
    }

    private Position getStart(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 'S') {
                    return new Position(x, y);
                }
            }
        }

        return new Position(0, 0);
    }

    private List<Node> getNeighbours(Node current, char[][] map) {
        List<Node> neighbours = new ArrayList<>();

        Node turned = new Node(current.x, current.y, current.score+1000, current.direction.next());
        if (canMove(map, nextPosition(turned))) {
            neighbours.add(turned);
        }

        turned = new Node(current.x, current.y, current.score+1000, current.direction.prev());
        if (canMove(map, nextPosition(turned))) {
            neighbours.add(turned);
        }

        Position pos = nextPosition(current);
        if (canMove(map, pos)) {
            neighbours.add(new Node(pos.x(), pos.y(), current.score+1, current.direction));
        }

        return neighbours;
    }

    private Position nextPosition(Node current) {
        return switch (current.direction) {
            case LEFT -> new Position(current.x-1, current.y);
            case RIGHT -> new Position(current.x+1, current.y);
            case UP -> new Position(current.x, current.y-1);
            case DOWN -> new Position(current.x, current.y+1);
        };
    }

    private boolean canMove(char[][] map, Position pos) {
        return map[pos.y()][pos.x()] == '.' || map[pos.y()][pos.x()] == 'E' || map[pos.y()][pos.x()] == 'S';
    }
}
