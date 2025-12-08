package year2025;

import java.util.*;

public class Day8_2 {
    private record JunctionBox(int x, int y, int z) {
        public double getDistance(JunctionBox otherBox) {
            return Math.sqrt(Math.pow(this.x - otherBox.x, 2)
                    + Math.pow(this.y - otherBox.y, 2)
                    + Math.pow(this.z - otherBox.z, 2)
            );
        }
    }

    private record Distance(JunctionBox box1, JunctionBox box2, double distance) {}

    public String part2(List<String> input) {
        List<JunctionBox> junctionBoxes = input.stream().map(line -> {
            String[] coordinates = line.split(",");
            return new JunctionBox(
                    Integer.parseInt(coordinates[0]),
                    Integer.parseInt(coordinates[1]),
                    Integer.parseInt(coordinates[2])
            );
        }).toList();

        List<Distance> distances = new ArrayList<>();
        for (int i = 0; i < junctionBoxes.size(); i++) {
            JunctionBox box1 = junctionBoxes.get(i);

            for (int j = i + 1; j < junctionBoxes.size(); j++) {
                JunctionBox box2 = junctionBoxes.get(j);

                distances.add(new Distance(box1, box2, box1.getDistance(box2)));
            }
        }
        distances.sort(Comparator.comparingDouble(o -> o.distance));

        List<Set<JunctionBox>> circuits = new ArrayList<>();
        for (Distance distance : distances) {

            List<Set<JunctionBox>> matchingCircuits = circuits.stream()
                    .filter(circuit -> circuit.contains(distance.box1) || circuit.contains(distance.box2))
                    .toList();

            // create new one
            if (matchingCircuits.isEmpty()) {
                Set<JunctionBox> circuit = new HashSet<>();
                circuit.add(distance.box1);
                circuit.add(distance.box2);
                circuits.add(circuit);
            }

            // union circuits
            else {
                Set<JunctionBox> newCircuit = new HashSet<>(matchingCircuits.stream().flatMap(Collection::stream).toList());
                newCircuit.add(distance.box1);
                newCircuit.add(distance.box2);

                circuits.removeAll(matchingCircuits);
                circuits.add(newCircuit);

                if (circuits.size() == 1 && circuits.getFirst().size() == junctionBoxes.size()) {
                    return String.valueOf(distance.box1.x * distance.box2.x);
                }
            }
        }

        return "-1";
    }
}
