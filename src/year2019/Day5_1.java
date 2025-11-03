package year2019;

import java.util.Arrays;
import java.util.List;

public class Day5_1 {
    public String part1(List<String> lines) {
        String input = lines.getFirst();
        int[] program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        return String.valueOf(runProgram(program));
    }

    private int runProgram(int[] program) {
        int result = -1;

        for (int i = 0; i < program.length;) {
            String[] mode = Integer.toString(program[i]).split("");

            int opcode = program[i] % 100;
            int mode1 = getOrDefault(mode, 2);
            int mode2 = getOrDefault(mode, 3);

            switch (opcode) {
                case 1 -> { add(program, i, mode1, mode2); i += 4; }
                case 2 -> { mul(program, i, mode1, mode2); i += 4; }
                case 3 -> { program[program[i+1]] = 1; i += 2; }
                case 4 -> { result = print(program, i, mode1); i += 2; }
                case 5 -> i = jumpIfTrue(program, i, mode1, mode2);
                case 6 -> i = jumpIfFalse(program, i, mode1, mode2);
                case 7 -> { less(program, i, mode1, mode2); i += 4; }
                case 8 -> { equals(program, i, mode1, mode2); i += 4; }
                case 99 -> { return result; }
                default -> throw new RuntimeException("unknown opcode");
            }
        }

        return -1;
    }

    private void mul(int[] program, int i, int mode1, int mode2) {
        program[program[i+3]] = (mode1 == 1 ? program[i+1] : program[program[i+1]]) * (mode2 == 1 ? program[i+2] : program[program[i+2]]);
    }

    private void add(int[] program, int i, int mode1, int mode2) {
        program[program[i+3]] = (mode1 == 1 ? program[i+1] : program[program[i+1]]) + (mode2 == 1 ? program[i+2] : program[program[i+2]]);
    }

    private int jumpIfTrue(int[] program, int i, int mode1, int mode2) {
        if ((mode1 == 1 ? program[i+1] : program[program[i+1]]) != 0) {
            return mode2 == 1 ? program[i+2] : program[program[i+2]];
        } else {
            return i + 3;
        }
    }

    private int jumpIfFalse(int[] program, int i, int mode1, int mode2) {
        if ((mode1 == 1 ? program[i+1] : program[program[i+1]]) == 0) {
            return mode2 == 1 ? program[i+2] : program[program[i+2]];
        } else {
            return i + 3;
        }
    }

    private void less(int[] program, int i, int mode1, int mode2) {
        int param1 = mode1 == 1 ? program[i+1] : program[program[i+1]];
        int param2 = mode2 == 1 ? program[i+2] : program[program[i+2]];
        program[program[i+3]] = param1 < param2 ? 1 : 0;
    }

    private void equals(int[] program, int i, int mode1, int mode2) {
        int param1 = mode1 == 1 ? program[i+1] : program[program[i+1]];
        int param2 = mode2 == 1 ? program[i+2] : program[program[i+2]];
        program[program[i+3]] = param1 == param2 ? 1 : 0;
    }

    private int print(int[] program, int i, int mode1) {
        int result = (mode1 == 1 ? program[i+1] : program[program[i+1]]);
        if (result != 0) {
            return result;
        }

        return -1;
    }

    private int getOrDefault(String[] arr, int digit) {
        if (digit < arr.length) {
            return Integer.parseInt(arr[arr.length - digit - 1]);
        } else {
            return 0;
        }
    }
}
