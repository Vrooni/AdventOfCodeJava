package year2017;

import java.util.List;

public class Day15_1 {
    public String part1(List<String> input) {
        long aValue = Integer.parseInt(input.get(0).split(" ")[4]);
        long bValue = Integer.parseInt(input.get(1).split(" ")[4]);

        int count = 0;

        for (int i = 0; i < 40000000; i++) {
            aValue = (aValue * 16807) % 2147483647;
            bValue = (bValue * 48271) % 2147483647;

            if ((aValue & 0xFFFF) == (bValue & 0xFFFF)) {
                count++;
            }
        }

        return String.valueOf(count);
    }
}
