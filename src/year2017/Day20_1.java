package year2017;

import java.util.List;
import java.util.stream.IntStream;

public class Day20_1 {
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

        public int length() {
            return Math.abs(x) + Math.abs(y) + Math.abs(z);
        }
    }

    /**
     * General: this one with the lowest acceleration (vector length) is the answer
     * Problem: several particles with lowest acceleration exists, then lowest velocity, then lowest position
     *
     */
    public String part1(List<String> input) {
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

        Particle closestParticle = particles.getFirst();
        for (Particle particle : particles) {
            if (particle.acceleration.length() < closestParticle.acceleration.length()
                    || particle.acceleration.length() == closestParticle.acceleration.length()
                        && particle.velocity.length() < closestParticle.velocity.length()
                    || particle.acceleration.length() == closestParticle.acceleration.length()
                        && particle.velocity.length() == closestParticle.velocity.length()
                        && particle.position.length() < closestParticle.position.length()) {
                closestParticle = particle;
            }
        }

        return String.valueOf(closestParticle.number);
    }
}
