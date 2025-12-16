package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14_1 {
    public record Position(int x, int y) {}
    private record Robot(int x, int y, int vx, int vy) {}

    public String part1(List<String> input) {
        List<Robot> robots = new ArrayList<>();

        for (String line : input) {
            Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
            Matcher matcher = pattern.matcher(line);
            matcher.find();

            robots.add(new Robot(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4))
            ));
        }

        List<Position> positionOfRobots = move(robots);
        int width = 101;
        int height = 103;

        List<Position> firstQuadrant = positionOfRobots.stream()
                .filter(position -> position.x() < width/2 && position.y() < height/2)
                .toList();

        List<Position> secondQuadrant = positionOfRobots.stream()
                .filter(position -> position.x() > width/2 && position.y() < height/2)
                .toList();


        List<Position> thirdQuadrant = positionOfRobots.stream()
                .filter(position -> position.x() < width/2 && position.y() > height/2)
                .toList();

        List<Position> fourthQuadrant = positionOfRobots.stream()
                .filter(position -> position.x() > width/2 && position.y() > height/2)
                .toList();

        return String.valueOf(firstQuadrant.size() * secondQuadrant.size() * thirdQuadrant.size() * fourthQuadrant.size());
    }

    private List<Position> move(List<Robot> robots) {
       return robots.stream().map(robot -> {
            int newX = (robot.x + robot.vx * 100) % 101;
            int newY = (robot.y + robot.vy * 100) % 103;

            newX = newX < 0 ? 101 + newX : newX;
            newY = newY < 0 ? 103 + newY : newY;
            return new Position(newX, newY);
        }).toList();
    }
}
