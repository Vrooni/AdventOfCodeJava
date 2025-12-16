package year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11_1 {
    public String part1(List<String> input) {
        List<Long> stones = Arrays.stream(input.getFirst().split(" "))
                .map(Long::parseLong)
                .toList();

        for (int i = 0; i < 25; i++) {
            stones = getNewStones(stones);
        }

        return String.valueOf(stones.size());
    }

    private List<Long> getNewStones(List<Long> stones) {
        List<Long> newStones = new ArrayList<>();
        for (long stone : stones) {
            String stoneStr = String.valueOf(stone);

            if (stone == 0) {
                newStones.add(1L);
            } else if (stoneStr.length() % 2 == 0) {
                long firstStone = Long.parseLong(stoneStr.substring(0, stoneStr.length()/2));
                long secondStone = Long.parseLong(stoneStr.substring(stoneStr.length()/2));
                newStones.add(firstStone);
                newStones.add(secondStone);
            } else {
                newStones.add(stone * 2024);
            }
        }
        return newStones;
    }
}
