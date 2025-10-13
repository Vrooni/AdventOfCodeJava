package year2015;

import java.util.ArrayList;
import java.util.List;

public class Day12_1 {

    public String part1(List<String> lines) {
        List<String> numbers = getNumbersFromString(lines.getFirst());

        int sum = 0;
        for (String number : numbers) {
            sum += Integer.parseInt(number);
        }

        return String.valueOf(sum);
    }

    private List<String> getNumbersFromString(String input) {
        List<String> numbers = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            StringBuilder number = new StringBuilder();
            boolean startOfNumber = false;

            while (true) {
                if (i >= input.length()) {
                    break;
                }

                char nextChar = input.charAt(i);

                if (startOfNumber) {
                    if (nextChar == '-' || Character.isDigit(nextChar)) {
                        number.append(nextChar);
                    } else {
                        break;
                    }
                } else {
                    if (nextChar == '-' || Character.isDigit(nextChar)) {
                        number.append(nextChar);
                        startOfNumber = true;
                    }
                }

                i++;
            }

            if (startOfNumber) {
                numbers.add(number.toString());
            }
        }

        return numbers;
    }
}
