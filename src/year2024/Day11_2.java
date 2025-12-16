package year2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11_2 {
    public String part2(List<String> input) {
        List<Long> stones = Arrays.stream(input.getFirst().split(" "))
                .map(Long::parseLong)
                .toList();

        Map<Long, Long> stonesCount = new HashMap<>();
        for (Long stone : stones) {
            stonesCount.merge(stone, 1L, Long::sum);
        }

        for (int i = 0; i < 75; i++) {
            Map<Long, Long> newStonesCount = new HashMap<>();

            for (Map.Entry<Long, Long> entry : stonesCount.entrySet()) {
                long stone = entry.getKey();
                long count = entry.getValue();
                String stoneStr = String.valueOf(stone);

                if (stone == 0) {
                    newStonesCount.merge(1L, count, Long::sum);
                } else if (stoneStr.length() % 2 == 0) {
                    long firstStone = Long.parseLong(stoneStr.substring(0, stoneStr.length()/2));
                    long secondStone = Long.parseLong(stoneStr.substring(stoneStr.length()/2));
                    newStonesCount.merge(firstStone, count, Long::sum);
                    newStonesCount.merge(secondStone, count, Long::sum);
                } else {
                    newStonesCount.merge(stone * 2024, count, Long::sum);
                }
            }

            stonesCount = newStonesCount;
        }

        return String.valueOf(stonesCount.values().stream().mapToLong(v -> v).sum());
    }
}
