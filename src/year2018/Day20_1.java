package year2018;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day20_1 {
    public record Position(int x, int y) {}

    private static class Room {
        int x;
        int y;
        int distance;

        public Room(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }

    public String part1(List<String> lines) {
        String input = lines.getFirst().replace("^", "").replace("$", "");
        Map<Position, Room> map = getMap(input);

        return String.valueOf(map.values().stream().mapToInt(room -> room.distance).max().getAsInt());
    }

    private Map<Position, Room> getMap(String path) {
        Room current = new Room(0, 0, 0);
        LinkedList<Room> stack = new LinkedList<>();

        Map<Position, Room> map = new HashMap<>();
        map.put(new Position(0, 0), current);

        for (char c : path.toCharArray()) {
            switch (c) {
                case 'N' -> {
                    int x = current.x;
                    int y = current.y - 1;
                    int distance = current.distance;

                    current = map.computeIfAbsent(new Position(x, y), _ -> new Room(x, y, Integer.MAX_VALUE));
                    current.distance = Math.min(distance + 1, current.distance);
                }
                case 'S' -> {
                    int x = current.x;
                    int y = current.y + 1;
                    int distance = current.distance;

                    current = map.computeIfAbsent(new Position(x, y), _ -> new Room(x, y, Integer.MAX_VALUE));
                    current.distance = Math.min(distance + 1, current.distance);
                }
                case 'W' -> {
                    int x = current.x - 1;
                    int y = current.y;
                    int distance = current.distance;

                    current = map.computeIfAbsent(new Position(x, y), _ -> new Room(x, y, Integer.MAX_VALUE));
                    current.distance = Math.min(distance + 1, current.distance);
                }
                case 'E' -> {
                    int x = current.x + 1;
                    int y = current.y;
                    int distance = current.distance;

                    current = map.computeIfAbsent(new Position(x, y), _ -> new Room(x, y, Integer.MAX_VALUE));
                    current.distance = Math.min(distance + 1, current.distance);
                }
                case '(' -> stack.addFirst(current);
                case ')' -> current = stack.removeFirst();
                case '|' -> current = stack.peek();
            }
        }

        return map;
    }
}
