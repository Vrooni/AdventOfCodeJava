package year2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2_2 {

    public String part2(List<String> lines) {
        int result = 0;

        for (String line : lines) {
            String[] splitLine = line.split("\t");
            List<Integer> numbers = new ArrayList<>(Arrays.stream(splitLine).map(Integer::parseInt).toList());

            for (int i = 0; i < numbers.size() - 1; i++) {
                for (int j = i+1; j < numbers.size() ; j++) {
                    float res = numbers.get(i) > numbers.get(j)
                            ? (float) numbers.get(i) / numbers.get(j)
                            : (float) numbers.get(j) / numbers.get(i);

                    if (res % 1 == 0) {
                        result += (int) res;
                    }
                }
            }
        }

        return String.valueOf(result);
    }
}
