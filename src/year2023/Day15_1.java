package year2023;

import java.util.ArrayList;
import java.util.List;

public class Day15_1 {

    public String part1(List<String> lines) {
        String input = lines.getFirst().trim();
        lines = new ArrayList<>(List.of(input.split(",")));

        int result = sum(lines.stream().map(this::getHash).toList());
        return String.valueOf(result);
    }

    private int getHash(String line) {
        int result = 0;

        for (char c : line.replaceAll("\n", "").toCharArray()) {
            result += c;
            result *= 17;
            result %= 256;
        }

        return result;
    }

    private int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(a -> a).sum();
    }
}
