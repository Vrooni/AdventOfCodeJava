package year2016;

import java.util.List;

public class Day2_2 {

    public String part2(List<String> input)  {
        char[][] keypad = new char[][] {
                {' ', ' ', '1', ' ', ' '},
                {' ', '2', '3', '4', ' '},
                {'5', '6', '7', '8', '9'},
                {' ', 'A', 'B', 'C', ' '},
                {' ', ' ', 'D', ' ', ' '},
        };


        int x = 0;
        int y = 2;
        StringBuilder code = new StringBuilder();

        for (String moves : input) {
            for (String move : moves.split("")) {
                switch (move) {
                    case "U" -> y = y - 1 < 0 || keypad[y - 1][x] == ' ' ? y : y - 1;
                    case "D" -> y = y + 1 > 4 || keypad[y + 1][x] == ' ' ? y : y + 1;
                    case "L" -> x = x - 1 < 0 || keypad[y][x - 1] == ' ' ? x : x - 1;
                    case "R" -> x = x + 1 > 4 || keypad[y][x + 1] == ' ' ? x : x + 1;
                }
            }

            code.append(keypad[y][x]);
        }

        return code.toString();
    }
}
