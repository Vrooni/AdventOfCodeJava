package year2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4_2 {

    public String part2(List<String> lines) {
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

        Map<Integer, Integer> wonCards = new HashMap<>(); // won cards with id and times card has been won

        for (Map.Entry<Integer, Integer> entrySet : cards.entrySet()) {
            wonCards.put(entrySet.getKey(), 1);
        }

        for (int i = 1; i <= cards.size(); i++) {
            int matchesCard = cards.get(i);
            int wonCountCard = wonCards.get(i);

            for (int j = i + 1; j <= i + matchesCard; j++) {
                wonCards.put(j, wonCards.get(j) + wonCountCard);
            }
        }

        return String.valueOf(sum(wonCards.values().stream().toList()));
    }

    private int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(a -> a).sum();
    }
}
