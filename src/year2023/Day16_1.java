package year2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day16_1 {
    private static Set<Beam> history = new HashSet<>();

    private record Beam(Point point, Direction direction) {
    }

    private record Point(int x, int y) {
    }

    private enum Direction {
        RIGHT, LEFT, UP, DOWN
    }

    public String part1(List<String> lines) {
        return String.valueOf(getEnergized(0, 0, Direction.RIGHT, lines));
    }

    private int getEnergized(int x, int y, Direction direction, List<String> lines) {
        List<Beam> beams = new ArrayList<>();
        history = new HashSet<>();

        beams.add(new Beam(new Point(x, y), direction));
        history.add(new Beam(new Point(x, y), direction));

        while (!beams.isEmpty()) {
            beams = handleCollision(beams, lines);
            beams = move(beams);
            beams = filter(beams, lines);
        }

        return getEnergized().size();
    }

    private List<Beam> move(List<Beam> beams) {
        List<Beam> newBeams = new ArrayList<>();

        for (Beam beam : beams) {

            switch (beam.direction) {
                case UP -> newBeams.add(new Beam(new Point(beam.point.x, beam.point.y - 1), beam.direction));
                case DOWN -> newBeams.add(new Beam(new Point(beam.point.x, beam.point.y + 1), beam.direction));
                case LEFT -> newBeams.add(new Beam(new Point(beam.point.x - 1, beam.point.y), beam.direction));
                case RIGHT -> newBeams.add(new Beam(new Point(beam.point.x + 1, beam.point.y), beam.direction));
            }
        }

        return newBeams;
    }

    private List<Beam> handleCollision(List<Beam> beams, List<String> lines) {
        List<Beam> newBeams = new ArrayList<>();

        for (Beam beam : beams) {
            int x = beam.point.x;
            int y = beam.point.y;
            Point point = new Point(x, y);

            switch (lines.get(y).charAt(x)) {
                case '|' -> {
                    if (beam.direction == Direction.LEFT || beam.direction == Direction.RIGHT) {
                        newBeams.add(new Beam(point, Direction.UP));
                        newBeams.add(new Beam(point, Direction.DOWN));
                    } else {
                        newBeams.add(new Beam(point, beam.direction));
                    }
                }
                case '-' -> {
                    if (beam.direction == Direction.UP || beam.direction == Direction.DOWN) {
                        newBeams.add(new Beam(point, Direction.LEFT));
                        newBeams.add(new Beam(point, Direction.RIGHT));
                    } else {
                        newBeams.add(new Beam(point, beam.direction));
                    }
                }
                case '/' -> {
                    switch (beam.direction) {
                        case UP -> newBeams.add(new Beam(point, Direction.RIGHT));
                        case DOWN -> newBeams.add(new Beam(point, Direction.LEFT));
                        case LEFT -> newBeams.add(new Beam(point, Direction.DOWN));
                        case RIGHT -> newBeams.add(new Beam(point, Direction.UP));
                    }
                }
                case '\\' -> {
                    switch (beam.direction) {
                        case UP -> newBeams.add(new Beam(point, Direction.LEFT));
                        case DOWN -> newBeams.add(new Beam(point, Direction.RIGHT));
                        case LEFT -> newBeams.add(new Beam(point, Direction.UP));
                        case RIGHT -> newBeams.add(new Beam(point, Direction.DOWN));
                    }
                }
                default -> newBeams.add(new Beam(point, beam.direction));
            }
        }

        return newBeams;
    }

    private List<Beam> filter(List<Beam> beams, List<String> lines) {
        beams = beams.stream().filter(beam -> !isOutOfRange(beam, lines) && !history.contains(beam)).toList();
        history.addAll(new ArrayList<>(beams));

        return beams;
    }

    private boolean isOutOfRange(Beam beam, List<String> lines) {
        int x = beam.point.x;
        int y = beam.point.y;

        return y < 0 || y >= lines.size() || x < 0 || x >= lines.get(0).length();
    }

    private List<Point> getEnergized() {
        List<Point> energized = new ArrayList<>();

        for (Beam beam : history) {
            if (!energized.contains(beam.point)) {
                energized.add(beam.point);
            }
        }

        return energized;
    }
}
