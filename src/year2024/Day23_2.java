package year2024;

import java.util.*;
import java.util.stream.Collectors;

public class Day23_2 {
    private final Map<String, List<String>> computers = new HashMap<>();
    private final Set<String> seenComputers = new HashSet<>();
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

    public String part2(List<String> input) {
        for (String line : input) {
            String[] splitLines = line.split("-");
            String computer1 = splitLines[0];
            String computer2 = splitLines[1];

            computers.computeIfAbsent(computer1, _ -> new ArrayList<>()).add(computer2);
            computers.computeIfAbsent(computer2, _ -> new ArrayList<>()).add(computer1);
        }

        for (Map.Entry<String, List<String>> entry : computers.entrySet()) {
            addConnections(entry.getKey(), entry.getValue(), new ArrayList<>());
        }

       Connection biggestConnection = connections.stream()
               .sorted((o1, o2) -> Integer.compare(o2.computers.size(), o1.computers.size()))
               .toList().getFirst();

        return biggestConnection.computers.stream().sorted().collect(Collectors.joining(","));
    }

    private void addConnections(String computer, List<String> neighbours, List<String> connection) {
        if (connection.stream().allMatch(computer1 -> computers.get(computer1).contains(computer))) {
            connection.add(computer);
            seenComputers.add(computer);

            for (String computer1 : neighbours) {
                if (seenComputers.contains(computer1)) {
                    connections.add(new Connection(connection));
                } else {
                    List<String> connectionCopy = new ArrayList<>(connection);
                    addConnections(computer1, computers.get(computer1), connectionCopy);
                }
            }
        } else {
            connections.add(new Connection(connection));
        }
    }
}
