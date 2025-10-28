package year2017;

import java.util.List;

public class Day5_1 {
    public String part1(List<String> input) {
        int[] jumps = input.stream().mapToInt(Integer::parseInt).toArray();

        int steps;
        int index = 0;
        int lastIndex = 0;

        for (steps = 0; index >= 0 && index < jumps.length; steps++) {
            index += jumps[index];
            jumps[lastIndex]++;
            lastIndex = index;
        }

        return String.valueOf(steps);
    }
}
