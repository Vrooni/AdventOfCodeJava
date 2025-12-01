package year2025;

import java.util.List;

public class Day1_1 {
    public String part1(List<String> input) {
        int number = 50;
        int password = 0;

        for (String move : input) {
            if (move.charAt(0) == 'R') {
                number += Integer.parseInt(move.substring(1));
                number %= 100;
            } else {
                number -= Integer.parseInt(move.substring(1));
                number %= 100;

                if (number < 0) {
                    number = 100 + number;
                }
            }

            if (number == 0) {
                password++;
            }
        }

        return String.valueOf(password);
    }
}
