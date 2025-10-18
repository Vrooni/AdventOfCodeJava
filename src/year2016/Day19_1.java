package year2016;

import java.util.List;

public class Day19_1 {
    /**
     * PATTERN:
     *  1 -> 1
     *  2 -> 1
     *  3 -> 3
     *  4 -> 1
     *  5 -> 3
     *  6 -> 5
     *  7 -> 7
     *  8 -> 1
     *  9 -> 3
     * 10 -> 5
     * 11 -> 7
     * 12 -> 9
     * 13 -> 11
     * 14 -> 13
     * 15 -> 15
     * 16 -> 1
     * any potenz of 2 leads resets to 1, else adding 2
     * count -> 1 + 2*(count - 2^a) (where a is as high as possible)
     */
    public String part1(List<String> input)  {
        int count = Integer.parseInt(input.getFirst());
        int a = log(count, 2);
        return String.valueOf(1 + 2*(count - (int) Math.pow(2, a)));
    }

    public int log(int x, int base) {
        return (int) (Math.log(x) / Math.log(base));
    }
}
