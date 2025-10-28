package year2017;

import java.util.*;

public class Day12_1 {

    public String part1(List<String> input) {
        Map<String, List<String>> programs = new HashMap<>();

        for (String line : input) {
            String[] splitLine = line.split( " <-> " );
            programs.put(splitLine[0], List.of(splitLine[1].split(", ")));
        }

        return String.valueOf(getGroup(programs).size());
    }

    private List<String> getGroup(Map<String, List<String>> programs) {
        Queue<String> queue = new LinkedList<>();
        List<String> group = new ArrayList<>();

        queue.offer("0");
        group.add("0");

        while (!queue.isEmpty()) {
            String program = queue.poll();
            List<String> neighbours = programs.getOrDefault(program, new ArrayList<>());

            for (String neighbour : neighbours) {
                if (!group.contains(neighbour)) {
                    queue.offer(neighbour);
                    group.add(neighbour);
                }
            }
        }

        return group;
    }
}
