package year2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23_1 {
    private record Instruction(String command, String register, Integer value) {}

    public String part1(List<String> lines) {
        List<Instruction> instructions = lines.stream().map(line -> {
            String[] splitLine = line.replace(",", "").split(" ");
            String register = null;
            Integer value;

            if (isNumeric(splitLine[1])) {
                value = Integer.parseInt(splitLine[1]);
            } else {
                register = splitLine[1];
                value = splitLine.length == 3 ? Integer.parseInt(splitLine[2]) : null;
            }

            return new Instruction(splitLine[0], register, value);
        }).toList();

        Map<String, Integer> registers = new HashMap<>();
        int index = 0;

        runInstruction(instructions, registers, index);
        return String.valueOf(registers.get("b"));
    }

    private void runInstruction(List<Instruction> instructions, Map<String, Integer> registers, int index) {
        while (index < instructions.size()) {
            Instruction instruction = instructions.get(index);

            switch (instruction.command) {
                case "hlf" -> registers.put(instruction.register, registers.getOrDefault(instruction.register, 0) / 2);
                case "tpl" -> registers.put(instruction.register, registers.getOrDefault(instruction.register, 0) * 3);
                case "inc" -> registers.put(instruction.register, registers.getOrDefault(instruction.register, 0) + 1);
                case "jmp" -> index += instruction.value - 1;
                case "jie" -> index += registers.getOrDefault(instruction.register, 0) % 2 == 0 ? instruction.value - 1 : 0;
                case "jio" -> index += registers.getOrDefault(instruction.register, 0) == 1 ? instruction.value - 1 : 0;
            }

            index++;
        }
    }

    private boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
