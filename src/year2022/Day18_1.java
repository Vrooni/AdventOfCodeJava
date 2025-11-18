package year2022;

import java.util.*;

public class Day18_1 {
    record Coordinate(int x, int y, int z) {}

    public String part1(List<String> lines) {
        Set<Coordinate> cubes = new HashSet<>();
        int allShownSides = 0;

        for (String line : lines) {
            String[] coordinate = line.split(",");

            int x = Integer.parseInt(coordinate[0]);
            int y = Integer.parseInt(coordinate[1]);
            int z = Integer.parseInt(coordinate[2]);

            cubes.add(new Coordinate(x, y, z));
        }

        for (Coordinate cube : cubes) {
            int shownSides = getShownSides(cube, cubes);
            allShownSides += shownSides;
        }

        return String.valueOf(allShownSides);
    }

    private static int getShownSides(Coordinate cube, Set<Coordinate> cubes) {
        Coordinate left = new Coordinate(cube.x-1, cube.y, cube.z);
        Coordinate right = new Coordinate(cube.x+1, cube.y, cube.z);
        Coordinate up = new Coordinate(cube.x, cube.y+1, cube.z);
        Coordinate down = new Coordinate(cube.x, cube.y-1, cube.z);
        Coordinate front = new Coordinate(cube.x, cube.y, cube.z-1);
        Coordinate back = new Coordinate(cube.x, cube.y, cube.z+1);

        int shownSides = 6;

        if (cubes.contains(left)) {
            shownSides--;
        }
        if (cubes.contains(right)) {
            shownSides--;
        }
        if (cubes.contains(up)) {
            shownSides--;
        }
        if (cubes.contains(down)) {
            shownSides--;
        }
        if (cubes.contains(front)) {
            shownSides--;
        }
        if (cubes.contains(back)) {
            shownSides--;
        }

        return shownSides;
    }
}
