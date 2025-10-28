package year2017;

import java.util.List;

public class Day5_2 {
    public String part2(List<String> input) {
        int[] jumps = input.stream().mapToInt(Integer::parseInt).toArray();

        int steps;
        int index = 0;
        int lastIndex = 0;

        for (steps = 0; index >= 0 && index < jumps.length; steps++) {
            int offset = jumps[index];
            index += offset;

            jumps[lastIndex] += offset >= 3 ? -1 : 1;
            lastIndex = index;
        }

        return String.valueOf(steps);
    }
}
