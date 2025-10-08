package year2015;

import java.util.List;

public class Day1_1 {

    public String part1(List<String> lines) {
        String moves = lines.getFirst();

        int floor = 0;
        for (char move : moves.toCharArray()) {
            if (move == '(') {
                floor += 1;
            } else if (move == ')') {
                floor -= 1;
            }
        }

        return String.valueOf(floor);
    }
}
