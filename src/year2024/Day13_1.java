package year2024;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13_1 {
    public String part1(List<String> input) {
        long tokens = 0;

        for (int i = 0; i < input.size()-2; i+= 4) {
            Pattern pattern = Pattern.compile("X\\+(\\d+), Y\\+(\\d+)");
            Matcher matcher = pattern.matcher(input.get(i));
            matcher.find();
            int xA = Integer.parseInt(matcher.group(1));
            int yA = Integer.parseInt(matcher.group(2));

            matcher = pattern.matcher(input.get(i+1));
            matcher.find();
            int xB = Integer.parseInt(matcher.group(1));
            int yB = Integer.parseInt(matcher.group(2));

            pattern = Pattern.compile("X=(\\d+), Y=(\\d+)");
            matcher = pattern.matcher(input.get(i+2));
            matcher.find();

            int xPrize = Integer.parseInt(matcher.group(1));
            int yPrize = Integer.parseInt(matcher.group(2));

            tokens += Math.max(0, getTokens(xA, yA, xB, yB, xPrize, yPrize));
        }

        return String.valueOf(tokens);
    }

    private int getTokens(int xA, int yA, int xB, int yB, int xPrice, int yPrice) {
        int timesA = 1;

        while (timesA < 100) {
            int x = timesA * xA;
            int y = timesA * yA;

            float timesBForX = (float) (xPrice - x)/xB;
            float timesBForY = (float) (yPrice - y)/yB;

            if (timesBForX % 1 == 0 && timesBForX == timesBForY) {
                return (int) (3 * timesA + timesBForX);
            }

            timesA++;
        }

        return -1;
    }
}
