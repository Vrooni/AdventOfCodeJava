package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day14_2 {

    public String part2(List<String> input) {
        int receipts = Integer.parseInt(input.getFirst());

        List<Integer> receiptList = Integer.toString(receipts).chars()
                .map(Character::getNumericValue).boxed()
                .toList();

        List<Integer> scores = new ArrayList<>(List.of(3, 7));
        int receipt1 = 0;
        int receipt2 = 1;

        while (!contains(scores, receiptList)) {
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

        return String.valueOf(scores.subList(0, indexOf(scores, receipts)).size());
    }

    private boolean contains(List<Integer> scores, List<Integer> receipts) {
        if (scores.size() < receipts.size()) {
            return false;
        }

        boolean contains = containsFrom(scores, receipts, scores.size() - receipts.size());
        if (contains) {
            return true;
        }

        if (scores.size() < receipts.size() + 1) {
            return false;
        }

        return containsFrom(scores, receipts, scores.size() - receipts.size() - 1);
    }

    private boolean containsFrom(List<Integer> scores, List<Integer> receipts, int offset) {
        for (int i = 0; i < receipts.size(); i++) {
            if (!scores.get(i + offset).equals(receipts.get(i))) {
                return false;
            }
        }

        return true;
    }

    private int indexOf(List<Integer> scores, int receipts) {
        String receiptsString = String.valueOf(receipts);

        String scoresString = scores.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(""));

        return scoresString.indexOf(receiptsString);
    }
}
