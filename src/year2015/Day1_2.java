package year2015;

import java.util.List;

public class Day1_2 {

    public String part2(List<String> lines) {
        String moves = lines.getFirst();

        int floor = 0;
        for (int i = 0; i < moves.length(); i++) {
            if (floor == -1) {
                return String.valueOf(i);
            }

            if (moves.charAt(i) == '(') {
                floor += 1;
            } else if (moves.charAt(i) == ')') {
                floor -= 1;
            }
        }

        return "-1";
    }
}
