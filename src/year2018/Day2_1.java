package year2018;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day2_1 {
    public String part1(List<String> ids) {
        int lettersTwice = 0;
        int lettersThreeTimes = 0;

        for (String id : ids) {
            Map<Character, Long> frequencies = id.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.groupingBy(
                            Function.identity(),
                            Collectors.counting()
                    ));

            lettersTwice += frequencies.values().stream().anyMatch(frequency -> frequency == 2L) ? 1 : 0;
            lettersThreeTimes += frequencies.values().stream().anyMatch(frequency -> frequency == 3L) ? 1 : 0;
        }

        return String.valueOf(lettersTwice * lettersThreeTimes);
    }
}
