package year2025;

import java.util.*;
import java.util.stream.Collectors;

public class Day3_2 {

    public String part2(List<String> input) {
        long result = 0;

        for (String line : input) {
            List<Integer> bank = Arrays.stream(line.split("")).map(Integer::parseInt).toList();
            Set<Integer> distinctBank = new HashSet<>(bank);
            List<Integer> sortedBank = new ArrayList<>(distinctBank);
            sortedBank.sort((a, b) -> Integer.compare(b, a));

            int indexOfFirst = 0;
            for (Integer battery : sortedBank) {
                indexOfFirst = bank.indexOf(battery);
                if (indexOfFirst <= bank.size() - 12) {
                    break;
                }
            }

            List<Integer> indices = new ArrayList<>();
            indices.add(indexOfFirst);
            for (int i = 0; i < 11; i++) {
                indices.add(getNextIndex(sortedBank, bank, indices));
            }

            String joltage = indices.stream()
                    .map(bank::get)
                    .map(Object::toString)
                    .collect(Collectors.joining(""));
            System.out.println(joltage);
            result += Long.parseLong(joltage);
        }

        return String.valueOf(result);
    }

    private int getNextIndex(List<Integer> sortedBank, List<Integer> bank, List<Integer> indices) {
        int index = 0;

        for (Integer battery : sortedBank) {
            index = indexOfAfter(bank, battery, indices.getLast() + 1);

            if (index >= bank.size() - (11 - indices.size())) {
                continue;
            }

            if (index > indices.getLast()) {
                break;
            }
        }

        return index;
    }

    private int indexOfAfter(List<Integer> bank, int battery, int startIndex) {
        for (int i = startIndex; i < bank.size(); i++) {
            if (bank.get(i).equals(battery)) {
                return i;
            }
        }

        return -1;
    }
}

