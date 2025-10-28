package year2017;

import java.util.List;

public class Day1_1 {

    public String part1(List<String> lines) {
        String input = lines.getFirst();
        int result = input.charAt(0) == input.charAt(input.length() - 1)
                ? Integer.parseInt(String.valueOf(input.charAt(0)))
                : 0;

        for (int i = 0; i < input.length() - 1; i++) {
            if (input.charAt(i) == input.charAt(i + 1)) {
                result += Integer.parseInt(String.valueOf(input.charAt(i)));
            }
        }

        return String.valueOf(result);
    }
}
