package year2023;

import java.util.ArrayList;
import java.util.List;

public class Day13_1 {

    public String part1(List<String> lines) {
        int result = 0;

        while (!lines.isEmpty()) {
            int index = lines.indexOf("");

            List<String> subLines = lines.subList(0, index == -1 ? lines.size() : index);
            result += 100 * getReflection(subLines);
            result += getReflection(reverse(subLines));

            lines = index == -1 ? new ArrayList<>() : lines.subList(index + 1, lines.size());
        }

        return String.valueOf(result);
    }

    private List<String> reverse(List<String> lines) {
        List<String> reversedLines = new ArrayList<>();

        for (int i = 0; i < lines.getFirst().length(); i++) {
            StringBuilder reversedLine = new StringBuilder();

            for (String line : lines) {
                reversedLine.append(line.charAt(i));
            }

            reversedLines.add(reversedLine.toString());
        }

        return reversedLines;
    }

    private int getReflection(List<String> lines) {
        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).equals(lines.get(i - 1))) {
                boolean reflection = true;

                for (int j = 1; i + j < lines.size() && i - 1 - j >= 0; j++) {
                    if (!lines.get(i + j).equals(lines.get(i - 1 - j))) {
                        reflection = false;
                        break;
                    }
                }

                if (reflection) {
                    return i;
                }
            }
        }

        return 0;
    }
}
