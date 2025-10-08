package year2015;

import java.util.ArrayList;
import java.util.List;

public class Day10_1 {

    public String part1(List<String> lines) {
        List<Integer> sequence = lines.getFirst()
                .chars()
                .map(c -> c - '0')
                .boxed()
                .toList();

        for (int i = 0; i < 40; i++) {
            sequence = lookAndSay(sequence);
        }

        return String.valueOf(sequence.size());
    }

    private int getSizeOfSequence(List<Integer> input, int number, int index) {
        int size = 0;

        for (int i = index; i < input.size(); i++) {
            if (input.get(i) == number) {
                size++;
            } else {
                break;
            }
        }

        return size;
    }

    private List<Integer> lookAndSay(List<Integer> input) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < input.size();) {
            int number = input.get(i);
            int sizeOfSequence = getSizeOfSequence(input, number, i);

            result.add(sizeOfSequence);
            result.add(number);

            i += sizeOfSequence;
        }

        return result;
    }
}
