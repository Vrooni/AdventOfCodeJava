package year2015;

import java.util.Arrays;
import java.util.List;

public class Day2_2 {

    public String part2(List<String> lines) {
        int ribbon = 0;
        for (String line : lines) {
            int[] dimensions = Arrays.stream(line.split("x")).mapToInt(Integer::parseInt).sorted().toArray();

            ribbon += perimeter(dimensions[0], dimensions[1]) + dimensions[0] * dimensions[1] * dimensions[2];
        }

        return String.valueOf(ribbon);
    }

    private static int perimeter(int a, int b) {
        return a + a + b + b;
    }
}
