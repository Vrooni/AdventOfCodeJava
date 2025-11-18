package year2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day15_2 {
    record Position(int x, int y) { }

    record Range(Position pos, int radius) {}

    public String part2(List<String> lines) {
        Set<Range> sensors = new HashSet<>();

        for (String line : lines) {
            line = line.replaceAll("Sensor at ", "");
            line = line.replaceAll("closest beacon is at ", "");

            String[] positions = line.split(": ");
            Position sensorPosition = getPosition(positions[0]);
            Position beaconPosition = getPosition((positions[1]));

            int radius = Math.abs(sensorPosition.x - beaconPosition.x) + Math.abs(sensorPosition.y - beaconPosition.y);

            sensors.add(new Range(sensorPosition, radius));
        }

        for (int y = 0; y <= 4000000; y++) {
            for (int x = 0; x <= 4000000; x++) {
                boolean isInRange = false;

                for (Range sensor : sensors) {
                    int dist = Math.abs(x - sensor.pos.x) + Math.abs(y - sensor.pos.y);

                    if (dist <= sensor.radius) {
                        int distX = x - sensor.pos.x;
                        int distY = y - sensor.pos.y;
                        x += sensor.radius - distX - Math.abs(distY);

                        isInRange = true;
                        break;
                    }
                }

                if (!isInRange) {
                    return String.valueOf(x*4000000L + y);
                }
            }
        }

        return "-1";
    }

    private Position getPosition(String s) {
        s = s.replaceAll("x=", "");

        String[] position = s.split(", y=");
        int x = Integer.parseInt(position[0]);
        int y = Integer.parseInt(position[1]);

        return new Position(x, y);
    }
}