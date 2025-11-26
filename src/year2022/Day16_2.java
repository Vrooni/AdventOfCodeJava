package year2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16_2 {
    Map<String, List<String>> graph = new HashMap<>();
    Map<String, Integer> flowRates = new HashMap<>();

    Map<String, Map<String, Integer>> distances = new HashMap<>();
    List<String> valvesToOpen = new ArrayList<>();

    // for performance, I have to use a bitmap
    // this bitmap is binary presentation of open valves
    Map<String, Integer> indexMap = new HashMap<>();
    Map<String, Integer> memo = new HashMap<>();

    public String part2(List<String> lines) {
        readInput(lines);
        getDistances();

        for (String valve : graph.keySet()) {
            if (flowRates.get(valve) > 0) {
                indexMap.put(valve, valvesToOpen.size());
                valvesToOpen.add(valve);
            }
        }

        // all full: (e.g. 6 valves) 111111 = 2^6 - 1
        int full = (int) (Math.pow(2, valvesToOpen.size()) - 1);
        int maxRelease = 0;

        // iterate over all possibilities to open valves
        // 101101 - human tries to open the 0 ones; elephant tries to open 1 ones
        for (int bitMask = 0; bitMask <= full; bitMask++) {
            int releaseHuman = dfs("AA", 26, bitMask);
            int releaseElephant = dfs("AA", 26, full ^ bitMask);
            maxRelease = Math.max(maxRelease, releaseHuman + releaseElephant);
        }
        return String.valueOf(maxRelease);
    }

    private void readInput(List<String> lines) {
        for (String line : lines) {
            String name = line.substring(6, 8);
            int rate = Integer.parseInt(line.substring(23, line.indexOf(';')));

            List<String> adjacent = new ArrayList<>();
            String[] splitLine = line.split(",");
            for (String valve : splitLine) {
                adjacent.add(valve.substring(valve.length() - 2));
            }

            graph.put(name, adjacent);
            flowRates.put(name, rate);
        }
    }

    // ---------------- Floyd–Warshall ----------------

    private void getDistances() {
        // this time: floyd warshall algorithm
        for (String valve1 : graph.keySet()) {
            // first set all to "infinite"
            Map<String, Integer> dist = new HashMap<>();
            for (String valve2 : graph.keySet()) {
                dist.put(valve2, 1000000);
            }

            // same valve -> distance 0
            dist.put(valve1, 0);

            // set direct neighbours (edges)
            for (String neighbour : graph.get(valve1)) {
                dist.put(neighbour, 1);
            }

            distances.put(valve1, dist);
        }

        for (String v1 : graph.keySet()) {
            for (String v2 : graph.keySet()) {
                for (String v3 : graph.keySet()) {
                    int newDist = distances.get(v2).get(v1) + distances.get(v1).get(v3);

                    if (newDist < distances.get(v2).get(v3)) {
                        distances.get(v2).put(v3, newDist);
                    }
                }
            }
        }
    }

    private int dfs(String currentValve, int remainingTime, int openedMask) {
        String key = currentValve + "," + remainingTime + "," + openedMask;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int maxRelease = 0;
        for (String valve : valvesToOpen) {
            int bit = 1 << indexMap.get(valve); // = 2^index (but faster)
            if ((openedMask & bit) != 0) {
                //already open
                continue;
            }

            int cost = distances.get(currentValve).get(valve) + 1; // travel + open
            if (cost > remainingTime) {
                // can not open in time
                continue;
            }

            int currentRelease = flowRates.get(valve) * (remainingTime - cost);
            int release = currentRelease + dfs(valve, remainingTime - cost, openedMask | bit);
            maxRelease = Math.max(maxRelease, release);
        }

        memo.put(key, maxRelease);
        return maxRelease;
    }
}
