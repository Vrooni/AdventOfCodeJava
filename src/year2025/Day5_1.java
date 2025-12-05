package year2025;

import java.util.ArrayList;
import java.util.List;

public class Day5_1 {
    record Range(long from, long to) {}

    public String part1(List<String> input) {
        List<Range> ranges = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        for (String line : input) {
            if (line.isEmpty()) {
                continue;
            }

            if (line.contains("-")) {
                String[] splitLine = line.split("-");
                ranges.add(new Range(Long.parseLong(splitLine[0]), Long.parseLong(splitLine[1])));
            } else {
                ids.add(Long.parseLong(line));
            }
        }

        long result = ids.stream().filter(id -> isValid(id, ranges)).count();
        return String.valueOf(result);
    }

    private boolean isValid(long id, List<Range> ranges) {
        for (Range range : ranges) {
            if (id >= range.from && id <= range.to) {
                return true;
            }
        }

        return false;
    }
}
