package year2024;

import java.util.LinkedList;
import java.util.List;

public class Day9_1 {
    public String part1(List<String> lines) {
        String input = lines.getFirst();
        LinkedList<Integer> blocks = new LinkedList<>();

        for (int i = 0; i < input.length(); i++) {
            int numberOfBlocks = Character.getNumericValue(input.charAt(i));
            if (i % 2 == 0) {
                int id = i/2;
                for (int j = 0; j < numberOfBlocks; j++) {
                    blocks.add(id);
                }
            } else {
                for (int j = 0; j < numberOfBlocks; j++) {
                    blocks.add(-1);
                }
            }
        }

        long checksum = 0;
        int index = 0;
        while (!blocks.isEmpty()) {
            int id = blocks.pollFirst();

            while (id == -1) {
                id = blocks.pollLast();
            }

            checksum += (long) index * id;
            index++;

        }

        return String.valueOf(checksum);
    }
}
