package year2018;

import java.util.*;

public class Day22_2 {
    public record Position(int x, int y) {}
    private record Square(int erosionLevel, int type) {}
    private record Equip(boolean torch, boolean gear) {}

    private record Step(int x, int y, boolean torch, boolean gear, int minutes) implements Comparable<Step> {
        public List<Step> getNeighbours(Square[][] squares) {
            List<Step> neighbours = new ArrayList<>();

            //change equip
            List<Equip> equips = getPossibleEquips(squares, this.x, this.y);
            for (Equip equip : equips) {
                if (!(this.torch == equip.torch && this.gear == equip.gear)) {
                    neighbours.add(new Step(this.x, this.y, equip.torch, equip.gear, this.minutes + 7));
                }
            }

            //UP
            if (this.y > 0 && getPossibleEquips(squares, this.x, this.y - 1).contains(new Equip(this.torch, this.gear))) {
                neighbours.add(new Step(this.x, this.y - 1, this.torch, this.gear, this.minutes + 1));
            }

            //DOWN
            if (squares[y + 1][x].type == -1) {
                addSquare(squares, this.x, this.y + 1);
            }
            if (getPossibleEquips(squares, this.x, this.y + 1).contains(new Equip(this.torch, this.gear))) {
                neighbours.add(new Step(this.x, this.y + 1, this.torch, this.gear, this.minutes + 1));
            }

            //LEFT
            if (this.x > 0 && getPossibleEquips(squares, this.x - 1, this.y).contains(new Equip(this.torch, this.gear))) {
                neighbours.add(new Step(this.x - 1, this.y, this.torch, this.gear, this.minutes + 1));
            }

            //RIGHT
            if (squares[y][x + 1].type == -1) {
                addSquare(squares, this.x + 1, this.y);
            }
            if (getPossibleEquips(squares, this.x + 1, this.y).contains(new Equip(this.torch, this.gear))) {
                neighbours.add(new Step(this.x + 1, this.y, this.torch, this.gear, this.minutes + 1));
            }

            return neighbours;
        }

        private void addSquare(Square[][] squares, int x, int y) {
            int depth = squares.length;

            if (x == 0) {
                int erosionLevel = (y * 48271 + depth) % 20183;
                squares[y][x] = new Square(erosionLevel, erosionLevel % 3);
                return;
            }

            if (y == 0) {
                int erosionLevel = (x * 16807 + depth) % 20183;
                squares[y][x] = new Square(erosionLevel, erosionLevel % 3);
                return;
            }

            if (squares[y-1][x].erosionLevel == -1) {
                addSquare(squares, x, y-1);
            }

            if (squares[y][x-1].erosionLevel == -1) {
                addSquare(squares, x-1, y);
            }

            long geologicIndex = (long) squares[y][x-1].erosionLevel * squares[y-1][x].erosionLevel;
            int erosionLevel = (int) ((geologicIndex + depth) % 20183);
            squares[y][x] = new Square(erosionLevel, erosionLevel % 3);
        }

        private List<Equip> getPossibleEquips(Square[][] squares, int x, int y) {
            List<Equip> possibleEquips = new ArrayList<>();

            switch (squares[y][x].type) {
                case 0 -> {
                    possibleEquips.add(new Equip(true, false));
                    possibleEquips.add(new Equip(false, true));
                }
                case 1 -> {
                    possibleEquips.add(new Equip(false, true));
                    possibleEquips.add(new Equip(false, false));
                }
                case 2 -> {
                    possibleEquips.add(new Equip(true, false));
                    possibleEquips.add(new Equip(false, false));
                }
            }

            return possibleEquips;
        }

        @Override
        public int compareTo(Step o) {
            return Integer.compare(this.minutes, o.minutes);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Step step)) return false;
            return x == step.x && y == step.y && torch == step.torch && gear == step.gear;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, torch, gear);
        }
    }

    public String part2(List<String> input) {
        String[] splitTarget = input.get(1).replace("target: ", "").split(",");
        Position target = new Position(Integer.parseInt(splitTarget[0]), Integer.parseInt(splitTarget[1]));

        int depth = Integer.parseInt(input.getFirst().replace("depth: ", ""));
        Square[][] squares = getSquares(depth, target);

        Queue<Step> queue = new PriorityQueue<>();
        queue.add(new Step(0, 0, true, false, 0));

        HashMap<Step, Integer> distances = new HashMap<>();
        distances.put(new Step(0, 0, true, false, 0), 0);

        while (!queue.isEmpty()) {
            Step current = queue.poll();

            if (current.x == target.x() && current.y == target.y() && current.torch) {
                return String.valueOf(current.minutes);
            }

            for (Step neighbour : current.getNeighbours(squares)) {
                if (neighbour.minutes < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    queue.add(neighbour);
                    distances.put(neighbour, neighbour.minutes);
                }
            }
        }

        return "-1";
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
