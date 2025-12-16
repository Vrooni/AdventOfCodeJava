package year2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1_2 {
    public String part2(List<String> input) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        for (String line : input) {
            list1.add(Integer.parseInt(line.substring(0, line.indexOf(" "))));
            list2.add(Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1)));
        }

        Collections.sort(list1);
        Collections.sort(list2);

        int result = 0;
        for (Integer locationId : list1) {
            result += locationId * Collections.frequency(list2, locationId);
        }

        return String.valueOf(result);
    }
}
