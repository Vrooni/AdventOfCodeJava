package year2022;

import java.util.Arrays;
import java.util.List;

public class Day6_2 {
    public String part2(List<String> lines) {
        String input = lines.getFirst();

        for (int i = 0; i < input.length()-13; i++) {
            boolean allDistinct = true;
            char[] fourteenValues = Arrays.copyOfRange(input.toCharArray(), i, i+14);

            Magic: for (int j = 0; j < 14; j++) {
                for (int k = j+1; k < 14; k++) {
                    if (fourteenValues[j] == fourteenValues[k]) {
                        allDistinct = false;
                        break Magic;
                    }
                }
            }

            if (allDistinct) {
                return String.valueOf(i+14);
            }
        }

        return "-1";
    }
}
