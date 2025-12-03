package year2025;

import java.util.*;

public class Day3_1 {

    public String part1(List<String> input) {
        int result = 0;

        for (String line : input) {
            List<Integer> bank = Arrays.stream(line.split("")).map(Integer::parseInt).toList();
            Set<Integer> distinctBank = new HashSet<>(bank);
            List<Integer> sortedBank = new ArrayList<>(distinctBank);
            sortedBank.sort((a, b) -> Integer.compare(b, a));

            int indexOfFirst = bank.indexOf(sortedBank.getFirst());
            if (indexOfFirst == bank.size() - 1) {
                indexOfFirst = bank.indexOf(sortedBank.get(1));
            }

            int indexOfSecond = 0;
            for (Integer battery : sortedBank) {
                indexOfSecond = bank.lastIndexOf(battery);
                if (indexOfSecond > indexOfFirst) {
                    break;
                }
            }

            String joltage = bank.get(indexOfFirst) + String.valueOf(bank.get(indexOfSecond));
            result += Integer.parseInt(joltage);
        }

        return String.valueOf(result);
    }
}

