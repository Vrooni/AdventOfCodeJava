package year2019;

import java.util.List;

public class Day1_1 {
    public String part1(List<String> input) {
        List<Integer> masses = input.stream().map(Integer::parseInt).toList();
        return String.valueOf(masses.stream().mapToInt(mass -> mass/3 - 2).sum());
    }
}
