package year2018;

import java.util.Arrays;
import java.util.List;

public class Day18_1 {
    private final char OPEN = '.';
    private final char TREE = '|';
    private final char LUMBERYARD = '#';

    public String part1(List<String> input) {
        char[][] map = readInput(input);
        for (int t = 0; t < 10; t++) {
            map = changeMap(map);
        }

        return String.valueOf(getResource(map));
    }

    private char[][] readInput(List<String> input) {
        char[][] map = new char[50][50];

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                map[i][j] = input.get(i).charAt(j);
            }
        }

        return map;
    }

    private char[][] changeMap(char[][] map) {
        char[][] newMap = Arrays.stream(map).map(char[]::clone).toArray(char[][]::new);

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                newMap[i][j] = getNext(map, j, i);
            }
        }

        return newMap;
    }

    private char getNext(char[][] map, int x, int y) {
        int treeAcres = 0;
        int lumberyardAcres = 0;

        for (int i = y-1; i <= y+1; i++) {
            for (int j = x-1; j <= x+1; j++) {
                if (i == y && j == x || i < 0 || i >= 50 || j < 0 || j >= 50) {
                    continue;
                }

                switch (map[i][j]) {
                    case TREE -> treeAcres++;
                    case LUMBERYARD -> lumberyardAcres++;
                }
            }
        }

        return switch (map[y][x]) {
            case OPEN -> treeAcres >= 3 ? TREE : OPEN;
            case TREE -> lumberyardAcres >= 3 ? LUMBERYARD : TREE;
            case LUMBERYARD -> lumberyardAcres > 0 && treeAcres > 0 ? LUMBERYARD : OPEN;
            default -> throw new RuntimeException("unknown acre");
        };
    }

    private int getResource(char[][] map) {
        int treeAcres = 0;
        int lumberyardAcres = 0;

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                switch (map[i][j]) {
                    case TREE -> treeAcres++;
                    case LUMBERYARD -> lumberyardAcres++;
                }
            }
        }

        return treeAcres * lumberyardAcres;
    }
}
