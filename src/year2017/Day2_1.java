package year2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2_1 {

    public String part1(List<String> lines) {
        int result = 0;

        for (String line : lines) {
            String[] splitLine = line.split("\t");
            List<Integer> numbers = new ArrayList<>(Arrays.stream(splitLine).map(Integer::parseInt).toList());

            numbers.sort(Integer::compareTo);
            result += numbers.getLast() - numbers.getFirst();
        }

        return String.valueOf(result);
    }
}
