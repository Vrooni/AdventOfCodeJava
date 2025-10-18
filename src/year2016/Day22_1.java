package year2016;

import java.util.List;

public class Day22_1 {
    private record Node(int x, int y) {}

    public String part1(List<String> input)  {
        input = input.subList(2, input.size());
        int pairs = 0;

        for (int i = 0; i < input.size(); i++) {
            String[] splitData = input.get(i).split("\\s+");

            int usedA = Integer.parseInt(splitData[2].replace("T", ""));
            int availA = Integer.parseInt(splitData[3].replace("T", ""));

            for (int j = i+1; j < input.size(); j++) {
                splitData = input.get(j).split("\\s+");

                int usedB = Integer.parseInt(splitData[2].replace("T", ""));
                int availB = Integer.parseInt(splitData[3].replace("T", ""));

                if (usedA != 0 && usedA <= availB || usedB != 0 && usedB <= availA) {
                    pairs++;
                }
            }
        }

        return String.valueOf(pairs);
    }
}
