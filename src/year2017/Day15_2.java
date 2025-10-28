package year2017;

import java.util.List;

public class Day15_2 {
    public String part2(List<String> input) {
        long aValue = Integer.parseInt(input.get(0).split(" ")[4]);
        long bValue = Integer.parseInt(input.get(1).split(" ")[4]);

        int count = 0;
        for (int i = 0; i < 5000000; i++) {
            aValue = getNextValue(aValue,16807, 4);
            bValue = getNextValue(bValue,48271, 8);

            if ((aValue & 0xFFFF) == (bValue & 0xFFFF)) {
                count++;
            }
        }

        return String.valueOf(count);
    }

    private long getNextValue(long value, int mul, int mod) {
        while (true) {
            value = (value * mul) % 2147483647;

            if (value % mod == 0) {
                return value;
            }
        }
    }
}
