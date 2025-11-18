package year2022;

import java.util.List;

public class Day10_2 {
    //Part one
    public String part2(List<String> lines) {
        int x = 1;
        int cycle = 0;
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            cycle++;
            draw(x, cycle, result);

            String[] splittedLine = line.split(" ");
            if (splittedLine[0].equals("addx")) {
                cycle++;
                draw(x, cycle, result);
                x += Integer.parseInt(splittedLine[1]);
            }
        }

        return result.toString();
    }

    private void draw(int x, int cycle, StringBuilder result) {
        int i = (cycle-1)%40;
        if (Math.abs(i - x) <= 1) {
            result.append("#");
        } else {
            result.append(" ");
        }

        if (i == 39) {
            result.append("\n");
        }
    }
}
