package year2017;

import java.util.List;

public class Day1_2 {

    public String part2(List<String> lines) {
        String input = lines.getFirst();

        int steps = input.length() / 2;
        int result = 0;

        for (int i = 0; i < input.length(); i++) {
            int next = (i + steps) % input.length();

            if (input.charAt(i) == input.charAt(next)) {
                result += Integer.parseInt(String.valueOf(input.charAt(i)));
            }
        }

        return String.valueOf(result);
    }
}
