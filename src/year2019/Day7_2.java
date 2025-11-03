package year2019;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Day7_2 {
    private static class IntCodeProgram {
        int[] program;
        int output = 0;
        int ip = 0;
        boolean firstInput = true;
    }

    public String part2(List<String> lines) {
        String input = lines.getFirst();
        int maxOutput = Integer.MIN_VALUE;

        for (int i = 5; i < 10; i++) {
            for (int j = 5; j < 10; j++) {
                for (int k = 5; k < 10; k++) {
                    for (int l = 5; l < 10; l++) {
                        for (int m = 5; m < 10; m++) {
                            if (new HashSet<>(List.of(i, j, k, l, m)).size() != 5) {
                                continue;
                            }

                            IntCodeProgram program1 = new IntCodeProgram();
                            IntCodeProgram program2 = new IntCodeProgram();
                            IntCodeProgram program3 = new IntCodeProgram();
                            IntCodeProgram program4 = new IntCodeProgram();
                            IntCodeProgram program5 = new IntCodeProgram();
                            program1.program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            program2.program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            program3.program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            program4.program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            program5.program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

                            boolean halts = false;

                            while (!halts) {
                                runProgram(program1, i, program5.output);
                                runProgram(program2, j, program1.output);
                                runProgram(program3, k, program2.output);
                                runProgram(program4, l, program3.output);
                                halts = runProgram(program5, m, program4.output);
                            }

                            maxOutput = Math.max(program5.output, maxOutput);
                        }
                    }
                }
            }
        }

        return String.valueOf(maxOutput);
    }

    private boolean runProgram(IntCodeProgram intCodeProgram, int phaseSetting, int input) {
        int[] program = intCodeProgram.program;

        while (intCodeProgram.ip < program.length) {
            int i = intCodeProgram.ip;
            String[] mode = Integer.toString(program[i]).split("");

            int opcode = program[i] % 100;
            int mode1 = getOrDefault(mode, 2);
            int mode2 = getOrDefault(mode, 3);

            switch (opcode) {
                case 1 -> { add(program, i, mode1, mode2); intCodeProgram.ip += 4; }
                case 2 -> { mul(program, i, mode1, mode2); intCodeProgram.ip += 4; }
                case 3 -> {
                    program[program[i+1]] = intCodeProgram.firstInput ? phaseSetting : input; intCodeProgram.ip += 2;
                    intCodeProgram.firstInput = false;
                }
                case 4 -> { print(intCodeProgram, i, mode1); intCodeProgram.ip += 2; return false; }
                case 5 -> intCodeProgram.ip = jumpIfTrue(program, i, mode1, mode2);
                case 6 -> intCodeProgram.ip = jumpIfFalse(program, i, mode1, mode2);
                case 7 -> { less(program, i, mode1, mode2); intCodeProgram.ip += 4; }
                case 8 -> { equals(program, i, mode1, mode2); intCodeProgram.ip += 4; }
                case 99 -> { return true; }
                default -> throw new RuntimeException("unknown opcode");
            }
        }

        return false;
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

    private void print(IntCodeProgram intCodeProgram, int i, int mode1) {
        int[] program = intCodeProgram.program;
        intCodeProgram.output = mode1 == 1 ? program[i+1] : program[program[i+1]];
    }

    private int getOrDefault(String[] arr, int digit) {
        if (digit < arr.length) {
            return Integer.parseInt(arr[arr.length - digit - 1]);
        } else {
            return 0;
        }
    }
}
