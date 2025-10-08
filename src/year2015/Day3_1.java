package year2015;

import java.util.ArrayList;
import java.util.List;

public class Day3_1 {

    static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Position other)) {
                return false;
            }

            return this.x == other.x && this.y == other.y;
        }
    }

    public String part1(List<String> lines) {
        String moves = lines.getFirst();
        Position actualPosition = new Position(0, 0);
        List<Position> positions = new ArrayList<>();
        positions.add(actualPosition);

        for (char move : moves.toCharArray()) {
            actualPosition = addPositionAfterMove(move, actualPosition, positions);
        }

        return String.valueOf(positions.size());
    }

    private Position addPositionAfterMove(char move, Position position, List<Position> positions) {
        int x = position.x;
        int y = position.y;

        switch (move) {
            case '^' -> x++;
            case 'v' -> x--;
            case '>' -> y++;
            case '<' -> y--;
            default -> throw new RuntimeException("No move detected");
        }

        position = new Position(x, y);
        if (!positions.contains(position)) {
            positions.add(position);
        }

        return position;
    }
}
