package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day14_1 {

    public String part1(List<String> input) {
        int receipts = Integer.parseInt(input.getFirst());

        List<Integer> scores = new ArrayList<>(List.of(3, 7));
        int receipt1 = 0;
        int receipt2 = 1;

        while (scores.size() < receipts + 10) {
            int newScore = scores.get(receipt1) + scores.get(receipt2);
            if (newScore < 10) {
                scores.add(newScore);
            } else {
                scores.add(newScore / 10);
                scores.add(newScore % 10);
            }

            receipt1 = (receipt1 + scores.get(receipt1) + 1) % scores.size();
            receipt2 = (receipt2 + scores.get(receipt2) + 1) % scores.size();
        }

        return scores
                .subList(receipts, receipts + 10).stream()
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }
}
