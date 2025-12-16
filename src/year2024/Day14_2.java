package year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14_2 {
    public record Position(int x, int y) {}
    private record Robot(int x, int y, int vx, int vy) {}

    public String part2(List<String> input) {
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

        for (int i = 1; ; i++) {
            List<Position> positionOfRobots = move(robots, i);

            for (int y = 0; y < 103; y++) {
                int finalY = y;
                List<Integer> line = new ArrayList<>(positionOfRobots.stream()
                        .filter(pos -> pos.y() == finalY)
                        .map(Position::x)
                        .toList());

                line.sort(Integer::compare);

                if (maxSequence(line) >= 10) {
                    String result = String.valueOf(i);
                    return result + "\n" + print(positionOfRobots);
                }
            }
        }
    }

    private List<Position> move(List<Robot> robots, int seconds) {
       return robots.stream().map(robot -> {
            int newX = (robot.x + robot.vx * seconds) % 101;
            int newY = (robot.y + robot.vy * seconds) % 103;

            newX = newX < 0 ? 101 + newX : newX;
            newY = newY < 0 ? 103 + newY : newY;
            return new Position(newX, newY);
        }).toList();
    }

    private int maxSequence(List<Integer> line) {
        int maxSequence = 0;
        int sequence = 0;

        for (int i = 0; i < line.size()-1; i++) {
            if (line.get(i+1) - line.get(i) == 1) {
                sequence++;
            } else {
                maxSequence = Math.max(sequence, maxSequence);
                sequence = 0;
            }
        }

        return maxSequence;
    }

    private String print(List<Position> positionOfRobots) {
        char[][] treeMap = new char[103][101];
        for (Position positionOfRobot : positionOfRobots) {
            treeMap[positionOfRobot.y()][positionOfRobot.x()] = '#';
        }

        List<String> treeMap2 = Arrays.stream(treeMap).map(String::new).toList();
        int startTreeY = -1;
        int endTreeY = -1;
        int startTreeX = -1;
        int endTreeX = -1;

        boolean searchEnd = false;
        for (int i = 0; i < treeMap2.size(); i++) {
            String line = treeMap2.get(i);

            int indexOfSequence = line.indexOf("###############################");
            if (indexOfSequence != -1) {
                if (searchEnd) {
                    endTreeY = i;
                } else {
                    startTreeY = i;
                    startTreeX = indexOfSequence;
                    endTreeX = indexOfSequence + 31;
                    searchEnd = true;
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int y = startTreeY; y <= endTreeY; y++) {
            for (int x = startTreeX; x <= endTreeX; x++) {
                stringBuilder.append(positionOfRobots.contains(new Position(x, y)) ? "#" : " ");
            }

            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
