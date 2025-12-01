package year2025;

import java.util.List;

public class Day1_2 {
    public String part2(List<String> input) {
        int number = 50;
        int password = 0;
        
        for (String move : input) {

            for (int i = 0; i < Integer.parseInt(move.substring(1)); i++) {
                if (move.charAt(0) == 'L') {
                    number--;
                    number += 100;
                    number %= 100;
                } else {
                    number++;
                    number %= 100;
                }

                if (number == 0) {
                    password++;
                }
            }
        }

        return String.valueOf(password);
    }
}