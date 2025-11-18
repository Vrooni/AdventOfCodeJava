package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day17_1 {
    record Point(int x, int y) { }

    public String part1(List<String> lines) {
        Set<Point> cave = new HashSet<>();
        List<Set<Point>> rocks = new ArrayList<>();
        String jet = lines.getFirst().replaceAll("\n", "");

        rocks.add(Set.of(new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)));
        rocks.add(Set.of(new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)));
        rocks.add(Set.of(new Point(2, 2), new Point(2, 1), new Point(0, 0), new Point(1, 0), new Point(2, 0)));
        rocks.add(Set.of(new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3)));
        rocks.add(Set.of(new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(1, 1)));

        int indexJet = 0;
        Point position;
        for (int i = 0; i < 2022; i++) {
            Set<Point> rock = rocks.get(i%5);
            int y = cave.stream().mapToInt(point -> point.y).max().orElse(-1);

            position = new Point(2, y + 4);
            while (true) {
                //move side
                char move = jet.charAt(indexJet);
                indexJet = (indexJet + 1)%jet.length();
                Point newPosition = new Point(0, 0);

                switch (move) {
                    case '<' -> newPosition = new Point(Math.max(position.x - 1, 0), position.y);
                    case '>' -> newPosition = new Point(Math.min(position.x + 1, 6 - rock.stream().mapToInt(p -> p.x).max().getAsInt()), position.y);
                }

                if (!collide(newPosition, rock, cave)) {
                    position = newPosition;
                }

                //move down
                newPosition = new Point(position.x, position.y - 1);
                if (collide(newPosition, rock, cave) || newPosition.y < 0) {
                    for (Point p : rock) {
                        Point positionOfPoint = new Point(p.x + position.x, p.y + position.y);
                        cave.add(positionOfPoint);
                    }

                    break;
                } else {
                    position = newPosition;
                }
            }
        }

        int y = cave.stream().mapToInt(point -> point.y).max().orElse(-1);
        return String.valueOf(y + 1);
    }

    private boolean collide(Point position, Set<Point> rock, Set<Point> cave) {
        for (Point p : rock) {
            Point positionOfPoint = new Point(p.x + position.x, p.y + position.y);
            if (cave.contains(positionOfPoint)) {
                return true;
            }
        }

        return false;
    }
}
