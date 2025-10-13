package year2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day24_1 {

    public String part1(List<String> lines) {
        List<Integer> presents = lines.stream().map(Integer::parseInt).toList();
        presents = reverse(new ArrayList<>(presents));

        return String.valueOf(getBestQE(presents, sum(presents) / 3));
    }

    private long getBestQE(List<Integer> presents, int size) {
        long qe = Long.MAX_VALUE;

        for (int i = 1; i < presents.size(); i++) {
            List<List<Integer>> combinations = new ArrayList<>();
            getCombinations(presents, combinations, new Integer[i], 0, presents.size()-1, 0, i);
            combinations = combinations.stream().filter(combination -> sum(combination) == size).toList();

            if (!combinations.isEmpty()) {
                for (List<Integer> combination : combinations) {
                    qe = Math.min(qe, mul(combination));
                }
                break;
            }
        }

        return qe;
    }

    private void getCombinations(List<Integer> presents, List<List<Integer>> combinations, Integer[] combination, int start, int end, int index, int size) {
        if (index == size) {
            combinations.add(Arrays.asList(Arrays.copyOf(combination, combination.length)));
            return;
        }

        for (int i = start; i <= end && end - i + 1 >= size - index; i++) {
            combination[index] = presents.get(i);
            getCombinations(presents, combinations, combination, i+1, end, index+1, size);
        }
    }

    private int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(a -> a).sum();
    }

    public long mul(List<Integer> numbers) {
        long x = 1;
        for (int number : numbers) {
            x *= number;
        }

        return x;
    }

    private List<Integer> reverse(List<Integer> list) {
        List<Integer> shallowCopy = list.subList(0, list.size());
        Collections.reverse(shallowCopy);

        return shallowCopy;
    }
}
