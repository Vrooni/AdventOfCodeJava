package year2018;

import java.util.List;

public class Day1_1 {
    public String part1(List<String> input) {
        List<Integer> changes = input.stream().map(Integer::parseInt).toList();
        return String.valueOf(changes.stream().mapToInt(i -> i).sum());
    }
}
