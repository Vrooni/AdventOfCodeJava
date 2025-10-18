package year2016;

import java.util.List;

public class Day2_1 {

    public String part1(List<String> input)  {
        char[][] keypad = {
                {'1', '2', '3'},
                {'4', '5', '6'},
                {'7', '8', '9'}
        };

        int x = 1;
        int y = 1;
        StringBuilder code = new StringBuilder();

        for (String moves : input) {
            for (String move : moves.split("")) {
                switch (move) {
                    case "U" -> y = Math.max(0, y - 1);
                    case "D" -> y = Math.min(2, y + 1);
                    case "L" -> x = Math.max(0, x - 1);
                    case "R" -> x = Math.min(2, x + 1);
                }
            }

            code.append(keypad[y][x]);
        }

        return code.toString();
    }
}
