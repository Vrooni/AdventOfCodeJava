package year2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4_1 {

    public String part1(List<String> lines) {
        Map<Integer, Integer> cards = new HashMap<>(); // cards with id and matches
        List<String> winningNumbers;
        int matches;

        for (String line : lines) {
            winningNumbers = new ArrayList<>();
            matches = 0;

            int id = Integer.parseInt(line
                    .split(":")[0]
                    .replace("Card", "")
                    .trim());

            line = line.substring(line.indexOf(":") + 1);
            String[] splittedLine = line.split("\\|");

            for (String number : splittedLine[0].split(" ")) {
                number = number.replaceAll(" ", "");
                if (!number.isEmpty()) {
                    winningNumbers.add(number);
                }
            }

            for (String number : splittedLine[1].split(" ")) {
                number = number.replaceAll(" ", "");
                if (!number.isEmpty() && winningNumbers.contains(number)) {
                    matches++;
                }
            }

            cards.put(id, matches);
        }

        int sum = 0;

        for (int value : cards.values()) {
            sum += (int) Math.pow(2, value - 1);
        }

        return String.valueOf(sum);
    }
}
