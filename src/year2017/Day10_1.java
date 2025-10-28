package year2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day10_1 {
    public String part1(List<String> lines) {
        String input = lines.getFirst();
        int[] numbers = IntStream.rangeClosed(0, 255).toArray();

        List<Integer> lengths = Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .toList();

        int currentPosition = 0;
        int skipSize = 0;

        for (Integer length : lengths) {
            for (int i = 0; i < length/2; i++) {
                int index1 = (currentPosition + i) % numbers.length;
                int index2 = (currentPosition + length - i - 1) % numbers.length;

                int temp = numbers[index1];
                numbers[index1] = numbers[index2];
                numbers[index2] = temp;
            }

            currentPosition = (currentPosition + length + skipSize++) % numbers.length;
        }

        return String.valueOf(numbers[0] * numbers[1]);
    }
}
