package year2018;

import java.util.*;

public class Day6_2 {
    public record Position(int x, int y) {}

    public String part2(List<String> input) {
        Map<Position, Integer> coordinates = getCoordinates(input);

        Position from = new Position(
                coordinates.keySet().stream().mapToInt(Position::x).min().getAsInt(),
                coordinates.keySet().stream().mapToInt(Position::y).min().getAsInt()
        );

        Position to = new Position(
                coordinates.keySet().stream().mapToInt(Position::x).max().getAsInt(),
                coordinates.keySet().stream().mapToInt(Position::y).max().getAsInt()
        );

        return String.valueOf(getSaveCoordinates(from, to, coordinates));
    }

    private int getSaveCoordinates(Position from, Position to, Map<Position, Integer> coordinates) {
        int saveCoordinates = 0;
        for (int i = from.y(); i <= to.y(); i++) {
            for (int j = from.x(); j <= to.x(); j++) {
                int distance = 0;

                for (Position position : coordinates.keySet()) {
                    distance += Math.abs(position.x() - j) + Math.abs(position.y() - i);
                    if (distance >= 10000) {
                        break;
                    }
                }

                if (distance < 10000) {
                    saveCoordinates++;
                }
            }
        }

        return saveCoordinates;
    }

    private Map<Position, Integer> getCoordinates(List<String> input) {
        Map<Position, Integer> coordinates = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            String coordinate = input.get(i);
            String[] splitCoordinate = coordinate.split(", ");

            coordinates.put(new Position(
                    Integer.parseInt(splitCoordinate[0]),
                    Integer.parseInt(splitCoordinate[1])), i+1);
        }

        return coordinates;
    }
}
