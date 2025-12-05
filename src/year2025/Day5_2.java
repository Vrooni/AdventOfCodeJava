package year2025;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day5_2 {
    record Range(long from, long to) {
        public boolean contains(long number) {
            return number >= this.from && number <= this.to;
        }
    }

    public String part2(List<String> input) {
        List<Range> ranges = new ArrayList<>();

        for (String line : input) {
            if (!line.contains("-")) {
                break;
            }

            String[] splitLine = line.split("-");
            ranges.add(new Range(Long.parseLong(splitLine[0]), Long.parseLong(splitLine[1])));
        }

        long result = combineRanges(ranges).stream().mapToLong(range -> range.to - range.from + 1).sum();
        return String.valueOf(result);
    }

    private Set<Range> combineRanges(List<Range> ranges) {
        while (true) {
            List<Range> rangesToRemove = new ArrayList<>();
            Range newRange = null;

            search: for (Range range1 : ranges) {
                for (Range range2 : ranges) {
                    if (range1.equals(range2)) {
                        continue;
                    }

                    if (range1.contains(range2.from)) {
                        newRange = new Range(range1.from, Math.max(range1.to, range2.to));
                        rangesToRemove.add(range1);
                        rangesToRemove.add(range2);
                        break search;
                    } else if (range1.contains(range2.to)) {
                        newRange = new Range(Math.min(range1.from, range2.from), range1.to);
                        rangesToRemove.add(range1);
                        rangesToRemove.add(range2);
                        break search;
                    }
                }
            }

            if (newRange == null) {
                break;
            }

            ranges.removeAll(rangesToRemove);
            ranges.add(newRange);
        }

        return new HashSet<>(ranges);
    }
}
