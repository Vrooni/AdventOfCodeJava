package year2022;

import java.util.*;
import java.util.stream.Collectors;

public class Day17_2 {
    record Point(int x, long y) {
    }

    record RockMovement(int rockIndex, int jetIndex, Set<Point> floorState) {
    }

    record AtIterationState(long iteration, long height) {
    }

    public String part2(List<String> lines) {
        Set<Point> cave = new HashSet<>();
        List<Set<Point>> rocks = new ArrayList<>();
        String jet = lines.getFirst().replaceAll("\n", "");
        Map<RockMovement, AtIterationState> mapOfStates = new HashMap<>();

        rocks.add(Set.of(new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)));
        rocks.add(Set.of(new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)));
        rocks.add(Set.of(new Point(2, 2), new Point(2, 1), new Point(0, 0), new Point(1, 0), new Point(2, 0)));
        rocks.add(Set.of(new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3)));
        rocks.add(Set.of(new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(1, 1)));

        int indexJet = 0;
        Point position;

        long jumped = 0;

        for (long i = 0; i < 1000000000000L; i++) {

            if (jumped == 0) {
                //check if state occurred before
                RockMovement currentState = new RockMovement((int) i % 5, indexJet, getState(cave));

                if (mapOfStates.containsKey(currentState)) {
                    AtIterationState match = mapOfStates.get(currentState);

                    long height = cave.stream().mapToLong(p -> p.y).max().orElse(-1);

                    long deltaIterations = i - match.iteration;
                    long deltaHeight = height - match.height;

                    long remainingIterations = 1000000000000L - i;

                    long jumpLoops = remainingIterations / deltaIterations;
                    jumped = jumpLoops * deltaHeight;

                    System.out.println("Jumping " + jumpLoops + " loops, " + jumped + " blocks");

                    i += jumpLoops * deltaIterations;

                    if (i == 1000000000000L) {
                        break;
                    }
                }

                mapOfStates.put(currentState, new AtIterationState(i, cave.stream().mapToLong(p -> p.y).max().orElse(-1)));
            }

            //move rock
            Set<Point> rock = rocks.get((int) (i % 5));
            long y = cave.stream().mapToLong(point -> point.y).max().orElse(-1);

            position = new Point(2, y + 4);
            while (true) {
                //move side
                char move = jet.charAt(indexJet);
                indexJet = (indexJet + 1) % jet.length();
                Point newPosition = new Point(0, 0);

                switch (move) {
                    case '<' -> newPosition = new Point(Math.max(position.x - 1, 0), position.y);
                    case '>' ->
                            newPosition = new Point(Math.min(position.x + 1, 6 - rock.stream().mapToInt(p -> p.x).max().getAsInt()), position.y);
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

        long y = cave.stream().mapToLong(point -> point.y).max().orElse(-1);
        return String.valueOf(jumped + y + 1);
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

    private Set<Point> getState(Set<Point> cave) {
        long y = cave.stream().mapToLong(point -> point.y).max().orElse(-1);

        Queue<Point> points = new ArrayDeque<>();
        points.add(new Point(0, y + 1));
        Set<Point> seen = new HashSet<>();
        seen.add(new Point(0, y + 1));

        while (!points.isEmpty()) {
            Point point = points.poll();

            Point left = new Point(point.x - 1, point.y);
            Point right = new Point(point.x + 1, point.y);
            Point down = new Point(point.x, point.y - 1);

            if (left.x >= 0 && !cave.contains(left) && !seen.contains(left)) {
                points.add(left);
                seen.add(left);
            }
            if (right.x <= 6 && !cave.contains(right) && !seen.contains(right)) {
                points.add(right);
                seen.add(right);
            }
            if (down.y >= 0 && !cave.contains(down) && !seen.contains(down)) {
                points.add(down);
                seen.add(down);
            }
        }

        long minY = seen.stream().mapToLong(p1 -> p1.y).min().orElse(0);
        return seen.stream().map(p -> new Point(p.x, p.y - minY)).collect(Collectors.toSet());
    }
}
