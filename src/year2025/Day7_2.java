package year2025;

import java.util.*;

public class Day7_2 {
    record Position(int x, int y) {}

    private final Map<Position, Long> memo = new HashMap<>();

    public String part2(List<String> input) {
        Position start = new Position(input.getFirst().indexOf("S"), 0);
        return String.valueOf(getTimelines(start, input));
    }

    private long getTimelines(Position position, List<String> input) {
        for (int i = position.y + 1; i < input.size(); i++) {
            if (input.get(i).charAt(position.x) == '^') {
                Position split = new Position(position.x, i);

                if (memo.containsKey(split)) {
                    return memo.get(split);
                }

                long timelines = 0;
                timelines += getTimelines(new Position(position.x - 1, i), input);
                timelines += getTimelines(new Position(position.x + 1, i), input);

                memo.put(split, timelines);
                return timelines;
            }
        }

        // end reached = one timeline
        return 1L;
    }
}
