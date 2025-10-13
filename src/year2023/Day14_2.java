package year2023;

import java.util.ArrayList;
import java.util.List;

public class Day14_2 {
    private final List<List<String>> history = new ArrayList<>();
    private final int TIMES = 1000000000;

    public String part2(List<String> lines) {
        lines = new ArrayList<>(lines);
        int cycleIndex = findCycle(lines);

        for (int i = 0; i < cycleIndex; i++) {
            history.removeFirst();
        }

        int endIndex = (TIMES - cycleIndex) % history.size() - 1;
        endIndex = endIndex == -1 ? history.size() - 1 : endIndex; // Underflow

        int result = getResult(history.get(endIndex));
        return String.valueOf(result);
    }

    private void roll(List<String> lines) {
        for (int x = 0; x < lines.getFirst().length(); x++) {
            for (int y = 0; y < lines.size(); y++) {

                // roll to north
                if (lines.get(y).charAt(x) == 'O') {
                    for (int y2 = y - 1; y2 >= 0; y2--) {
                        if (lines.get(y2).charAt(x) != '.') {
                            lines.set(y, replace(lines.get(y), x, '.'));
                            lines.set(y2 + 1, replace(lines.get(y2 + 1), x, 'O'));
                            break;
                        } else if (y2 == 0) {
                            lines.set(y, replace(lines.get(y), x, '.'));
                            lines.set(y2, replace(lines.get(y2), x, 'O'));
                            break;
                        }
                    }
                }
            }
        }
    }

    private List<String> turn(List<String> lines) {
        List<String> turnedLines = new ArrayList<>();

        for (int x = 0; x < lines.getFirst().length(); x++) {
            StringBuilder s = new StringBuilder();
            for (int y = lines.size() - 1; y >= 0; y--) {
                s.append(lines.get(y).charAt(x));
            }

            turnedLines.add(s.toString());
        }

        return turnedLines;
    }

    private int findCycle(List<String> lines) {
        for (int i = 0; i < TIMES; i++) {
            if (history.contains(lines)) {
                return history.indexOf(lines);
            }

            // ignore 0 step
            if (i != 0) {
                history.add(new ArrayList<>(lines));
            }

            for (int j = 0; j < 4; j++) {
                roll(lines);
                lines = turn(lines);
            }
        }

        return -1;
    }

    private int getResult(List<String> lines) {
        int result = 0;

        for (int i = 0; i < lines.size(); i++) {
            int times = lines.size() - i;
            result += times * lines.get(i).replaceAll("\\.", "").replaceAll("#", "").length();
        }

        return result;
    }

    private String replace(String s, int index, char c) {
        return s.substring(0, index) + c + s.substring(index + 1);
    }
}
