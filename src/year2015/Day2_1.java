package year2015;

import java.util.List;

public class Day2_1 {

    public String part1(List<String> lines) {
        int wrappingPaper = 0;
        for (String line : lines) {
            String[] dimensions = line.split("x");

            int l = Integer.parseInt(dimensions[0]);
            int w = Integer.parseInt(dimensions[1]);
            int h = Integer.parseInt(dimensions[2]);

            wrappingPaper += area(l, w, h) + extraArea(l, w, h);
        }

        return String.valueOf(wrappingPaper);
    }

    private int area(int l, int w, int h) {
        return 2 * l * w + 2 * w * h + 2 * h * l;
    }

    private int extraArea(int l, int w, int h) {
        return Math.min(Math.min(l * w, l * h), w * h);
    }
}
