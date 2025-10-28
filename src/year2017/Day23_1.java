package year2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23_1 {
    public String part1(List<String> instructions) {
        Map<String, Integer> registers = new HashMap<>(Map.of(
                "a", 0,
                "b", 0,
                "c", 0,
                "d", 0,
                "e", 0,
                "f", 0,
                "h", 0
        ));

        int index = 0;
        int mulCount = 0;
        while (index >= 0 && index < instructions.size()) {
            String[] instruction = instructions.get(index).split(" ");

            switch (instruction[0]) {
                case "set" -> registers.put(instruction[1], getValue(registers, instruction[2]));
                case "sub" -> registers.put(instruction[1], registers.get(instruction[1]) - getValue(registers, instruction[2]));
                case "mul" -> { registers.put(instruction[1], registers.get(instruction[1]) * getValue(registers, instruction[2])); mulCount++; }
                case "jnz" -> index += getValue(registers, instruction[1]) == 0 ? 0 : getValue(registers, instruction[2]) - 1;
            }

            index++;
        }

        return String.valueOf(mulCount);
    }

    private int getValue(Map<String, Integer> registers, String regOrValue) {
        return registers.containsKey(regOrValue) ? registers.get(regOrValue) : Integer.parseInt(regOrValue);
    }
}
