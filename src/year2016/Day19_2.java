package year2016;

import java.util.List;

public class Day19_2 {
    /**
     *  PATTERN:
     * 1: 1
     * 2: 1
     * 3: 3
     * 4: 1
     * 5: 2
     * 6: 3
     * 7: 5
     * 8: 7
     * 9: 9
     * 10: 1
     * 11: 2
     * 12: 3
     * 13: 4
     * 14: 5
     * 15: 6
     * 16: 7
     * 17: 8
     * 18: 9
     * 19: 11
     * 20: 13
     * 21: 15
     * 22: 17
     * 23: 19
     * 24: 21
     * 25: 23
     * 26: 25
     * 27: 27
     * 28: 1
     * 29: 2
     * 30: 3
     * Any potenz of 3 (+1) leads resets to 1, else adding 1 before half way or 2 after half way
     * formel == ???
     * first half = remainder
     * second half = change + 2*(j - (power + change)))
     * second half condition: remainder > change
     */
    public String part2(List<String> input)  {
        int count = Integer.parseInt(input.getFirst());
        int a = log(count-1, 3);

        int power = (int) Math.pow(3, a);
        int nextPower = (int) Math.pow(3, a+1);

        int remainder = count - power;

        int change = (nextPower - power) / 2;
        int jChange = power + change;


        return String.valueOf(
            count == 1 ? 1 : (
                remainder > change
                ? change + 2*(count - jChange)
                : remainder
            )
        );
    }

    public int log(int x, int base) {
        return (int) (Math.log(x) / Math.log(base));
    }
}
