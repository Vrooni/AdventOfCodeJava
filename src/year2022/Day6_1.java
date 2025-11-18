package year2022;

import java.util.List;

public class Day6_1 {
    public String part1(List<String> lines) {
        String input = lines.getFirst();

        for (int i = 0; i < input.length()-3; i++) {
            char c1 = input.charAt(i);
            char c2 = input.charAt(i+1);
            char c3 = input.charAt(i+2);
            char c4 = input.charAt(i+3);

            if (c1 != c2 && c1 != c3 && c1 != c4
                    && c2 != c3 && c2 != c4
                    && c3 != c4) {
                return String.valueOf(i+4);
            }
        }

        return "-1";
    }
}
