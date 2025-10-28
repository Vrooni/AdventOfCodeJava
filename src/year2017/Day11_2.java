package year2017;

import java.util.ArrayList;
import java.util.List;

public class Day11_2 {
    private record Position(int x, int y) {}

    /**
     * THOUGHTS
     *     0:0     2:2
     * 2:1     2:1
     *     2:1     4:2
     * 4:2     4:2
     *     4:2     6:3
     * 6:3     6:3
     * <p>
     * Right and Left are more expensive in relation, so save all with same coord distance (x+y)
     */
    public String part2(List<String> lines) {
        String input = lines.getFirst();

        List<Position> furthestList = new ArrayList<>();
        int furthest = 0;
        int x = 0;
        int y = 0;

        for (String direction : input.split(",")) {
            switch (direction) {
                case "n" -> y -= 2;
                case "s" -> y += 2;
                case "nw" -> { x++; y--; }
                case "sw" -> { x++; y++; }
                case "ne" -> { x--; y--; }
                case "se" -> { x--; y++; }
            }

            int dist = Math.abs(x) + Math.abs(y);

            if (dist == furthest) {
                furthestList.add(new Position(x, y));
            }

            else if (dist > furthest) {
                furthest = dist;
                furthestList = new ArrayList<>();
                furthestList.add(new Position(x, y));
            }
        }

        int steps = 0;

        for (Position position : furthestList) {
            steps = Math.max(steps, getSteps(position.x, position.y));
        }

        return String.valueOf(steps);
    }

    private int getSteps(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);

        int diagonalSteps = Math.min(x, y);

        x -= diagonalSteps;
        y -= diagonalSteps;

        return diagonalSteps + y/2 + x;
    }
}
