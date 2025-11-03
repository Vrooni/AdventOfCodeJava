package year2019;

import java.util.*;

public class Day6_2 {
    private record SpaceObject(String name, int steps) implements Comparable<SpaceObject> {
        @Override
        public int compareTo(SpaceObject o) {
            return Integer.compare(this.steps, o.steps);
        }
    }

    public String part2(List<String> input) {
        Map<String, List<String>> spaceObjects = new HashMap<>();
        for (String line : input) {
            String object1 = line.split("\\)")[0];
            String object2 = line.split("\\)")[1];

            spaceObjects.computeIfAbsent(object1, _ -> new ArrayList<>()).add(object2);
            spaceObjects.computeIfAbsent(object2, _ -> new ArrayList<>()).add(object1);
        }

        Queue<SpaceObject> queue = new PriorityQueue<>();
        Set<SpaceObject> visited = new HashSet<>();
        queue.add(new SpaceObject("YOU", 0));
        visited.add(new SpaceObject("YOU", 0));

        while (!queue.isEmpty()) {
            SpaceObject current = queue.poll();

            if (Objects.equals(current.name, "SAN")) {
                return String.valueOf(current.steps - 2);
            }
            for (String neighbour : spaceObjects.get(current.name)) {
                SpaceObject neighbourObject = new SpaceObject(neighbour, current.steps + 1);

                if (!visited.contains(neighbourObject)) {
                    visited.add(neighbourObject);
                    queue.add(neighbourObject);
                }
            }
        }

        return "-1";
    }
}
