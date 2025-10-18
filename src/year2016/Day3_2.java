package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day3_2 {

    public String part2(List<String> input)  {
        List<List<Integer>> allLengths = new ArrayList<>();
        int possible = 0;

        for (String lengths : input) {
            int length1 = Integer.parseInt(lengths.substring(0, 5).replaceAll(" ", ""));
            int length2 = Integer.parseInt(lengths.substring(5, 10).replaceAll(" ", ""));
            int length3 = Integer.parseInt(lengths.substring(10).replaceAll(" ", ""));

            allLengths.add(List.of(length1, length2, length3));
        }

        for (int i = 0; i < allLengths.getFirst().size(); i++) {
            for (int j = 0; j < allLengths.size(); j += 3) {
                int length1 = allLengths.get(j).get(i);
                int length2 = allLengths.get(j+1).get(i);
                int length3 = allLengths.get(j+2).get(i);

                if (length1 + length2 > length3 && length1 + length3 > length2 && length2 + length3 > length1) {
                    possible++;
                }
            }
        }

        return String.valueOf(possible);
    }
}
