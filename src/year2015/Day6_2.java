package year2015;

import java.util.List;

public class Day6_2 {
    record Point(int x, int y) {
    }

    enum Operation {
        ON, OFF, TOGGLE
    }

    public String part2(List<String> lines) {
        int[][] lights = new int[1000][1000];

        for (String line : lines) {
            if (line.startsWith("turn on")) {
                line = line.replace("turn on ", "");
                lights = switchLights(line, Operation.ON, lights);

            } else if (line.startsWith("turn off")) {
                line = line.replace("turn off ", "");
                lights = switchLights(line, Operation.OFF, lights);

            } else if (line.startsWith("toggle")) {
                line = line.replace("toggle ", "");
                lights = switchLights(line, Operation.TOGGLE, lights);

            } else {
                System.out.println("Warning: Unknown operation");
            }
        }

        return String.valueOf(getCount(lights));
    }

    private int[][] switchLights(String line, Operation operation, int[][] lights) {
        String[] points = line.split(" through ");

        Point from = fromString(points[0]);
        Point to = fromString(points[1]);

        int[][] newLights = new int[1000][1000];
        switch (operation) {
            case ON -> newLights = turnOn(lights, from, to);
            case OFF -> newLights = turnOff(lights, from, to);
            case TOGGLE -> newLights = toggle(lights, from, to);
        }

        return newLights;
    }

    private Point fromString(String s) {
        String[] coordinates = s.split(",");

        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);

        return new Point(x, y);
    }

    private int[][] turnOn(int[][] lights, Point from, Point to) {
        for (int y = from.y; y <= to.y; y++) {
            for (int x = from.x; x <= to.x; x++) {
                lights[y][x] = lights[y][x] + 1;
            }
        }

        return lights;
    }

    private int[][] turnOff(int[][] lights, Point from, Point to) {
        for (int y = from.y; y <= to.y; y++) {
            for (int x = from.x; x <= to.x; x++) {
                lights[y][x] = Math.max(0, lights[y][x] - 1);
            }
        }

        return lights;
    }

    private int[][] toggle(int[][] lights, Point from, Point to) {
        for (int y = from.y; y <= to.y; y++) {
            for (int x = from.x; x <= to.x; x++) {
                lights[y][x] = lights[y][x] + 2;
            }
        }

        return lights;
    }

    private int getCount(int[][] lights) {
        int count = 0;
        for (int y = 0; y <= 999; y++) {
            for (int x = 0; x <= 999; x++) {
                count += lights[y][x];
            }
        }

        return count;
    }
}
