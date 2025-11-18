package year2022;

import java.util.*;

public class Day18_2 {
    record Coordinate(int x, int y, int z) {}

    public String part2(List<String> lines) {
        Set<Coordinate> cubes = new HashSet<>();

        for (String line : lines) {
            String[] coordinate = line.split(",");

            int x = Integer.parseInt(coordinate[0]);
            int y = Integer.parseInt(coordinate[1]);
            int z = Integer.parseInt(coordinate[2]);

            cubes.add(new Coordinate(x, y, z));
        }

        Set<Coordinate> freeCubes = new HashSet<>();

        int fromX = cubes.stream().mapToInt(c -> c.x).min().getAsInt() - 1;
        int fromY = cubes.stream().mapToInt(c -> c.y).min().getAsInt() - 1;
        int fromZ = cubes.stream().mapToInt(c -> c.z).min().getAsInt() - 1;
        int toX = cubes.stream().mapToInt(c -> c.x).max().getAsInt() + 1;
        int toY = cubes.stream().mapToInt(c -> c.y).max().getAsInt() + 1;
        int toZ = cubes.stream().mapToInt(c -> c.z).max().getAsInt() + 1;
        
        for (int x = fromX; x <= toX; x++) {
            for (int y = fromY; y <= toY; y++) {
                freeCubes.add(new Coordinate(x, y, fromZ));
                freeCubes.add(new Coordinate(x, y, toZ));
            }
        }

        for (int x = fromX; x <= toX; x++) {
            for (int z = fromZ; z <= toZ; z++) {
                freeCubes.add(new Coordinate(x, fromY, z));
                freeCubes.add(new Coordinate(x, toY, z));
            }
        }

        for (int y = fromY; y <= toY; y++) {
            for (int z = fromZ; z <= toZ; z++) {
                freeCubes.add(new Coordinate(fromX, y, z));
                freeCubes.add(new Coordinate(toX, y, z));
            }
        }

        Queue<Coordinate> cubesToGo = new ArrayDeque<>(freeCubes);
        while (!cubesToGo.isEmpty()) {
            Coordinate cube = cubesToGo.poll();

            Coordinate[] candidates = {
                    new Coordinate(cube.x-1, cube.y, cube.z),
                    new Coordinate(cube.x+1, cube.y, cube.z),
                    new Coordinate(cube.x, cube.y+1, cube.z),
                    new Coordinate(cube.x, cube.y-1, cube.z),
                    new Coordinate(cube.x, cube.y, cube.z-1),
                    new Coordinate(cube.x, cube.y, cube.z+1)
            };

            for (Coordinate c : candidates) {
                if (isInBounds(c, fromX, toX, fromY, toY, fromZ, toZ) && !cubes.contains(c) && !freeCubes.contains(c)) {
                    cubesToGo.add(c);
                    freeCubes.add(c);
                }
            }
        }

        int allShownSides = getAllShownSides(cubes, freeCubes);
        return String.valueOf(allShownSides);
    }

    private static int getAllShownSides(Set<Coordinate> cubes, Set<Coordinate> freeCubes) {
        int allShownSides = 0;
        for (Coordinate cube : cubes) {
            Coordinate left = new Coordinate(cube.x-1, cube.y, cube.z);
            Coordinate right = new Coordinate(cube.x+1, cube.y, cube.z);
            Coordinate up = new Coordinate(cube.x, cube.y+1, cube.z);
            Coordinate down = new Coordinate(cube.x, cube.y-1, cube.z);
            Coordinate front = new Coordinate(cube.x, cube.y, cube.z-1);
            Coordinate back = new Coordinate(cube.x, cube.y, cube.z+1);

            if (freeCubes.contains(left)) {
                allShownSides++;
            }
            if (freeCubes.contains(right)) {
                allShownSides++;
            }
            if (freeCubes.contains(up)) {
                allShownSides++;
            }
            if (freeCubes.contains(down)) {
                allShownSides++;
            }
            if (freeCubes.contains(front)) {
                allShownSides++;
            }
            if (freeCubes.contains(back)) {
                allShownSides++;
            }
        }
        return allShownSides;
    }

    private boolean isInBounds(Coordinate cube, int fromX, int toX, int fromY, int toY, int fromZ, int toZ) {
        return cube.x >= fromX && cube.x <= toX
                && cube.y >= fromY && cube.y <= toY
                && cube.z >= fromZ && cube.z <= toZ;
    }
}
