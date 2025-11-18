package year2022;

import java.util.*;

public class Day12_2 {
    record Point(int x, int y) { }
    record Candidate(List<Point> way, Point point) { }

    public String part2(List<String> lines) {
        lines = new ArrayList<>(lines);

        Point end = null;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.getFirst().length(); x++) {
                char letter = lines.get(y).charAt(x);

                if (letter == 'S') {
                    lines.set(y, lines.get(y).replace('S', 'a'));
                } else if (letter == 'E') {
                    lines.set(y, lines.get(y).replace('E', 'z'));
                    end = new Point(x, y);
                }
            }
        }

        Map<Point, List<Point>> directionsOfPoints = readInput(lines);

        ArrayDeque<Candidate> candidates = new ArrayDeque<>();
        candidates.addLast(new Candidate(new ArrayList<>(), end));
        Set<Point> seen = new HashSet<>();
        seen.add(end);

        while (true) {
            Candidate candidate = candidates.removeFirst();

            if (lines.get(candidate.point.y).charAt(candidate.point.x) == 'a') {
                return String.valueOf(candidate.way.size());
            }

            for (Point p : directionsOfPoints.get(candidate.point)) {
                if (seen.contains(p)) {
                    continue;
                }

                List<Point> currentWay = new ArrayList<>(candidate.way);
                currentWay.add(p);
                candidates.add(new Candidate(currentWay, p));
                seen.add(p);
            }
        }
    }

    private static Map<Point, List<Point>> readInput(List<String> lines) {
        Map<Point, List<Point>> directionsOfPoints = new HashMap<>();

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.getFirst().length(); x++) {
                char letter = lines.get(y).charAt(x);

                List<Point> directions = directionsOfPoints.computeIfAbsent(new Point(x, y),
                        _ -> new ArrayList<>());


                //look left
                if (x != 0 && letter - lines.get(y).charAt(x-1) <= 1) {
                    directions.add(new Point(x-1, y));
                }

                //look right
                if (x != lines.getFirst().length()-1 && letter - lines.get(y).charAt(x+1) <= 1) {
                    directions.add(new Point(x+1, y));
                }

                //look up
                if (y != 0 && letter - lines.get(y-1).charAt(x) <= 1) {
                    directions.add(new Point(x, y-1));
                }

                //look down
                if (y != lines.size()-1 && letter - lines.get(y+1).charAt(x) <= 1) {
                    directions.add(new Point(x, y+1));
                }
            }
        }
        return directionsOfPoints;
    }
}
