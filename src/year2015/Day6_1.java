import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day6_1 {
    record Point(int x, int y) {
    }

    enum Operation {
        ON, OFF, TOGGLE
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        boolean[][] lights = new boolean[1000][1000];

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
                System.out.println("Warnung: Keine Operation erkannt");
            }
        }

        System.out.println(getCount(lights));
    }

    private static boolean[][] switchLights(String line, Operation operation, boolean[][] lights) {
        String[] points = line.split(" through ");

        Point from = fromString(points[0]);
        Point to = fromString(points[1]);

        boolean[][] newLights = new boolean[1000][1000];
        switch (operation) {
            case ON -> newLights = turnOn(lights, from, to);
            case OFF -> newLights = turnOff(lights, from, to);
            case TOGGLE -> newLights = toggle(lights, from, to);
        }

        return newLights;
    }

    private static Point fromString(String s) {
        String[] coordinates = s.split(",");

        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);

        return new Point(x, y);
    }

    private static boolean[][] turnOn(boolean[][] lights, Point from, Point to) {
        for (int y = from.y; y <= to.y; y++) {
            for (int x = from.x; x <= to.x; x++) {
                lights[y][x] = true;
            }
        }

        return lights;
    }

    private static boolean[][] turnOff(boolean[][] lights, Point from, Point to) {
        for (int y = from.y; y <= to.y; y++) {
            for (int x = from.x; x <= to.x; x++) {
                lights[y][x] = false;
            }
        }

        return lights;
    }

    private static boolean[][] toggle(boolean[][] lights, Point from, Point to) {
        for (int y = from.y; y <= to.y; y++) {
            for (int x = from.x; x <= to.x; x++) {
                lights[y][x] = !lights[y][x];
            }
        }

        return lights;
    }

    private static int getCount(boolean[][] lights) {
        int count = 0;
        for (int y = 0; y <= 999; y++) {
            for (int x = 0; x <= 999; x++) {
                if (lights[y][x]) {
                    count++;
                }
            }
        }

        return count;
    }
}
