package year2023;

import java.util.List;

public class Day1_1 {

    public String part1(List<String> lines) {
        int sum = 0;

        for (String line : lines) {
            line = line.replaceAll("[^0-9]", "");
            int n = line.length();

            String stringNumber = line.charAt(0) + line.substring(n - 1);
            sum += Integer.parseInt(stringNumber);
        }
        return String.valueOf(sum);
    }
}
