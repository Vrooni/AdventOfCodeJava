package year2019;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10_1 {
    public record Position(int x, int y) {}

    private record Asteroid(Position position, List<Position> asteroidsInSight) {}

    public String part1(List<String> map) {
        Map<Position, Asteroid> asteroids = getAsteroids(map);

        Position monitoringStation = getMonitoringStation(asteroids);
        return String.valueOf(asteroids.get(monitoringStation).asteroidsInSight.size());
    }

    private Position getMonitoringStation(Map<Position, Asteroid> asteroids) {
        Position monitoringStation = new Position(0, 0);
        int maxAsteroids = Integer.MIN_VALUE;

        for (Map.Entry<Position, Asteroid> entry : asteroids.entrySet()) {
            if (entry.getValue().asteroidsInSight.size() > maxAsteroids) {
                monitoringStation = entry.getKey();
                maxAsteroids = entry.getValue().asteroidsInSight.size();
            }
        }

        return monitoringStation;
    }

    private Map<Position, Asteroid> getAsteroids(List<String> map) {
        Map<Position, Asteroid> asteroids = new HashMap<>();

        for (int y = 0; y < map.size(); y++) {
            String line = map.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    asteroids.put(new Position(x, y), new Asteroid(new Position(x, y), new ArrayList<>()));
                }
            }
        }

        for (Map.Entry<Position, Asteroid> entry1 : asteroids.entrySet()) {
            Position p1 = entry1.getKey();
            List<Position> asteroidsInsight = entry1.getValue().asteroidsInSight;

            for (Map.Entry<Position, Asteroid> entry2 : asteroids.entrySet()) {
                Position p2 = entry2.getKey();

                if (inSight(p1, p2, map)) {
                    asteroidsInsight.add(p2);
                }
            }
        }

        return asteroids;
    }

    private boolean inSight(Position p1, Position p2, List<String> map) {
        if (p1.equals(p2)) {
            return false;
        }

        Position vector = new Position(p2.x() - p1.x(), p2.y() - p1.y());
        int gcd = gcd(vector.x(), vector.y());
        if (Math.abs(gcd) == 1) {
            return true;
        }

        vector = new Position(
                vector.y() == 0 ? vector.x()/Math.abs(vector.x()) : vector.x() / gcd,
                vector.x() == 0 ? vector.y()/Math.abs(vector.y()) : vector.y() / gcd
        );

        for (int i = 1; ; i++) {
            Position asteroid = new Position(p1.x() + i*vector.x(), p1.y() + i*vector.y());
            if (asteroid.equals(p2)) {
                return true;
            }

            if (map.get(asteroid.y()).charAt(asteroid.x()) == '#') {
                return false;
            }
        }
    }

    private int gcd(int a, int b) {
        BigInteger b1 = BigInteger.valueOf(a);
        BigInteger b2 = BigInteger.valueOf(b);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }
}
