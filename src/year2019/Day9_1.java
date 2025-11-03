package year2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9_1 {
    private long output = -1;

    public String part1(List<String> lines) {
        String input = lines.getFirst();
        List<Long> program = new ArrayList<>(Arrays.stream(input.split(",")).map(Long::parseLong).toList());

        runProgram(program);
        return String.valueOf(output);
    }

    private void runProgram(List<Long> program) {
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
                case 3 -> i = set(program, relativeBase, i, mode1);
                case 4 -> i = print(program, relativeBase, i, mode1);
                case 5 -> i = jumpIfTrue(program, relativeBase, i, mode1, mode2);
                case 6 -> i = jumpIfFalse(program, relativeBase, i, mode1, mode2);
                case 7 -> i = less(program, relativeBase, i, mode1, mode2, mode3);
                case 8 -> i = equals(program, relativeBase, i, mode1, mode2, mode3);
                case 9 -> { relativeBase = increaseRB(program, relativeBase, i, mode1); i += 2; }
                case 99 -> { return; }
                default -> throw new RuntimeException("unknown opcode");
            }
        }
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

    private int set(List<Long> program, int relativeBase, int i, int mode1) {
        int index = getIndex(program, mode1, i+1, relativeBase);
        setValue(program, index, 1);
        return i + 2;
    }

    private int print(List<Long> program, int relativeBase, int i, int mode1) {
        long result = getParam(program, mode1, i+1, relativeBase);
        if (result != 0) {
            output = result;
        }

        return i + 2;
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
