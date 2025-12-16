package year2024;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21_2 {
    public record Position(int x, int y) {}
    private record Move(char start, char end) {}
    private record MoveForRobot(Move move, int robot) {}

    private final Map<Character, Position> numPad = getNumericButtons();
    private final Map<Character, Position> dirPad = getDirectionButtons();

    private final Map<Move, String> numPadMoves = getNumPadMoves();
    private final Map<Move, String> dirPadMoves = getDirPadMoves();

    private final Map<MoveForRobot, Long> codeLengths = new HashMap<>();

    public String part2(List<String> input) {
        long complexities = 0;

        for (String code : input) {
            long numericCode = Integer.parseInt(code.replace("A", ""));
            code = getNextCode(code);

            long length = 0;
            for (int i = 0; i < code.length(); i++) {
                char buttonA = i == 0 ? 'A' : code.charAt(i-1);
                char buttonB = code.charAt(i);
                length += getMoveLength(new Move(buttonA, buttonB), 1);
            }

            complexities += numericCode * length;
        }

        return String.valueOf(complexities);
    }

    private String getNextCode(String code) {
        StringBuilder newCode = new StringBuilder();

        char currentButton = 'A';
        for (char button : code.toCharArray()) {
            Move move = new Move(currentButton, button);
            newCode.append(numPadMoves.get(move));
            currentButton = button;
        }

        return newCode.toString();
    }

    private long getMoveLength(Move move, int robot) {
        if (robot == 25) {
            return dirPadMoves.get(move).length();
        }

        MoveForRobot moveForRobot = new MoveForRobot(move, robot);
        Long length = codeLengths.get(moveForRobot);

        if (length != null) {
            return length;
        }

        length = 0L;
        String nextCode = dirPadMoves.get(move);
        for (int i = 0; i < nextCode.length(); i++) {
            char buttonA = i == 0 ? 'A' : nextCode.charAt(i-1);
            char buttonB = nextCode.charAt(i);
            length += getMoveLength(new Move(buttonA, buttonB), robot + 1);
        }

        codeLengths.put(moveForRobot, length);
        return length;
    }

    private Map<Character, Position> getNumericButtons() {
        Map<Character, Position> buttons = new HashMap<>();

        buttons.put('7', new Position(0, 0));
        buttons.put('8', new Position(1, 0));
        buttons.put('9', new Position(2, 0));
        buttons.put('4', new Position(0, 1));
        buttons.put('5', new Position(1, 1));
        buttons.put('6', new Position(2, 1));
        buttons.put('1', new Position(0, 2));
        buttons.put('2', new Position(1, 2));
        buttons.put('3', new Position(2, 2));
        buttons.put('0', new Position(1, 3));
        buttons.put('A', new Position(2, 3));

        return buttons;
    }

    private Map<Character, Position> getDirectionButtons() {
        Map<Character, Position> buttons = new HashMap<>();

        buttons.put('^', new Position(1, 0));
        buttons.put('A', new Position(2, 0));
        buttons.put('<', new Position(0, 1));
        buttons.put('v', new Position(1, 1));
        buttons.put('>', new Position(2, 1));

        return buttons;
    }

    private Map<Move, String> getNumPadMoves() {
        return getPadMoves(true);
    }

    private Map<Move, String> getDirPadMoves() {
        return getPadMoves(false);
    }

    private Map<Move, String> getPadMoves(boolean useNumPad) {
        Map<Character, Position> pad = useNumPad ? numPad : dirPad;
        Map<Move, String> moves = new HashMap<>();

        for (Map.Entry<Character, Position> start : pad.entrySet()) {
            for (Map.Entry<Character, Position> end : pad.entrySet()) {
                Move move = new Move(start.getKey(), end.getKey());
                addMoves(moves, move, start.getValue(), end.getValue(), useNumPad);
            }
        }

        return moves;
    }

    /**
     * general:
     * up/left       --> left than up
     * ------------------------------------------------
     * <^A -> v<<A>^A>A -> v<A<AA>>^AvA<^A>AvA^A
     * ^<A -> <Av<A>>^A -> v<<A>>^Av<A<A>>^AvAA<^A>A
     * ------------------------------------------------
     * up/right      --> doesn't matter
     * ------------------------------------------------
     * >^A -> vA<A -> v<Av^Av<<A>^^A
     * ^>A -> <AvA -> v<<A>>^Av<Av^A
     * ------------------------------------------------
     * down/left     --> left than down
     * ------------------------------------------------
     * <vA -> v<<A>A^>A -> v<A<AA>>^AvA^A<Av>A^A
     * v<A -> v<A<A>>^A -> v<A<A>>^Av<<A>>^AvAA^<A>A
     * ------------------------------------------------
     * down/right    --> down than right
     * ------------------------------------------------
     * >vA -> vA<A  -> v<A>^Av<<A>>^A
     * v>A -> <vA>A -> v<<A>Av^AvA^A
     * ------------------------------------------------
     * <p>
     * special case:
     * up/left       (e.g. 0 -> 1)   --> up than left
     * down/right    (e.g. 1 -> 0)   --> right than down
     * up/right      (e.g. < -> ^)   --> right than up
     * down/left     (e.g. ^ -> <)   --> down than left
     */
    private void addMoves(Map<Move, String> moves, Move move, Position start, Position end, boolean useNumPad) {
        int distanceX = end.x() - start.x();
        int distanceY = end.y() - start.y();

        String upMoves = "^".repeat(Math.abs(Math.min(0, distanceY)));
        String leftMoves = "<".repeat(Math.abs(Math.min(0, distanceX)));
        String downMoves = "v".repeat(Math.max(0, distanceY));
        String rightMoves = ">".repeat(Math.max(0, distanceX));

        if (isSpecialCase(start, end, useNumPad)) {
            //special case
            moves.put(move, rightMoves
                    + downMoves
                    + upMoves
                    + leftMoves
                    + "A"
            );
        } else {
            //general
            moves.put(move, leftMoves
                    + downMoves
                    + upMoves
                    + rightMoves
                    + "A"
            );
        }
    }

    private boolean isSpecialCase(Position start, Position end, boolean useNumPad) {
        return useNumPad && start.y() == 3 && end.x() == 0
                || useNumPad && start.x() == 0 && end.y() == 3
                || !useNumPad && start.y() == 0 && end.x() == 0
                || !useNumPad && start.x() == 0 && end.y() == 0;
    }
}
