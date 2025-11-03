package year2019;

import java.util.Arrays;
import java.util.List;

public class Day2_1 {
    public String part1(List<String> lines) {
        String input = lines.getFirst();
        int[] program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        program[1] = 12;
        program[2] = 2;
        runProgram(program);

        return String.valueOf(program[0]);
    }

    private void runProgram(int[] program) {
        for (int i = 0; i < program.length; i += 4) {
            switch (program[i]) {
                case 1 -> program[program[i+3]] = program[program[i+1]] + program[program[i+2]];
                case 2 -> program[program[i+3]] = program[program[i+1]] * program[program[i+2]];
                case 99 -> { return; }
                default -> throw new RuntimeException("unknown opcode");
            }
        }
    }
}
