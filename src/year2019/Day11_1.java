package year2019;

import java.util.*;

public class Day11_1 {

    public record Position(int x, int y) {}

    public enum Direction {
        UP, RIGHT, DOWN, LEFT;

        public Direction next() {
            return values()[(this.ordinal() + 1) % values().length];
        }

        public Direction previous() {
            return values()[(this.ordinal() + values().length - 1) % values().length];
        }
    }

    public String part1(List<String> lines) {
        String input = lines.getFirst();
        List<Long> program = new ArrayList<>(Arrays.stream(input.split(",")).map(Long::parseLong).toList());

        return String.valueOf(runProgram(program).size());
    }

    private Map<Position, Boolean> runProgram(List<Long> program) {
        int input = 0;
        int output = 0;

        boolean handleOutput = false;
        boolean firstOutput = true;

        int x = 0;
        int y = 0;
        Direction direction = Direction.UP;
        Map<Position, Boolean> panels = new HashMap<>();

        int i = 0;
        int relativeBase = 0;

        while (i >= 0) {
            String mode = pad(Long.toString(program.get(i)));

            int opcode = Integer.parseInt(mode.substring(3));
            int mode1 = Character.getNumericValue(mode.charAt(2));
            int mode2 = Character.getNumericValue(mode.charAt(1));
            int mode3 = Character.getNumericValue(mode.charAt(0));

            switch (opcode) {
                case 1 -> i = add(program, relativeBase, i, mode1, mode2, mode3);
                case 2 -> i = mul(program, relativeBase, i, mode1, mode2, mode3);
                case 3 -> i = set(program, relativeBase, i, mode1, input);
                case 4 -> {
                    handleOutput = true;
                    output = print(program, relativeBase, i, mode1);
                    i += 2;
                }
                case 5 -> i = jumpIfTrue(program, relativeBase, i, mode1, mode2);
                case 6 -> i = jumpIfFalse(program, relativeBase, i, mode1, mode2);
                case 7 -> i = less(program, relativeBase, i, mode1, mode2, mode3);
                case 8 -> i = equals(program, relativeBase, i, mode1, mode2, mode3);
                case 9 -> { relativeBase = increaseRB(program, relativeBase, i, mode1); i += 2; }
                case 99 -> { return panels; }
                default -> throw new RuntimeException("unknown opcode");
            }

            if (handleOutput) {
                if (firstOutput) {
                    panels.put(new Position(x, y), output == 1);
                } else {
                    direction = getNextDirection(output, direction);
                    Position position = move(direction, x, y);
                    x = position.x();
                    y = position.y();

                    input = getNextInput(panels, position);
                }

                handleOutput = false;
                firstOutput = !firstOutput;
            }
        }

        return panels;
    }

    private Direction getNextDirection(int output, Direction direction) {
        return output == 0 ? direction.previous() : direction.next();
    }

    private int getNextInput(Map<Position, Boolean> panels, Position position) {
        return panels.getOrDefault(position, false) ? 1 : 0;
    }

    private Position move(Direction direction, int x, int y) {
        switch (direction) {
            case UP -> y--;
            case DOWN -> y++;
            case LEFT -> x--;
            case RIGHT -> x++;
        }

        return new Position(x, y);
    }

    private int add(List<Long> program, int relativeBase, int i, int mode1, int mode2, int mode3) {
        long param1 = getParam(program, mode1, i+1, relativeBase);
        long param2 = getParam(program, mode2, i+2, relativeBase);
        int index = getIndex(program, mode3, i+3, relativeBase);

        setValue(program, index, param1 + param2);
        return i+4;
    }

    private int mul(List<Long> program, int relativeBase, int i, int mode1, int mode2, int mode3) {
        long param1 = getParam(program, mode1, i+1, relativeBase);
        long param2 = getParam(program, mode2, i+2, relativeBase);
        int index = getIndex(program, mode3, i+3, relativeBase);

        setValue(program, index, param1 * param2);
        return i + 4;
    }

    private int set(List<Long> program, int relativeBase, int i, int mode1, long input) {
        int index = getIndex(program, mode1, i+1, relativeBase);
        setValue(program, index, input);
        return i + 2;
    }

    private int print(List<Long> program, int relativeBase, int i, int mode1) {
        return (int) getParam(program, mode1, i+1, relativeBase);
    }

    private int jumpIfTrue(List<Long> program, int relativeBase, int i, int mode1, int mode2) {
        long param1 = getParam(program, mode1, i+1, relativeBase);
        long param2 = getParam(program, mode2, i+2, relativeBase);

        if (param1 != 0) {
            return (int) param2;
        } else {
            return i + 3;
        }
    }

    private int jumpIfFalse(List<Long> program, int relativeBase, int i, int mode1, int mode2) {
        long param1 = getParam(program, mode1, i+1, relativeBase);
        long param2 = getParam(program, mode2, i+2, relativeBase);
        if (param1 == 0) {
            return (int) param2;
        } else {
            return i + 3;
        }
    }

    private int less(List<Long> program, int relativeBase, int i, int mode1, int mode2, int mode3) {
        long param1 = getParam(program, mode1, i+1, relativeBase);
        long param2 = getParam(program, mode2, i+2, relativeBase);
        int index = getIndex(program, mode3, i+3, relativeBase);

        setValue(program, index, param1 < param2 ? 1 : 0);
        return i + 4;
    }

    private int equals(List<Long> program, int relativeBase, int i, int mode1, int mode2, int mode3) {
        long param1 = getParam(program, mode1, i+1, relativeBase);
        long param2 = getParam(program, mode2, i+2, relativeBase);
        int index = getIndex(program, mode3, i+3, relativeBase);

        setValue(program, index, param1 == param2 ? 1 : 0);
        return i + 4;
    }

    private int increaseRB(List<Long> program, int relativeBase, int i, int mode1) {
        long param1 = getParam(program, mode1, i+1, relativeBase);
        return (int) (relativeBase + param1);
    }

    private long getValue(List<Long> program, long i) {
        if (i >= program.size()) {
            return 0;
        }

        return program.get((int) i);
    }

    private void setValue(List<Long> program, long i, long value) {
        for (int j = program.size(); j <= i; j++) {
            program.add(0L);
        }

        program.set((int) i, value);
    }

    private long getParam(List<Long> program, int mode, int i, long relativeBase) {
        long value = getValue(program, i);

        return switch (mode) {
            case 0 -> getValue(program, value);
            case 1 -> value;
            case 2 -> getValue(program, relativeBase + value);
            default -> throw new RuntimeException("unknown mode");
        };
    }

    private int getIndex(List<Long> program, int mode, int i, long relativeBase) {
        int value = (int) getValue(program, i);

        return switch (mode) {
            case 0 -> value;
            case 2 -> (int) relativeBase + value;
            default -> throw new RuntimeException("unknown mode");
        };
    }

    private String pad(String s) {
        StringBuilder sBuilder = new StringBuilder(s);

        while (sBuilder.length() < 5) {
            sBuilder.insert(0, "0");
        }

        return sBuilder.toString();
    }
}
