package year2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18_2 {
    private final char OPEN = '.';
    private final char TREE = '|';
    private final char LUMBERYARD = '#';

    private record Map(char[][] map) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Map(char[][] map1))) return false;
            return Arrays.deepEquals(map, map1);
        }
    }

    public String part2(List<String> input) {
        char[][] map = readInput(input);
        List<Map> history = new ArrayList<>();

        List<char[][]> cycle;
        int startCycle;

        while (true) {
            history.add(new Map(map));
            map = changeMap(map);

            startCycle = history.indexOf(new Map(map));
            if (startCycle >= 0) {
                cycle = history.subList(startCycle, history.size()).stream().map(Map::map).toList();
                break;
            }
        }

        return String.valueOf(getResource(cycle.get((1000000000 - startCycle) % cycle.size())));
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
