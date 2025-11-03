package year2019;

import java.util.Arrays;
import java.util.List;

public class Day2_2 {
    public String part2(List<String> lines) {
        String input = lines.getFirst();

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                int[] program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

                program[1] = i;
                program[2] = j;
                runProgram(program);

                if (program[0] == 19690720) {
                    return String.valueOf(100 * i + j);
                }
            }
        }

        return "-1";
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
