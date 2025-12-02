package year2025;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day2_2 {
    public String part2(List<String> input) {
        long result = 0;

        for (String idRange : input.getFirst().split(",")) {
            String[] idRangeSplit = idRange.split("-");
            long from = Long.parseLong(idRangeSplit[0]);
            long to = Long.parseLong(idRangeSplit[1]);

            for (long i = from; i <= to ; i++) {
                String id = String.valueOf(i);

                if (hasSequence(id)) {
                    result += i;
                }
            }
        }

        return String.valueOf(result);
    }

    private boolean hasSequence(String id) {
        for (int length = 1; length <= id.length()/2; length++) {
            if (id.length() % length != 0) {
                continue;
            }

            Set<String> splitIds = new HashSet<>();
            for (int i = 0; i < id.length(); i += length) {
                splitIds.add(id.substring(i, i + length));
            }

            if (splitIds.size() == 1) {
                return true;
            }
        }

        return false;
    }
}
