package year2019;

import java.util.ArrayList;
import java.util.List;

public class Day8_1 {
    public String part1(List<String> lines) {
        String input = lines.getFirst();
        List<String> layers = getLayers(input);

        String wantedLayer = layers.getFirst();
        int minZeros = Integer.MAX_VALUE;

        for (String layer : layers) {
            int zeros = countDigits(layer, '0');
            if (zeros < minZeros) {
                minZeros = zeros;
                wantedLayer = layer;
            }
        }

        return String.valueOf(countDigits(wantedLayer, '1') * countDigits(wantedLayer, '2'));
    }

    private int countDigits(String layer, char digit) {
        int digits = 0;

        for (char color : layer.toCharArray()) {
            if (color == digit) {
                digits++;
            }
        }

        return digits;
    }

    private List<String> getLayers(String input) {
        List<String> layers = new ArrayList<>();
        int width = 25;
        int height = 6;
        int length = width * height;

        while (!input.isEmpty()) {
            layers.add(input.substring(0, length));
            input = input.substring(length);
        }

        return layers;
    }
}
