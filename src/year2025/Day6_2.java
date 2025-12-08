package year2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6_2 {
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

    public String part2(List<String> input) {
        input = new ArrayList<>(input);
        String operatorsString = input.removeLast();
        List<Character> operators = new ArrayList<>(operatorsString
                .chars()
                .mapToObj(i -> (char) i)
                .filter(c -> c != ' ')
                .toList());

        int height = input.size();
        int width = input.getFirst().length();

        List<String> rotated = new ArrayList<>();
        for (int i = 0; i < width; i++) {

            StringBuilder numberStr = new StringBuilder();
            for (int j = 0; j < height; j++) {
                numberStr.append(input.get(j).charAt(i));
            }

            rotated.add(numberStr.toString());
        }

        List<Problem> problems = new ArrayList<>();
        Problem currentProblem = new Problem();

        for (String numberStr : rotated) {
            if (numberStr.isBlank()) {
                currentProblem.add = operators.removeFirst() == '+';
                problems.add(currentProblem);

                currentProblem = new Problem();
            } else {
                currentProblem.numbers.add(Integer.parseInt(numberStr.trim()));
            }
        }

        currentProblem.add = operators.removeFirst() == '+';
        problems.add(currentProblem);

        long result = problems.stream().mapToLong(Problem::solve).sum();
        return String.valueOf(result);
    }
}