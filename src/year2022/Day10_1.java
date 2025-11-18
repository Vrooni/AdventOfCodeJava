package year2022;

import java.util.List;

public class Day10_1 {

    public String part1(List<String> lines) {
        int x = 1;
        int cycle = 0;
        int signalStrength = 0;

        for (String line : lines) {
            cycle++;
            signalStrength += signalStrength(x, cycle);

            String[] splittedLine = line.split(" ");
            if (splittedLine[0].equals("addx")) {
                cycle++;
                signalStrength += signalStrength(x, cycle);
                x += Integer.parseInt(splittedLine[1]);
            }
        }

        return String.valueOf(signalStrength);
    }

    private int signalStrength(int x, int cycle) {
        if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
            return cycle * x;
        }

        return 0;
    }
}
