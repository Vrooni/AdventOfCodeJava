package year2023;

import java.util.ArrayList;
import java.util.List;

public class Day9_1 {

    public String part1(List<String> lines) {
        int result = 0;

        for (String line : lines) {
            List<Integer> input = readInput(line);
            List<List<Integer>> history = readHistory(input);
            history.getLast().add(0);

            for (int i = history.size() - 2; i >= 0; i--) {
                List<Integer> list = history.get(i);
                list.add(history.get(i + 1).getLast() + history.get(i).getLast());
            }

            result += history.getFirst().getLast();
        }

        return String.valueOf(result);
    }

    private List<Integer> readInput(String line) {
        List<Integer> input = new ArrayList<>();

        for (String number : line.split(" ")) {
            input.add(Integer.parseInt(number));
        }

        return input;
    }

    private List<List<Integer>> readHistory(List<Integer> input) {
        List<List<Integer>> history = new ArrayList<>();
        history.add(input);

        while (true) {
            List<Integer> mappedList = new ArrayList<>();

            for (int i = 0; i < input.size() - 1; i++) {
                mappedList.add(input.get(i + 1) - input.get(i));
            }

            input = mappedList;
            history.add(mappedList);

            if (mappedList.stream().allMatch(number -> number == 0)) {
                break;
            }
        }

        return history;
    }
}
