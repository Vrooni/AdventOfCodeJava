package year2022;

import java.util.Arrays;
import java.util.List;

public class Day4_1 {

    public String part1(List<String> lines) {
        int fullyOverlaps = 0;
        for (String line : lines) {
            String[] cleanRanges = line.split(",");

            int[] range1 = Arrays.stream(cleanRanges[0].split("-")).mapToInt(Integer::parseInt).toArray();
            int[] range2 = Arrays.stream(cleanRanges[1].split("-")).mapToInt(Integer::parseInt).toArray();

            if (range1[0] <= range2[0] && range1[1] >= range2[1]
                    || range1[0] >= range2[0] && range1[1] <= range2[1]) {
                fullyOverlaps++;
            }
        }

        return String.valueOf(fullyOverlaps);
    }
}
