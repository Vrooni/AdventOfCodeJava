package year2017;

import java.util.List;

public class Day13_2 {
    public String part2(List<String> input) {
        int last = Integer.parseInt(input.getLast().split(": ")[0]);

        int[] layers = new int[last+1];

        for (String line : input) {
            String[] splitLine = line.split(": ");
            layers[Integer.parseInt(splitLine[0])] = Integer.parseInt(splitLine[1]);
        }

        for (int j = 0; true; j++) {
            boolean caught = false;

            for (int i = 0; i < layers.length; i++) {
                int steps = layers[i] * 2 - 2;

                if (layers[i] != 0 && (i+j) % steps == 0) {
                    caught = true;
                    break;
                }
            }

            if (!caught) {
                return String.valueOf(j);
            }
        }
    }
}
