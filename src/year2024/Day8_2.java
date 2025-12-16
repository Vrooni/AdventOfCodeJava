package year2024;

import java.util.*;

public class Day8_2 {
    public record Position(int x, int y) {}

    public String part2(List<String> input) {
        Map<Character, List<Position>> antennas = new HashMap<>();

        int height = input.size();
        int width = input.getFirst().length();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char frequency = input.get(i).charAt(j);
                if (frequency != '.') {
                    antennas.computeIfAbsent(frequency, _ -> new ArrayList<>()).add(new Position(j, i));
                }
            }
        }

        Set<Position> antinodes = new HashSet<>();
        for (Map.Entry<Character, List<Position>> entry : antennas.entrySet()) {
            List<Position> positions = entry.getValue();

            for (int i = 0; i < positions.size(); i++) {
                for (int j = i+1; j < positions.size(); j++) {
                    Position pos1 = positions.get(i);
                    Position pos2 = positions.get(j);

                    int xDist = pos2.x() - pos1.x();
                    int yDist = pos2.y() - pos1.y();

                    int antinodeX = pos1.x();
                    int antinodeY = pos1.y();
                    while (addPosition(antinodeX, antinodeY, width, height, antinodes)) {
                        antinodeX -= xDist;
                        antinodeY -= yDist;
                    }

                    antinodeX = pos2.x();
                    antinodeY = pos2.y();
                    while (addPosition(antinodeX, antinodeY, width, height, antinodes)) {
                        antinodeX += xDist;
                        antinodeY += yDist;
                    }
                }
            }
        }

        return String.valueOf(antinodes.size());
    }

    private boolean addPosition(int x, int y, int width, int height, Set<Position> antinodes) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            antinodes.add(new Position(x, y));
            return true;
        }

        return false;
    }
}
