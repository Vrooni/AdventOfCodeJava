package year2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1_1 {
    public String part1(List<String> lines) {
        List<Integer> calories = new ArrayList<>();
        int calorie = 0;
        for (String line : lines) {
            if (line.isEmpty()) {
                calories.add(calorie);
                calorie = 0;
            } else {
                calorie += Integer.parseInt(line);
            }
        }

        Collections.sort(calories);

        return String.valueOf(calories.getLast());
    }
}
