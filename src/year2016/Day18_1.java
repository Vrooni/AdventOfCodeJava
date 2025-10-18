package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day18_1 {
    public String part1(List<String> input)  {
        List<List<Boolean>> tiles = new ArrayList<>();

        List<Boolean> firstRow = new ArrayList<>();
        for (char c : input.getFirst().toCharArray()) {
            firstRow.add(c == '^');
        }
        tiles.add(firstRow);

        for (int i = 0; i < 40 - 1; i++) {
            List<Boolean> rowBefore = tiles.get(i);
            List<Boolean> thisRow = new ArrayList<>();

            for (int j = 0; j < rowBefore.size(); j++) {
                boolean left = j != 0 && rowBefore.get(j - 1);
                boolean middle = rowBefore.get(j);
                boolean right = j != rowBefore.size() - 1 && rowBefore.get(j + 1);

                thisRow.add(left && middle && !right
                                || !left && middle && right
                                || left && !middle && !right
                                || !left && !middle && right
                );
            }

            tiles.add(thisRow);
        }

        return String.valueOf(tiles.stream()
                .mapToInt(row -> (int) row.stream().filter(tile -> !tile).count())
                .sum());
    }
}
