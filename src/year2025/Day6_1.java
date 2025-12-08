package year2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6_1 {
    private static class Problem {
        List<Integer> numbers = new ArrayList<>();
        boolean add;

        public long solve() {
            if (add) {
                return numbers.stream().mapToLong(number -> number).sum();
            }

            long result = 1;
            for (Integer number : numbers) {
                result *= number;
            }

            return result;
        }
    }

    public String part1(List<String> input) {
        List<Problem> problems = new ArrayList<>();

        for (String line : input) {
            List<String> splitLine = Arrays.stream(line.split(" ")).filter(n -> !n.isBlank()).toList();

            for (int i = 0; i < splitLine.size(); i++) {
                if (problems.size() <= i) {
                    problems.add(new Problem());
                }

                if (isNumeric(splitLine.get(i))) {
                    problems.get(i).numbers.add(Integer.parseInt(splitLine.get(i)));
                } else {
                    problems.get(i).add = splitLine.get(i).equals("+");
                }
            }
        }

        long result = problems.stream().mapToLong(Problem::solve).sum();
        return String.valueOf(result);
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}