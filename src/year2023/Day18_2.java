package year2023;

import java.util.ArrayList;
import java.util.List;

public class Day18_2 {
    private record Position(long x, long y) {}

    public String part2(List<String> input) {
        long x = 0;
        long y = 0;

        List<Position> corners = new ArrayList<>();
        corners.add(new Position(x, y));

        long outerPoints = 0;
        for (String instruction : input) {

            instruction = instruction.split(" ")[2];
            int meters = Integer.parseInt(instruction.substring(2, 7), 16);
            char direction = instruction.charAt(7);

            switch (direction) {
                case '0' -> x += meters;
                case '1' -> y += meters;
                case '2' -> x -= meters;
                case '3' -> y -= meters;
            }

            corners.add(new Position(x, y));
            outerPoints += meters;
        }

        long area = calculateArea(corners);

        // https://en.wikipedia.org/wiki/Pick's_theorem
        // a way to calculate area out of gitter points
        // or to calculate gitter points from area
        // area = inner + outer/2 - 1;
        // inner = area - outer/2 + 1
        long innerPoints = area - outerPoints/2 + 1;

        return String.valueOf(innerPoints + outerPoints);
    }


    private long calculateArea(List<Position> corners) {
        // https://en.wikipedia.org/wiki/Shoelace_formula
        // a way to calculate area of polygons

        long area = 0;
        for (int i = 0; i < corners.size() - 1; i++) {
            Position corner1 = corners.get(i);
            Position corner2 = corners.get(i + 1);

            area += (corner1.y + corner2.y) * (corner1.x - corner2.x);
        }

        return Math.abs(area/2);
    }
}



