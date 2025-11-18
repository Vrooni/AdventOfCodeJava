package year2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day15_1 {
    record Position(int x, int y) { }

    public String part1(List<String> lines) {
        Set<Position> cave = new HashSet<>();
        Set<Position> beacons = new HashSet<>();

        for (String line : lines) {
            line = line.replaceAll("Sensor at ", "");
            line = line.replaceAll("closest beacon is at ", "");

            String[] positions = line.split(": ");
            Position sensorPosition = getPosition(positions[0]);
            Position beaconPosition = getPosition((positions[1]));
            if (beaconPosition.y == 2000000) {
                beacons.add(beaconPosition);
            }

            int radius = Math.abs(sensorPosition.x - beaconPosition.x) + Math.abs(sensorPosition.y - beaconPosition.y);

            for (int y = -radius; y <= radius; y++) {
                if (sensorPosition.y + y == 2000000) {
                    int width = radius - Math.abs(y);

                    for (int x = -width; x <= width; x++) {
                        cave.add(new Position(sensorPosition.x+x, sensorPosition.y+y));
                    }
                }
            }
        }

        return String.valueOf(cave.size() - beacons.size());
    }

    private Position getPosition(String s) {
        s = s.replaceAll("x=", "");

        String[] position = s.split(", y=");
        int x = Integer.parseInt(position[0]);
        int y = Integer.parseInt(position[1]);

        return new Position(x, y);
    }
}