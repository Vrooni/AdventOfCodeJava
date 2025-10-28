package year2017;

import java.util.List;

public class Day17_2 {

    public String part2(List<String> lines) {
        int times = Integer.parseInt(lines.getFirst());

        int index = 0;
        int result = 0;

        for (int i = 1; i <= 50_000_000; i++) {
            index = (index + times) % i + 1;
            if (index == 1) {
                result = i;
            }
        }

        return String.valueOf(result);
    }
}
