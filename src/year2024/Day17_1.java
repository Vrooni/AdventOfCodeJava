package year2024;

import java.util.*;
import java.util.stream.Collectors;

public class Day17_1 {

    public String part1(List<String> input) {
        String programString = input.get(4).split(": ")[1];
        int[] program = Arrays.stream(programString.split(",")).mapToInt(Integer::parseInt).toArray();

        int a = Integer.parseInt(input.get(0).split(": ")[1]);
        int b = Integer.parseInt(input.get(1).split(": ")[1]);
        int c = Integer.parseInt(input.get(2).split(": ")[1]);

        List<Long> output = runProgram(program, a, b, c);
        return output.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    private List<Long> runProgram(int[] program, long a, long b, long c) {
        List<Long> output = new ArrayList<>();
        int pointer = 0;

        while (pointer < program.length-1) {
            int opcode = program[pointer];
            int literalOperand = program[pointer+1];

            long comboOperand = switch (literalOperand) {
                case 4 -> a;
                case 5 -> b;
                case 6 -> c;
                default -> literalOperand;
            };

            switch (opcode) {
                case 0 -> a = (long) (a / (Math.pow(2, comboOperand)));
                case 1 -> b = b ^ literalOperand;
                case 2 -> b = comboOperand % 8;
                case 3 -> {
                    if (a != 0) {
                        pointer = literalOperand;
                        continue;
                    }
                }
                case 4 -> b = b ^ c;
                case 5 -> output.add(comboOperand % 8);
                case 6 -> b = (long) (a / (Math.pow(2, comboOperand)));
                case 7 -> c = (long) (a / (Math.pow(2, comboOperand)));
            }

            pointer += 2;
        }

        return output;
    }
}
