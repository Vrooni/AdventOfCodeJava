package year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO not ready yet
public class Day12_2 {
    private static class Coordinate {
        private int x;
        private int y;
        private int z;

        public Coordinate(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Coordinate that)) return false;
            return x == that.x && y == that.y && z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    private record Moon(Coordinate position, Coordinate velocity) {
        private Moon copy() {
            return new Moon(
                    new Coordinate(position.x, position.y, position.z),
                    new Coordinate(velocity.x, velocity.y, velocity.z)
            );
        }

        @Override
        public String toString() {
            return "p(" + position.x + ", " + position.y + ", " + position.z + ")" +
                    " " +
                    "v(" + velocity.x + ", " + velocity.y + ", " + velocity.z + ")";
        }
    }

    public String part2(List<String> input) {
        List<Moon> moons = readInput(input);
        Moon moon1 = moons.getFirst().copy();

        int before = 0;
        for (int i = 0; true; i++) {
            calculateVelocity(moons);
            move(moons);

            if (moons.getFirst().equals(moon1)) {
                return String.valueOf(i - before);
            }
        }

        //Position x of moon 1 for time t
        //px1(t) = px1(t-1) + vx(t-1)
        //vx1(t) = vx1(t-1) + dx2(t-1) + dx3(t-1) + dx4(t-1)
        //dx2(t-1) = px2(t-1) - px1(t-1) / |px2(t-1) - px1(t-1)|
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
