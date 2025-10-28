package year2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8_1 {

    public String part1(List<String> instructions) {
        Map<String, Integer> registers = new HashMap<>();

        for (String instruction : instructions) {
            String[] splitInstruction = instruction.split(" if ");

            if (eval(splitInstruction[1], registers)) {
                String[] operation = splitInstruction[0].split(" ");

                String register = operation[0];
                int value = Integer.parseInt(operation[2]);
                int mul = operation[1].equals("inc") ? 1 : -1;

                int newValue = registers.getOrDefault(register, 0) + mul * value;
                registers.put(register, newValue);
            }
        }

        return String.valueOf(registers.values().stream().max(Integer::compareTo).get());
    }

    private boolean eval(String condition, Map<String, Integer> registers) {
        String[] splitCondition = condition.split(" ");

        int value1 = registers.getOrDefault(splitCondition[0], 0);
        int value2 = Integer.parseInt(splitCondition[2]);

        boolean result;

        switch (splitCondition[1]) {
            case "==" -> result = value1 == value2;
            case "!=" -> result = value1 != value2;
            case "<" -> result = value1 < value2;
            case ">" -> result = value1 > value2;
            case "<=" -> result = value1 <= value2;
            case ">=" -> result = value1 >= value2;
            default -> result = false;
        }

        return result;
    }
}
