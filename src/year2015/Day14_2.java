package year2015;

import java.util.ArrayList;
import java.util.List;

public class Day14_2 {
    private record Reindeer(int speed, int duration, int pause) { }

    public String part2(List<String> lines) {
        List<Reindeer> reindeer = parseInput(lines);
        int maxPoints = getMaxPoints(reindeer);
        return String.valueOf(maxPoints);
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

    private int getMaxPoints(List<Reindeer> reindeer) {
        List<Integer> allPoints = new ArrayList<>();
        List<Integer> distances = new ArrayList<>();

        for (int i = 0; i < reindeer.size(); i++) {
            allPoints.add(0);
            distances.add(0);
        }

        int totalDuration = 2503;
        for (int i = 0; i < totalDuration; i++) {
            int maxDistanceThisRound = Integer.MIN_VALUE;

            for (int j = 0; j < reindeer.size(); j++) {
                int distance = distances.get(j);

                if (i % (reindeer.get(j).duration + reindeer.get(j).pause) < reindeer.get(j).duration) {
                    distance += reindeer.get(j).speed;
                }

                maxDistanceThisRound = Math.max(distance, maxDistanceThisRound);
                distances.set(j, distance);
            }

            for (int j = 0; j < reindeer.size(); j++) {
                if (distances.get(j) == maxDistanceThisRound) {
                    allPoints.set(j, allPoints.get(j)+1);
                }
            }
        }

        int maxPoints = Integer.MIN_VALUE;

        for (int points : allPoints) {
            maxPoints = Math.max(points, maxPoints);
        }

        return maxPoints;
    }
}
