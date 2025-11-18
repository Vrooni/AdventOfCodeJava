package year2022;

import java.util.ArrayList;
import java.util.List;

public class Day25_1 {
    public String part1(List<String> lines) {
        long result = getResult(lines);
        return getSnafuResult(result);
    }

    private long getResult(List<String> lines) {
        long result = 0;

        for (String line : lines) {
            long decimal = 0;
            int power = line.length()-1;

            for (char c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    decimal += (long) (Character.getNumericValue(c) * Math.pow(5, power));
                } else {
                    if (c == '-') {
                        decimal += (long) (-1 * Math.pow(5, power));
                    } else if (c == '=') {
                        decimal += (long) (-2 * Math.pow(5, power));
                    }
                }

                power--;
            }

            result += decimal;
        }

        return result;
    }

    private String getSnafuResult(long decimal) {
        int power = getStartPower(decimal);
        List<Integer> nativeSnafu = getNativeResult(decimal, power);

        return convertToSnafu(nativeSnafu);
    }

    private int getStartPower(long decimal) {
        int power = 0;

        while (!(Math.pow(5, power + 1) > decimal) || !(decimal >= Math.pow(5, power))) {
            power++;
        }

        return power;
    }

    private List<Integer> getNativeResult(long decimal, int power) {
        List<Integer> nativeSnafu = new ArrayList<>();

        for (int i = 0; i <= power; i++) {
            nativeSnafu.add(0);
        }

        for (int i = power; i >= 0; i--) {
            int temp = (int) (decimal / Math.pow(5, i));
            decimal -= (long) (temp * Math.pow(5, i));

            nativeSnafu.set(i, temp);
        }

        for (int i = 0; i <= power; i++) {
            if (nativeSnafu.get(i) > 2) {

                if (nativeSnafu.size() == i + 1) {
                    nativeSnafu.add(1);
                } else {
                    nativeSnafu.set(i+1, nativeSnafu.get(i+1)+1);
                }

                nativeSnafu.set(i, nativeSnafu.get(i)-5);
            }
        }

        return nativeSnafu;
    }

    private String convertToSnafu(List<Integer> nativeSnafu) {
        StringBuilder builder = new StringBuilder();

        for (int i = nativeSnafu.size()-1; i >= 0; i--) {
            if (nativeSnafu.get(i) >= 0) {
                builder.append(nativeSnafu.get(i));
            } else if (nativeSnafu.get(i) == -1) {
                builder.append("-");
            } else if (nativeSnafu.get(i) == -2) {
                builder.append("=");
            }
        }

        return builder.toString();
    }
}
