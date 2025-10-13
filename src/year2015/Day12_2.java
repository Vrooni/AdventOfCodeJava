package year2015;

import java.util.ArrayList;
import java.util.List;

public class Day12_2 {

    public String part2(List<String> lines) {
        String input = lines.getFirst();
        input = removeRedObjects(input);
        List<String> numbers = getNumbersFromString(input);

        int sum = 0;
        for (String number : numbers) {
            sum += Integer.parseInt(number);
        }

        return String.valueOf(sum);
    }

    private String removeRedObjects(String input) {
        while (true) {
            int indexOfRed = input.indexOf("red");
            if (indexOfRed == -1) {
                return input;
            }

            int startIndex = findSartIndex(indexOfRed, input);
            if (startIndex == -1) {
                throw new RuntimeException("No bracket found");
            }

            // ignore arrays
            if (input.charAt(startIndex) == '[') {
                input = input.replaceFirst("red", "");
                continue;
            }

            int endIndex = findEndIndex(startIndex, input);
            if (endIndex == -1) {
                throw new RuntimeException("No bracket found");
            }

            input = input.substring(0, startIndex) + input.substring(endIndex + 1);
        }
    }

    private int findSartIndex(int indexOfRed, String input) {
        int openObjects = 0;
        int openArrays = 0;

        for (int i = indexOfRed; i >= 0; i--) {
            switch (input.charAt(i)) {
                case ']' -> openArrays++;
                case '}' -> openObjects++;
                case '[' -> {
                    if (openArrays-- == 0 && openObjects == 0) {
                        return i;
                    }
                }
                case '{' -> {
                    if (openObjects-- == 0 && openArrays == 0) {
                        return i;
                    }
                }
            }
        }

        return -1;
    }

    private int findEndIndex(int startIndex, String input) {
        int openObjects = 0;
        int openArrays = 0;

        for (int i = startIndex+1; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case '[' -> openArrays++;
                case '{' -> openObjects++;
                case ']' -> openArrays--;
                case '}' -> {
                    if (openObjects-- == 0 && openArrays == 0) {
                        return i;
                    }
                }
            }
        }

        return -1;
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
