package year2018;

import java.util.*;

public class Day23_2 {
    private record NanoBot(int x, int y, int z, int radius) {}

    private static class Cube implements Comparable<Cube> {
        int x;
        int y;
        int z;
        int radius;
        List<NanoBot> nanoBots;

        public Cube(int x, int y, int z, int radius, List<NanoBot> nanoBots) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.radius = radius;
            this.nanoBots = nanoBots;
        }

        public List<Cube> divideCube() {
            List<Cube> dividedCube = new ArrayList<>();
            int newRadius = this.radius / 2;

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < 2; k++) {
                        Cube division = new Cube(
                                this.x + k * newRadius,
                                this.y + j * newRadius,
                                this.z + i * newRadius,
                                newRadius,
                                new ArrayList<>());

                        division.addBotsInRange(this.nanoBots);
                        dividedCube.add(division);
                    }
                }
            }

            return dividedCube;
        }

        private void addBotsInRange(List<NanoBot> nanoBots) {
            this.nanoBots = nanoBots.stream().filter(this::inRange).toList();
        }

        private boolean inRange(NanoBot bot) {
            int xDist = distance(this.x, this.x + radius - 1, bot.x);
            int yDist = distance(this.y, this.y + radius - 1, bot.y);
            int zDist = distance(this.z, this.z + radius - 1, bot.z);

            return xDist + yDist + zDist <= bot.radius;
        }

        private int distance() {
            return Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z);
        }

        private int distance(int min, int max, int coord) {
            if (coord < min) {
                return Math.abs(coord - min);
            } else if (coord > max) {
                return Math.abs(coord - max);
            }

            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cube cube)) return false;
            return x == cube.x && y == cube.y && z == cube.z && radius == cube.radius;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z, radius);
        }

        @Override
        public int compareTo(Cube o) {
            int botsCompare = Integer.compare(o.nanoBots.size(), this.nanoBots.size());
            if (botsCompare != 0) {
                return botsCompare;
            }

            return Integer.compare(this.distance(), o.distance());
        }
    }

    public String part2(List<String> input) {
        List<NanoBot> nanoBots = readInput(input);
        Cube cube = getInitialCube(nanoBots);

        Queue<Cube> queue = new PriorityQueue<>();
        queue.add(cube);

        while (!queue.isEmpty()) {
            Cube current = queue.poll();

            if (current.radius == 1) {
                return String.valueOf(current.distance());
            }

            queue.addAll(current.divideCube());
        }

        return "-1";
    }

    private List<NanoBot> readInput(List<String> input) {
        List<NanoBot> nanoBots = new ArrayList<>();

        for (String nanoBot : input) {
            String pos = nanoBot.split(", ")[0];
            String radius = nanoBot.split(", ")[1];

            String[] splitPos = pos.replace("pos=<", "").replace(">", "").split(",");

            nanoBots.add(new NanoBot(
                    Integer.parseInt(splitPos[0]),
                    Integer.parseInt(splitPos[1]),
                    Integer.parseInt(splitPos[2]),
                    Integer.parseInt(radius.replace("r=", ""))
            ));
        }

        return nanoBots;
    }

    private Cube getInitialCube(List<NanoBot> nanoBots) {
        int minX = nanoBots.stream().mapToInt(bot -> bot.x).min().getAsInt();
        int minY = nanoBots.stream().mapToInt(bot -> bot.y).min().getAsInt();
        int minZ = nanoBots.stream().mapToInt(bot -> bot.z).min().getAsInt();
        int maxX = nanoBots.stream().mapToInt(bot -> bot.x).max().getAsInt();
        int maxY = nanoBots.stream().mapToInt(bot -> bot.y).max().getAsInt();
        int maxZ = nanoBots.stream().mapToInt(bot -> bot.z).max().getAsInt();

        int radius = max(maxX - minX, maxY - minY, maxZ - minZ);

        //pad it up, so it's dividable by 2 every time
        int exponent = (int) Math.ceil(Math.log(radius) / Math.log(2));
        radius = (int) Math.pow(2, exponent);

        return new Cube(minX, minY, minZ, radius, nanoBots);
    }

    public static int max(int... numbers) {
        return Arrays.stream(numbers).max().getAsInt();
    }

}
