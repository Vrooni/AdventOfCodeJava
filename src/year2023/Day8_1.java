package year2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8_1 {
    record MapInformation(String left, String right) {
    }

    public String part1(List<String> lines) {
        lines = new ArrayList<>(lines);
        String[] way = lines.removeFirst().split("");
        Map<String, MapInformation> maps = new HashMap<>();

        String currentKey = "AAA";
        int steps = 0;

        lines.removeFirst();

        for (String line : lines) {
            String[] splitLine = line.split(" = ");
            String key = splitLine[0];

            splitLine = splitLine[1]
                    .replaceAll("\\(", "")
                    .replaceAll("\\)", "")
                    .split(", ");

            maps.put(key, new MapInformation(splitLine[0], splitLine[1]));

        }

        outer: while (true) {

            for (String dir : way) {
                switch (dir) {
                    case "L" -> currentKey = maps.get(currentKey).left;
                    case "R" -> currentKey = maps.get(currentKey).right;
                }

                steps++;
                if (currentKey.equals("ZZZ")) {
                    break outer;
                }
            }
        }

        return String.valueOf(steps);
    }
}
