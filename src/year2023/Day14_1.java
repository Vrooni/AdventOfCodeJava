package year2023;

import java.util.ArrayList;
import java.util.List;

public class Day14_1 {

    public String part1(List<String> lines) {
        lines = new ArrayList<>(lines);
        roll(lines);

        int result = getResult(lines);
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
