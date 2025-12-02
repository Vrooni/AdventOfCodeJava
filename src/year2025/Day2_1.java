package year2025;

import java.util.List;

public class Day2_1 {
    public String part1(List<String> input) {
        long result = 0;

        for (String idRange : input.getFirst().split(",")) {
            String[] idRangeSplit = idRange.split("-");
            long from = Long.parseLong(idRangeSplit[0]);
            long to = Long.parseLong(idRangeSplit[1]);

            for (long i = from; i <= to ; i++) {
                String id = String.valueOf(i);

                if (id.length() % 2 == 0 && id.substring(0, id.length() / 2).equals(id.substring(id.length() / 2))) {
                    result += i;
                }
            }
        }

        return String.valueOf(result);
    }
}