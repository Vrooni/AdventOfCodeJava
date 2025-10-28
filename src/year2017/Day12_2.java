package year2017;

import java.util.*;

public class Day12_2 {
    public String part2(List<String> input) {
        Map<String, List<String>> programs = new HashMap<>();

        for (String line : input) {
            String[] splitLine = line.split( " <-> " );
            programs.put(splitLine[0], List.of(splitLine[1].split(", ")));
        }

        List<List<String>> groups = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            String programName = String.valueOf(i);
            if (groups.stream().anyMatch(g -> g.contains(programName))) {
                continue;
            }

            groups.add(getGroupFor(programName, programs));
        }

        return String.valueOf(groups.size());
    }

    private List<String> getGroupFor(String programName, Map<String, List<String>> programs) {
        Queue<String> queue = new LinkedList<>();
        List<String> group = new ArrayList<>();

        queue.offer(programName);
        group.add(programName);

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
