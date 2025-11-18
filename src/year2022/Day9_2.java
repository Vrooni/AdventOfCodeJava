package year2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Day9_2 {

    record Point(int x, int y) {

        public Point updateT(Point h) {
            if (Math.abs(h.x - this.x) <= 1 && Math.abs(h.y - this.y) <= 1) {
                return this;
            }

            if (h.x == this.x) {
                if (h.y > this.y) {
                    return new Point(this.x, this.y+1);
                } else {
                    return new Point(this.x, this.y-1);
                }
            }

            if (h.y == this.y) {
                if (h.x > this.x) {
                    return new Point(this.x+1, this.y);
                } else {
                    return new Point(this.x-1, this.y);
                }
            }

            if (h.x > this.x) {
                if (h.y > this.y) {
                    return new Point(this.x+1, this.y+1);
                } else {
                    return new Point(this.x+1, this.y-1);
                }
            } else {
                if (h.y > this.y) {
                    return new Point(this.x-1, this.y+1);
                } else {
                    return new Point(this.x-1, this.y-1);
                }
            }
        }
    }

    public String part2(List<String> lines) {
        //Part one
        Set<Point> visitedPoints = new HashSet<>();
        List<Point> knots = new ArrayList<>(IntStream.range(0, 10).mapToObj(_ -> new Point(0, 0)).toList());
        visitedPoints.add(knots.get(9));

        for (String line: lines) {
            String[] splittedLine = line.split(" ");
            int steps =  Integer.parseInt(splittedLine[1]);

            for (int i = 0; i < steps; i++) {
                switch (splittedLine[0]) {
                    case "R" -> knots.set(0, new Point(knots.getFirst().x + 1, knots.getFirst().y));
                    case "L" -> knots.set(0, new Point(knots.getFirst().x - 1, knots.getFirst().y));
                    case "U" -> knots.set(0, new Point(knots.getFirst().x, knots.getFirst().y - 1));
                    case "D" -> knots.set(0, new Point(knots.getFirst().x, knots.getFirst().y + 1));
                }

                for (int j = 1; j < knots.size(); j++) {
                    knots.set(j, knots.get(j).updateT(knots.get(j-1)));

                    if (j == 9) {
                        visitedPoints.add(knots.get(j));
                    }
                }
            }
        }

       return String.valueOf(visitedPoints.size());
    }
}
