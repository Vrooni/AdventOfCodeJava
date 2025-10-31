package year2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10_1 {
    public record Position(int x, int y) {}
    private record Light(Position position, Position velocity) {}

    public String part1(List<String> input) {
        List<Light> lights = input.stream()
                .map(line -> {
                    String[] light = line
                            .replace("position=", "")
                            .split(" velocity=");
                    return new Light(getPosition(light[0]), getPosition(light[1]));
                }).toList();

        while (true) {
            List<Light> newLights = new ArrayList<>();

            for (Light light : lights) {
                newLights.add(new Light(new Position(
                        light.position.x() + light.velocity.x(),
                        light.position.y() + light.velocity.y()),
                        light.velocity));
            }

            if (getSize(newLights) > getSize(lights)) {
                return print(lights);
            }

            lights = newLights;
        }
    }

    private Position getPosition(String s) {
        String[] position = s
                .replaceAll("\\s+","")
                .replace("<", "")
                .replace(">", "")
                .split(",");

        return new Position(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
    }

    private long getSize(List<Light> lights) {
        int minX = lights.stream().mapToInt(light -> light.position.x()).min().getAsInt();
        int maxX = lights.stream().mapToInt(light -> light.position.x()).max().getAsInt();
        int minY = lights.stream().mapToInt(light -> light.position.y()).min().getAsInt();
        int maxY = lights.stream().mapToInt(light -> light.position.y()).max().getAsInt();

        return (long) Math.abs(maxX - minX) * Math.abs(maxY - minY);
    }

    private String print(List<Light> lights) {
        StringBuilder result = new StringBuilder();
        Map<Position, Boolean> sky = new HashMap<>();

        for (Light light : lights) {
            sky.put(light.position, true);
        }

        int minX = lights.stream().mapToInt(light -> light.position.x()).min().getAsInt();
        int maxX = lights.stream().mapToInt(light -> light.position.x()).max().getAsInt();
        int minY = lights.stream().mapToInt(light -> light.position.y()).min().getAsInt();
        int maxY = lights.stream().mapToInt(light -> light.position.y()).max().getAsInt();

        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                Position position = new Position(j, i);
                result.append(sky.getOrDefault(position, false) ? "#" : " ");
            }

            result.append("\n");
        }

        return result.toString();
    }
}
