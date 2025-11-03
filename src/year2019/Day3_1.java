package year2019;

import year2019.utils.Position;

import java.util.*;

public class Day3_1 {
    public String part1(List<String> input) {
        List<Position> wire1 = readInput(input.get(0));
        List<Position> wire2 = readInput(input.get(1));

        List<Position> sortWire1 = new ArrayList<>(wire1);
        List<Position> sortWire2 = new ArrayList<>(wire2);
        sortWire1.sort(Comparator.comparingInt(o -> Math.abs(o.x()) + Math.abs(o.y())));
        sortWire2.sort(Comparator.comparingInt(o -> Math.abs(o.x()) + Math.abs(o.y())));

        for (Position position : sortWire1) {
            if (sortWire2.contains(position)) {
                return String.valueOf(Math.abs(position.x()) + Math.abs(position.y()));
            }
        }

        return "-1";
    }

    private List<Position> readInput(String input) {
        List<Position> wire = new ArrayList<>();
        int x = 0;
        int y = 0;

        for (String move : input.split(",")) {
            int steps = Integer.parseInt(move.substring(1));

            switch (move.charAt(0)) {
                case 'U' -> {
                    addPositions(wire, x, x, y - 1, y - steps, false, true);
                    y -= steps;
                }
                case 'D' -> {
                    addPositions(wire, x, x, y + 1, y + steps, false, false);
                    y += steps;
                }
                case 'L' -> {
                    addPositions(wire, x - 1, x - steps, y, y, true, false);
                    x -= steps;
                }
                case 'R' -> {
                    addPositions(wire, x + 1, x + steps, y, y, false, false);
                    x += steps;
                }
            }
        }

        return wire;
    }

    private void addPositions(List<Position> wire, int fromX, int toX, int fromY, int toY, boolean negativeX, boolean negativeY) {
        if (negativeY) {
            for (int y = fromY; y >= toY; y--) {
                addPosition(wire, fromX, toX, negativeX, y);
            }
        } else {
            for (int y = fromY; y <= toY; y++) {
                addPosition(wire, fromX, toX, negativeX, y);
            }
        }
    }

    private void addPosition(List<Position> wire, int fromX, int toX, boolean negativeX, int y) {
        if (negativeX) {
            for (int x = fromX; x >= toX; x--) {
                wire.add(new Position(x, y));
            }
        } else {
            for (int x = fromX; x <= toX; x++) {
                wire.add(new Position(x, y));
            }
        }
    }
}
