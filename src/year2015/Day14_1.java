package year2015;

import java.util.ArrayList;
import java.util.List;

public class Day14_1 {
    private record Reindeer(int speed, int duration, int pause) { }

    public String part1(List<String> lines) {
        List<Reindeer> reindeer = parseInput(lines);
        int longestDistance = getLongestDistance(reindeer);

        return String.valueOf(longestDistance);
    }

    private List<Reindeer> parseInput(List<String> input) {
        List<Reindeer> reindeer = new ArrayList<>();

        for (String line : input) {
            String[] splittedLine = line.split(" ");

            int speed = Integer.parseInt(splittedLine[3]);
            int duration = Integer.parseInt(splittedLine[6]);
            int pause = Integer.parseInt(splittedLine[13]);

            reindeer.add(new Reindeer(speed, duration, pause));
        }

        return reindeer;
    }

    private int getLongestDistance(List<Reindeer> reindeer) {
        int maxDistance = Integer.MIN_VALUE;

        for (Reindeer deer : reindeer) {
            int duration = deer.duration + deer.pause;
            int totalDuration = 2503;
            int iterations = totalDuration / duration;

            int distance = iterations * deer.speed * deer.duration;
            int remainingTime = totalDuration - iterations * duration;

            int flyingTime = Math.min(remainingTime, deer.duration);
            distance += flyingTime * deer.speed;

            maxDistance = Math.max(distance, maxDistance);
        }

        return maxDistance;
    }
}
