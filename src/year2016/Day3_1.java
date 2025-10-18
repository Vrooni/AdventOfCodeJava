package year2016;

import java.util.List;

public class Day3_1 {

    public String part1(List<String> input)  {
        int possible = 0;

        for (String lengths : input) {
            int length1 = Integer.parseInt(lengths.substring(0, 5).replaceAll(" ", ""));
            int length2 = Integer.parseInt(lengths.substring(5, 10).replaceAll(" ", ""));
            int length3 = Integer.parseInt(lengths.substring(10).replaceAll(" ", ""));

            if (length1 + length2 > length3 && length1 + length3 > length2 && length2 + length3 > length1) {
                possible++;
            }
        }

        return String.valueOf(possible);
    }
}
