package year2019;

import java.util.List;

public class Day1_2 {
    public String part2(List<String> input) {
        List<Integer> masses = input.stream().map(Integer::parseInt).toList();

        int fuel = 0;
        for (Integer mass : masses) {
            int fuelForMass = 0;

            for (int i = mass/3 - 2; i > 0; i = i/3 - 2) {
                fuelForMass += i;
            }

            fuel += fuelForMass;
        }

        return String.valueOf(fuel);
    }
}
