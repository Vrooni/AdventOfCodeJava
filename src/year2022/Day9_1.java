package year2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9_1 {

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

    public String part1(List<String> lines) {
        Set<Point> visitedPoints = new HashSet<>();
        Point tPosition = new Point(0, 0);
        Point hPosition = new Point(0, 0);

        visitedPoints.add(hPosition);
        for (String line: lines) {
            String[] splittedLine = line.split(" ");
            int steps =  Integer.parseInt(splittedLine[1]);

            for (int i = 0; i < steps; i++) {
                switch (splittedLine[0]) {
                    case "R" -> hPosition = new Point(hPosition.x + 1, hPosition.y);
                    case "L" -> hPosition = new Point(hPosition.x - 1, hPosition.y);
                    case "U" -> hPosition = new Point(hPosition.x, hPosition.y - 1);
                    case "D" -> hPosition = new Point(hPosition.x, hPosition.y + 1);
                }

                tPosition = tPosition.updateT(hPosition);
                visitedPoints.add(tPosition);
            }
        }

        return String.valueOf(visitedPoints.size());
    }
}
