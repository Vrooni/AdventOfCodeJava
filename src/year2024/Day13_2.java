package year2024;

import java.util.List;

public class Day13_2 {
    public String part2(List<String> input) {
        long tokens = 0;

        for (int i = 0; i < input.size()-2; i+= 4) {
            String[] buttonA = input.get(i)
                    .replace("Button A: ", "")
                    .split(", ");
            String[] buttonB = input.get(i+1)
                    .replace("Button B: ", "")
                    .split(", ");
            String[] price = input.get(i+2)
                    .replace("Prize: ", "")
                    .split(", ");

            int xA = Integer.parseInt(buttonA[0].replace("X+", ""));
            int yA = Integer.parseInt(buttonA[1].replace("Y+", ""));
            int xB = Integer.parseInt(buttonB[0].replace("X+", ""));
            int yB = Integer.parseInt(buttonB[1].replace("Y+", ""));
            long xPrize = Long.parseLong(price[0].replace("X=", "")) + 10000000000000L;
            long yPrize = Long.parseLong(price[1].replace("Y=", "")) + 10000000000000L;

            tokens += Math.max(0, getTokens(xA, yA, xB, yB, xPrize, yPrize));
        }

        return String.valueOf(tokens);
    }

    private long getTokens(double xA, double yA, double xB, double yB, double xPrice, double yPrice) {
        //(94) A    +   (22) B  =   (8400)
        //(34)          (67)        (5400)

        //94A + 22B = 8400
        //34A + 67B = 5400

        //94x + 22y = 8400
        //34x + 67y = 5400

        //y = 8400/22 - 94/22 x
        //y = 5400/67 - 34/67 x

        //8400/22 - 94/22 x = 5400/67 - 34/67 x
        //-94/22 x + 34/67 x = 5400/67 - 8400/22
        //(-94/22 + 34/67) x = 5400/67 - 8400/22
        //x = (5400/67 - 8400/22) / (-94/22 + 34/67)

        long timesA = Math.round((yPrice/yB - xPrice/xB) / (-xA/xB + yA/yB));
        long timesB = Math.round(xPrice/xB - xA/xB * timesA);

        return xPrice == timesA * xA + timesB * xB && yPrice == timesA * yA + timesB * yB
                ? timesA * 3 + timesB
                : -1;
    }
}
