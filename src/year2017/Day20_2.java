package year2017;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day20_2 {
    private static class Particle {
        int number;
        Vector position;
        Vector velocity;
        Vector acceleration;

        public Particle(int number, Vector position, Vector velocity, Vector acceleration) {
            this.number = number;
            this.position = position;
            this.velocity = velocity;
            this.acceleration = acceleration;
        }

        private void move() {
            this.velocity.add(this.acceleration);
            this.position.add(this.velocity);
        }
    }

    private static class Vector {
        int x;
        int y;
        int z;

        public Vector(String vector) {
            String[] splitVector = vector
                    .substring(2)
                    .replace("<", "")
                    .replace(">", "")
                    .split(",");

            this.x = Integer.parseInt(splitVector[0]);
            this.y = Integer.parseInt(splitVector[1]);
            this.z = Integer.parseInt(splitVector[2]);
        }

        public void add(Vector other) {
            this.x += other.x;
            this.y += other.y;
            this.z += other.z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vector vector)) return false;
            return x == vector.x && y == vector.y && z == vector.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    public String part2(List<String> input) {
        List<Particle> particles = IntStream.range(0, input.size())
                .mapToObj(index -> {
                    String particle = input.get(index);

                    String[] splitParticle = particle.split(", ");
                    return new Particle(index,
                            new Vector(splitParticle[0]),
                            new Vector(splitParticle[1]),
                            new Vector(splitParticle[2])
                    );
                })
                .toList();

        int ticksSinceChange = 0;
        while (ticksSinceChange < 100) {
            int size = particles.size();
            particles.forEach(Particle::move);

            Map<Vector, Long> frequencyMap = particles.stream()
                    .collect(Collectors.groupingBy(particle -> particle.position, Collectors.counting()));
            particles = particles.stream()
                    .filter(particle -> frequencyMap.get(particle.position) == 1)
                    .toList();

            ticksSinceChange = size == particles.size() ? ticksSinceChange + 1 : 0;
        }

        return String.valueOf(particles.size());
    }
}
