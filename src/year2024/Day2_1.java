package year2024;

import java.util.Arrays;
import java.util.List;

public class Day2_1 {
    public String part1(List<String> input) {
        int saveReports = 0;

        for (String line : input) {
            List<Integer> report = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
            if (isSave(report)) {
                saveReports++;
            }
        }

        return String.valueOf(saveReports);
    }

    private boolean isSave(List<Integer> report) {
        boolean desc = report.get(0) - report.get(1) > 0;

        for (int i = 0; i < report.size() - 1; i++) {
            int distance = Math.abs(report.get(i) - report.get(i+1));
            if (distance < 1 || distance > 3) {
                return false;
            }

            if (desc && report.get(i) - report.get(i+1) < 0) {
                return false;
            }

            if (!desc && report.get(i) - report.get(i+1) > 0) {
                return false;
            }
        }

        return true;
    }
}
