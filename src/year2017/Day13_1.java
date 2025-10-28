package year2017;

import java.util.List;

public class Day13_1 {
    public String part1(List<String> input) {
        int last = Integer.parseInt(input.getLast().split(": ")[0]);

        int[] layers = new int[last+1];

        for (String line : input) {
            String[] splitLine = line.split(": ");
            layers[Integer.parseInt(splitLine[0])] = Integer.parseInt(splitLine[1]);
        }

        int caughtLayers = 0;

        for (int i = 0; i < layers.length; i++) {
            int steps = layers[i] * 2 - 2;

            if (layers[i] != 0 && i % steps == 0) {
                caughtLayers += i * layers[i];
            }
        }

        return String.valueOf(caughtLayers);
    }
}
