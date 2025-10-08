package year2023;

import java.util.*;

public class Day8_2 {
    record MapInformation(String left, String right) {
    }

    public String part2(List<String> lines) {
        lines = new ArrayList<>(lines);
        String[] way = lines.removeFirst().split("");
        Map<String, MapInformation> maps = new HashMap<>();

        lines.removeFirst();

        for (String line : lines) {
            String[] splitLine = line.split(" = ");
            String key = splitLine[0];

            splitLine = splitLine[1]
                    .replaceAll("\\(", "")
                    .replaceAll("\\)", "")
                    .split(", ");

            maps.put(key, new MapInformation(splitLine[0], splitLine[1]));

        }

        List<List<String>> gonePaths = findCycles(way, maps);
        List<Integer> cyclesLengthFromAToZ = new ArrayList<>();
        List<Integer> cyclesLengthFromZToZ = new ArrayList<>();

        for (List<String> gonePath : gonePaths) {
            String endOfCycle = gonePath
                    .stream()
                    .filter(p -> p.endsWith("Z"))
                    .findFirst().get();

            int index = gonePath.indexOf(endOfCycle);
            cyclesLengthFromAToZ.add(index);
            cyclesLengthFromZToZ.add(gonePath.size() - (index + 1));
        }

        int maxCycleLength = Collections.max(cyclesLengthFromZToZ);
        int offset = cyclesLengthFromAToZ.get(cyclesLengthFromZToZ.indexOf(maxCycleLength));

        for (long i = maxCycleLength; true; i += maxCycleLength) {
            long endIndex = offset + i;
            boolean reachedEnd = true;

            for (int j = 0; j < cyclesLengthFromZToZ.size(); j++) {
                int currentOffset = cyclesLengthFromAToZ.get(j);
                int cycleLength = cyclesLengthFromZToZ.get(j);

                if ((endIndex - currentOffset) % cycleLength != 0) {
                    reachedEnd = false;
                    break;
                }
            }

            if (reachedEnd) {
                return String.valueOf(endIndex);
            }
        }
    }

    private List<List<String>> findCycles(String[] way, Map<String, MapInformation> maps) {
        List<String> currentKeys = new ArrayList<>(maps.keySet()
                .stream()
                .filter(key -> key.endsWith("A"))
                .toList());

        List<List<String>> gonePaths = new ArrayList<>();
        List<Integer> unfinishedPaths = new ArrayList<>();

        for (int i = 0; i < currentKeys.size(); i++) {
            List<String> currentPath = new ArrayList<>();
            currentPath.add(currentKeys.get(i));
            gonePaths.add(currentPath);
            unfinishedPaths.add(i);
        }

        List<Integer> finishedPaths;
        String currentKey = "";

        outer: while (true) {
            for (String dir : way) {
                finishedPaths = new ArrayList<>();

                for (int i : unfinishedPaths) {
                    String key = currentKeys.get(i);

                    currentKey = switch (dir) {
                        case "L" -> maps.get(key).left;
                        case "R" -> maps.get(key).right;
                        default -> currentKey;
                    };

                    gonePaths.get(i).add(currentKey);
                    currentKeys.set(i, currentKey);

                    if (hasCycle(gonePaths.get(i))) {
                        finishedPaths.add(i);
                    }
                }

                unfinishedPaths.removeAll(finishedPaths);
                if (unfinishedPaths.isEmpty()) {
                    break outer;
                }
            }
        }

        return gonePaths;
    }

    private static boolean hasCycle(List<String> path) {
        return (path.stream().filter(p -> p.endsWith("Z")).count() == 2);
    }
}
