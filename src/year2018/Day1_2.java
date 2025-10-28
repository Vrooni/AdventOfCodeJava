package year2018;

import year2018.utils.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day1_2 {
    public String part2(List<String> input) {
        List<Integer> changes = input.stream().map(Integer::parseInt).toList();

        Set<Integer> frequencies = new HashSet<>();
        frequencies.add(0);
        int frequency = 0;

        while (true) {
            for (Integer change : changes) {

                frequency = frequency + change;
                if (frequencies.contains(frequency)) {
                    return String.valueOf(frequency);
                }
                frequencies.add(frequency);
            }
        }
    }
}
