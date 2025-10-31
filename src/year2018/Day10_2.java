package year2018;

import java.util.ArrayList;
import java.util.List;

public class Day10_2 {
    public record Position(int x, int y) {}
    private record Light(Position position, Position velocity) {}

    public String part2(List<String> input) {
        List<Light> lights = input.stream()
                .map(line -> {
                    String[] light = line
                            .replace("position=", "")
                            .split(" velocity=");
                    return new Light(getPosition(light[0]), getPosition(light[1]));
                }).toList();

        for (int i = 0; true; i++) {
            List<Light> newLights = new ArrayList<>();

            for (Light light : lights) {
                newLights.add(new Light(new Position(
                        light.position.x() + light.velocity.x(),
                        light.position.y() + light.velocity.y()),
                        light.velocity));
            }

            if (getSize(newLights) > getSize(lights)) {
                return String.valueOf(i);
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
}
