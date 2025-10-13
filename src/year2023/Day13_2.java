package year2023;

import java.util.ArrayList;
import java.util.List;

public class Day13_2 {

    public String part2(List<String> lines) {
        int result = 0;

        while (!lines.isEmpty()) {
            int index = lines.indexOf("");

            List<String> subLines = lines.subList(0, index == -1 ? lines.size() : index);

            result += 100 * flipAndGetReflection(subLines);
            result += flipAndGetReflection(reverse(subLines));

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

    private int getReflection(List<String> lines, int flippedIndex, int size) {
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
                    int reflectionLength = Math.min(size - i, i);

                    if (flippedIndex >= i - reflectionLength && flippedIndex <= i + reflectionLength - 1) {
                        return i;
                    }
                }
            }
        }

        return 0;
    }

    private int flipAndGetReflection(List<String> subLines) {
        for (int i = 0; i < subLines.size(); i++) {
            String subLine = subLines.get(i);

            for (int j = i + 1; j < subLines.size(); j++) {
                List<Integer> indexesAtDifferences = new ArrayList<>();

                for (int k = 0; k < subLine.length(); k++) {
                    if (subLines.get(j).charAt(k) != subLine.charAt(k)) {
                        indexesAtDifferences.add(k);
                    }
                }

                if (indexesAtDifferences.size() == 1) {
                    int idx = indexesAtDifferences.getFirst();
                    char difference = subLine.charAt(idx);
                    String flippedSubLine = subLine.substring(0, idx) + (difference == '#' ? '.' : '#')
                            + subLine.substring(idx + 1);

                    List<String> subLinesWithFlipped = new ArrayList<>(subLines);
                    subLinesWithFlipped.set(i, flippedSubLine);
                    int reflection = getReflection(subLinesWithFlipped, i, subLines.size());

                    if (reflection != 0) {
                        return reflection;
                    }
                }
            }
        }

        return 0;
    }
}
