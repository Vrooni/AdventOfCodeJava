package year2022;

import java.util.Arrays;
import java.util.List;

public class Day4_2 {
    public String part2(List<String> lines) {
        int overlaps = 0;
        for (String line : lines) {
            String[] cleanRanges = line.split(",");

            int[] range1 = Arrays.stream(cleanRanges[0].split("-")).mapToInt(Integer::parseInt).toArray();
            int[] range2 = Arrays.stream(cleanRanges[1].split("-")).mapToInt(Integer::parseInt).toArray();

            if (isBetween(range1[0], range2) || isBetween(range1[1], range2)
                    || isBetween(range2[0], range1) || isBetween(range2[1], range1)) {
                overlaps++;
            }
        }

        return String.valueOf(overlaps);
    }

    private boolean isBetween(int value, int[] range) {
        return value >= range[0] && value <= range[1];
    }
}
