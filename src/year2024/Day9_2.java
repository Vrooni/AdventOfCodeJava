package year2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day9_2 {
    private static class File {
        int id;
        int index;
        int size;

        public File(int id, int index, int size) {
            this.id = id;
            this.index = index;
            this.size = size;
        }
    }

    public String part2(List<String> lines) {
        String input = lines.getFirst();

        List<Integer> blocks = new ArrayList<>();
        List<File> files = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            int numberOfBlocks = Character.getNumericValue(input.charAt(i));
            if (i % 2 == 0) {
                int id = i/2;
                files.add(new File(id, blocks.size(), numberOfBlocks));
                for (int j = 0; j < numberOfBlocks; j++) {
                    blocks.add(id);
                }
            } else {
                for (int j = 0; j < numberOfBlocks; j++) {
                    blocks.add(-1);
                }
            }
        }

        Collections.reverse(files);
        for (File file : files) {
            List<Integer> space = Collections.nCopies(file.size, -1);
            int index = Collections.indexOfSubList(blocks, space);
            if (index == -1 || index >= file.index) {
                continue;
            }

            for (int i = index; i < index + file.size; i++) {
                blocks.set(i, file.id);
            }

            for (int i = file.index; i < file.index + file.size; i++) {
                blocks.set(i, -1);
            }
        }

        long checksum = 0;
        for (int i = 0; i < blocks.size(); i++) {
            int block = blocks.get(i);
            checksum += i * (block == -1 ? 0 : block);
        }

        return String.valueOf(checksum);
    }
}
