package year2022;

import java.util.*;

public class Day12_1 {
    record Point(int x, int y) { }
    record Candidate(List<Point> way, Point point) { }

    public String part1(List<String> lines) {
        lines = new ArrayList<>(lines);
        Map<Point, List<Point>> directionsOfPoints = new HashMap<>();

        Point start = null;
        Point end = null;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.getFirst().length(); x++) {
                char letter = lines.get(y).charAt(x);

                if (letter == 'S') {
                    lines.set(y, lines.get(y).replace('S', 'a'));
                    start = new Point(x, y);
                } else if (letter == 'E') {
                    lines.set(y, lines.get(y).replace('E', 'z'));
                    end = new Point(x, y);
                }
            }
        }

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.getFirst().length(); x++) {
                char letter = lines.get(y).charAt(x);

                List<Point> directions = directionsOfPoints.computeIfAbsent(new Point(x, y),
                _ -> new ArrayList<>());


                //look left
                if (x != 0 && lines.get(y).charAt(x-1) - letter <= 1) {
                    directions.add(new Point(x-1, y));
                }

                //look right
                if (x != lines.getFirst().length()-1 && lines.get(y).charAt(x+1) - letter <= 1) {
                    directions.add(new Point(x+1, y));
                }

                //look up
                if (y != 0 && lines.get(y-1).charAt(x) - letter <= 1) {
                    directions.add(new Point(x, y-1));
                }

                //look down
                if (y != lines.size()-1 && lines.get(y+1).charAt(x) - letter <= 1) {
                    directions.add(new Point(x, y+1));
                }
            }
        }

        ArrayDeque<Candidate> candidates = new ArrayDeque<>();
        candidates.addLast(new Candidate(new ArrayList<>(), start));
        Set<Point> seen = new HashSet<>();
        seen.add(start);

        while (true) {
            Candidate candidate = candidates.removeFirst();

            if (candidate.point.equals(end)) {
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
}
