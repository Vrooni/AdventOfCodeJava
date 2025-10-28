package year2017;

import java.util.List;

public class Day9_2 {
    public String part2(List<String> lines) {
        String input = lines.getFirst();

        int score = 0;
        boolean garbage = false;

        for (int i = 0; i < input.length(); i++) {
            if (garbage) {
                switch (input.charAt(i)) {
                    case '!' -> i++;
                    case '>' -> garbage = false;
                    default -> score++;
                }
            } else {
                if (input.charAt(i) == '<') {
                    garbage = true;
                }
            }
        }

        return String.valueOf(score);
    }
}
