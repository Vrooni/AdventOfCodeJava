package year2017;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6_2 {
    public String part2(List<String> lines) {
        //Part one
        String input = lines.getFirst();
        String[] splitInput = input.trim().split("\t");
        List<Integer> banks = new ArrayList<>(Arrays.stream(splitInput).map(Integer::parseInt).toList());

        List<List<Integer>> seen = new ArrayList<>();

        while (!seen.contains(banks)) {
            seen.add(new ArrayList<>(banks));

            int max = banks.stream().max(Integer::compareTo).orElse(0);
            int indexMax = banks.indexOf(max);

            banks.set(indexMax, 0);

            for (int i = 1; i <= max; i++) {
                int index = (indexMax + i) % banks.size();
                banks.set(index, banks.get(index)+1);
            }
        }

        return String.valueOf(seen.size() - seen.indexOf(banks));
    }
}
