package year2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8_1 {
    record Point(int x, int y) {}

    public String part1(List<String> lines) {
        Set<Point> visibleTrees = new HashSet<>();

        //looking origin left
        for (int y = 0; y < lines.size(); y++) {
            int maxTreeSize = -1;

            for (int x = 0; x < lines.getFirst().length(); x++) {
                int treeSize = Character.getNumericValue(lines.get(y).charAt(x));

                if (treeSize > maxTreeSize) {
                    visibleTrees.add(new Point(x, y));
                    maxTreeSize = treeSize;
                }
            }
        }

        //looking origin right
        for (int y = 0; y < lines.size(); y++) {
            int maxTreeSize = -1;

            for (int x = lines.getFirst().length() - 1; x >= 0; x--) {
                int treeSize = Character.getNumericValue(lines.get(y).charAt(x));

                if (treeSize > maxTreeSize) {
                    visibleTrees.add(new Point(x, y));
                    maxTreeSize = treeSize;
                }
            }
        }

        //looking origin up
        for (int x = 0; x < lines.getFirst().length(); x++) {
            int maxTreeSize = -1;

            for (int y = 0; y < lines.size(); y++) {
                int treeSize = Character.getNumericValue(lines.get(y).charAt(x));

                if (treeSize > maxTreeSize) {
                    visibleTrees.add(new Point(x, y));
                    maxTreeSize = treeSize;
                }
            }
        }

        //looking origin up
        for (int x = 0; x < lines.getFirst().length(); x++) {
            int maxTreeSize = -1;

            for (int y = lines.size() - 1; y >= 0; y--) {
                int treeSize = Character.getNumericValue(lines.get(y).charAt(x));

                if (treeSize > maxTreeSize) {
                    visibleTrees.add(new Point(x, y));
                    maxTreeSize = treeSize;
                }
            }
        }

        return String.valueOf(visibleTrees.size());
    }
}
