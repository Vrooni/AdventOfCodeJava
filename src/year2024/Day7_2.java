package year2024;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Day7_2 {
    public String part2(List<String> input) {
        BigInteger resultTrueEquations = BigInteger.ZERO;

        for (String equation : input) {
            long result = Long.parseLong(equation.split(": ")[0]);
            List<Long> numbers = Arrays.stream(equation.split(": ")[1]
                            .split(" "))
                    .map(Long::parseLong)
                    .toList();

            if (isTrue(result, numbers, new int[numbers.size()-1], 0)) {
                resultTrueEquations = resultTrueEquations.add(BigInteger.valueOf(result));
            }
        }

        return String.valueOf(resultTrueEquations);
    }

    private boolean isTrue(long result, List<Long> numbers, int[] operations, int depth) {
        for (int i = 0; i < 3; i++) {
            operations[depth] = i;

            if (depth == numbers.size()-2) {
                if (calculate(numbers, operations) == result) {
                    return true;
                }
            } else {
                boolean isTrue = isTrue(result, numbers, operations, depth+1);
                if (isTrue) return true;
            }
        }

        return false;
    }

    private long calculate(List<Long> numbers, int[] operations) {
        long testResult = numbers.getFirst();

        for (int i = 1; i < numbers.size(); i++) {
            switch (operations[i-1]) {
                case 0 -> testResult += numbers.get(i);
                case 1 -> testResult *= numbers.get(i);
                default -> testResult = Long.parseLong(testResult + numbers.get(i).toString());
            }
        }

        return testResult;
    }
}
