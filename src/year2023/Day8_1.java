package year2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day8_1 {
    record MapInformation(String left, String right) {
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        String[] way = lines.remove(0).split("");
        Map<String, MapInformation> maps = new HashMap<>();

        String currentKey = "AAA";
        int steps = 0;

        lines.remove(0);

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

        System.out.println(steps);
    }
}
