package year2015;

import java.util.ArrayList;
import java.util.List;

public class Day17_2 {

    public String part2(List<String> lines) {
        List<Integer> containers = readInput(lines);
        List<List<Integer>> allPossibleCombinations = getPossibleCombinations(containers, new ArrayList<>());

        int minimumContainers = allPossibleCombinations.stream().mapToInt(List::size).min().getAsInt();
        long count = allPossibleCombinations.stream().filter(container -> container.size() == minimumContainers).count();
        return String.valueOf(count);
    }

    private static List<Integer> readInput(List<String> input) {
        List<Integer> containers = new ArrayList<>();

        for (String line : input) {
            containers.add(Integer.parseInt(line));
        }

        return containers;
    }

    private List<List<Integer>> getPossibleCombinations(List<Integer> containers, List<Integer> thisCombination) {
        List<List<Integer>> possibleCombination = new ArrayList<>();
        int filledLiters = thisCombination.stream().mapToInt(c -> c).sum();

        if (filledLiters == 150) {
            possibleCombination.add(new ArrayList<>(thisCombination));
            return possibleCombination;
        }

        List<Integer> copyContainers = new ArrayList<>(containers);
        for (int container : containers) {
            copyContainers.remove((Integer) container);

            if (filledLiters + container > 150) {
                continue;
            }

            thisCombination.add(container);
            possibleCombination.addAll(getPossibleCombinations(copyContainers, thisCombination));
            thisCombination.removeLast();
        }

        return possibleCombination;
    }
}
