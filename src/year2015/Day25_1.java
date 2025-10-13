package year2015;

import java.util.List;

public class Day25_1 {

    public String part1(List<String> lines) {
        String input = lines.getFirst();
        String[] splitLine = input.substring(input.indexOf("row")).split(", ");

        int row = Integer.parseInt(splitLine[0].replace("row ", ""));
        int column = Integer.parseInt(splitLine[1]
                .replace("column ", "")
                .replace(".", "")
                .replace("\n", "")
        );

        int currentRow = 1;
        int currentColumn = 1;
        int value = 20151125;

        do {
            if (currentRow - 1 == 0) {
                currentRow = currentColumn + 1;
                currentColumn = 1;
            } else {
                currentRow--;
                currentColumn++;
            }

            long result = (value * 252533L) % 33554393;
            value = (int) result;
        } while (currentRow != row || currentColumn != column);

        return String.valueOf(value);
    }
}
