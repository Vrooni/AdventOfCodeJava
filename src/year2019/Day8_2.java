package year2019;

import java.util.ArrayList;
import java.util.List;

public class Day8_2 {
    public String part2(List<String> lines) {
        String input = lines.getFirst();
        List<String> layers = getLayers(input);

        List<Boolean> pixels = new ArrayList<>();
        for (int i = 0; i < 25*6; i++) {
            for (String layer : layers) {
                char pixel = layer.charAt(i);

                if (pixel != '2') {
                    pixels.add(pixel == '0');
                    break;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < pixels.size(); i++) {
            if (i != 0 && i % 25 == 0) {
                result.append("\n");
            }
            result.append(pixels.get(i) ? ' ' : '#');
        }

        return result.toString();
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
