package year2017;

import java.util.List;

public class Day9_1 {
    public String part1(List<String> lines) {
        String input = lines.getFirst();

        int score = 0;
        int depth = 0;
        boolean garbage = false;

        for (int i = 0; i < input.length(); i++) {
            if (garbage) {
                switch (input.charAt(i)) {
                    case '!' -> i++;
                    case '>' -> garbage = false;
                }
            } else {
                switch (input.charAt(i)) {
                    case '{' -> depth++;
                    case '}' -> score += depth--;
                    case '<' -> garbage = true;
                }
            }
        }

        return String.valueOf(score);
    }
}
