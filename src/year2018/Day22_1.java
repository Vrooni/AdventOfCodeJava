package year2018;


import java.util.*;

public class Day22_1 {
    public record Position(int x, int y) {}
    private record Square(int erosionLevel, int type) {}

    public String part1(List<String> input) {
        String[] splitTarget = input.get(1).replace("target: ", "").split(",");

        int depth = Integer.parseInt(input.getFirst().replace("depth: ", ""));
        Position target = new Position(Integer.parseInt(splitTarget[0]), Integer.parseInt(splitTarget[1]));

        Square[][] squares = getSquares(depth, target);
        return String.valueOf(Arrays.stream(squares)
                .flatMapToInt(line -> Arrays
                        .stream(line)
                        .mapToInt(square -> square.type == -1 ? 0 : square.type))
                .sum());
    }

    private Square[][] getSquares(int depth, Position target) {
        Square[][] squares = new Square[depth][depth];
        for (Square[] row: squares) {
            Arrays.fill(row, new Square(-1, -1));
        }

        int erosionLevel = depth % 20183;
        squares[0][0] = new Square(erosionLevel, erosionLevel % 3);

        for (int x = 1; x <= target.x(); x++) {
            erosionLevel = (x * 16807 + depth) % 20183;
            squares[0][x] = new Square(erosionLevel, erosionLevel % 3);
        }

        for (int y = 1; y <= target.y(); y++) {
            erosionLevel = (y * 48271 + depth) % 20183;
            squares[y][0] = new Square(erosionLevel, erosionLevel % 3);
        }

        for (int y = 1; y <= target.y(); y++) {
            for (int x = 1; x <= target.x(); x++) {
                long geologicIndex = (long) squares[y][x-1].erosionLevel * squares[y-1][x].erosionLevel;
                erosionLevel =  (int) ((geologicIndex + depth) % 20183);
                squares[y][x] = new Square(erosionLevel, erosionLevel % 3);
            }
        }

        erosionLevel = depth % 20183;
        squares[target.y()][target.x()] = new Square(erosionLevel, erosionLevel % 3);

        return squares;
    }
}
