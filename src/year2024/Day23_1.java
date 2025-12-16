package year2024;

import java.util.*;

public class Day23_1 {
    private final Map<String, List<String>> computers = new HashMap<>();
    private final Set<Connection> connections = new HashSet<>();

    private record Connection(List<String> computers) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Connection that = (Connection) o;
            return new HashSet<>(computers).equals(new HashSet<>(that.computers));
        }

        @Override
        public int hashCode() {
            return Objects.hash(new HashSet<>(computers));
        }
    }

    public String part1(List<String> input) {
        for (String line : input) {
            String[] splitLines = line.split("-");
            String computer1 = splitLines[0];
            String computer2 = splitLines[1];

            computers.computeIfAbsent(computer1, _ -> new ArrayList<>()).add(computer2);
            computers.computeIfAbsent(computer2, _ -> new ArrayList<>()).add(computer1);
        }

        for (Map.Entry<String, List<String>> entry : computers.entrySet()) {
            String computer1 = entry.getKey();
            for (String computer2 : entry.getValue()) {
                for (String computer3 : computers.get(computer2)) {
                    if (entry.getValue().contains(computer3)) {
                        connections.add(new Connection(List.of(computer1, computer2, computer3)));
                    }
                }
            }
        }

        return String.valueOf(connections.stream()
                .filter(connection -> connection.computers.getFirst().startsWith("t")
                        || connection.computers.get(1).startsWith("t")
                        || connection.computers.get(2).startsWith("t"))
                .count()
        );
    }
}
