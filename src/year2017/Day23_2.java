package year2017;

import java.util.List;

public class Day23_2 {
    public String part2(List<String> instructions) {
        int b = Integer.parseInt(instructions.get(0).split(" ")[2]);
        b = b * 100 + 100000;
        int c = b + 17000;
        int h = 0;

        for (; b != c + 17; b += 17) {
            int f = 1;
            for (int d = 2; d < b; d++) {
                if (b % d == 0) {
                    f = 0;
                    break;
                }
            }

            if (f == 0) {
                h++;
            }
        }

        return String.valueOf(h);
    }
}
