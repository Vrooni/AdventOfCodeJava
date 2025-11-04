package year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12_1 {
    private static class Coordinate {
        private int x;
        private int y;
        private int z;

        public Coordinate(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private record Moon(Coordinate position, Coordinate velocity) {
        private int getPot() {
            return Math.abs(position.x) + Math.abs(position.y) + Math.abs(position.z);
        }

        private int getKin() {
            return Math.abs(velocity.x) + Math.abs(velocity.y) + Math.abs(velocity.z);
        }
    }

    public String part1(List<String> input) {
        List<Moon> moons = readInput(input);

        for (int i = 0; i < 1000; i++) {
            calculateVelocity(moons);
            move(moons);
        }

        int result = 0;
        for (Moon moon : moons) {
            result += moon.getPot() * moon.getKin();
        }

        return String.valueOf(result);
    }

    private List<Moon> readInput(List<String> input) {
        List<Moon> moons = new ArrayList<>();

        for (String line : input) {
            Pattern pattern = Pattern.compile("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>");
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                int z = Integer.parseInt(matcher.group(3));

                moons.add(new Moon(new Coordinate(x, y, z), new Coordinate(0, 0, 0)));
            }
        }

        return moons;
    }

    private void calculateVelocity(List<Moon> moons) {
        for (int j = 0; j < moons.size(); j++) {
            for (int k = j+1; k < moons.size(); k++) {
                Moon moon1 = moons.get(j);
                Moon moon2 = moons.get(k);

                int diffX = moon2.position.x - moon1.position.x;
                if (diffX > 0) {
                    moon1.velocity.x += 1;
                    moon2.velocity.x -= 1;
                } else if (diffX < 0) {
                    moon1.velocity.x -= 1;
                    moon2.velocity.x += 1;
                }

                int diffY = moon2.position.y - moon1.position.y;
                if (diffY > 0) {
                    moon1.velocity.y += 1;
                    moon2.velocity.y -= 1;
                } else if (diffY < 0) {
                    moon1.velocity.y -= 1;
                    moon2.velocity.y += 1;
                }

                int diffZ = moon2.position.z - moon1.position.z;
                if (diffZ > 0) {
                    moon1.velocity.z += 1;
                    moon2.velocity.z -= 1;
                } else if (diffZ < 0) {
                    moon1.velocity.z -= 1;
                    moon2.velocity.z += 1;
                }
            }
        }
    }

    private void move(List<Moon> moons) {
        for (Moon moon : moons) {
            moon.position.x += moon.velocity.x;
            moon.position.y += moon.velocity.y;
            moon.position.z += moon.velocity.z;
        }
    }
}
