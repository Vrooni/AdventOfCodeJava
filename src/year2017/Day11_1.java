package year2017;

import java.util.List;

public class Day11_1 {

    /**
     * THOUGHTS
     *     0,0     2,0
     *-1,1     1,1
     *     0,2     2,2
     * -1,3    1,3
     *     0,4     2,4
     * -1,5    1,5
     * <p>
     * Steps down/up: by 2
     * Steps diagonal 1
     */
    public String part1(List<String> lines) {
        String input = lines.getFirst();
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
        }

        return String.valueOf(getSteps(x, y));
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
