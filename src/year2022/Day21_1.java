package year2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21_1 {
    public String part1(List<String> lines) {
        Map<String, String> monkeys = new HashMap<>();

        for (String line : lines) {
            String[] splitLine = line.split(": ");

            String name = splitLine[0];
            String number = splitLine[1];

            monkeys.put(name, number);
        }

        return String.valueOf(getNumberOfRoot(monkeys, "root"));
    }

    private long getNumberOfRoot(Map<String, String> monkeys, String monkey) {
        String number = monkeys.get(monkey);

        if (isNumeric(number)) {
            return Long.parseLong(number);
        }

        if (number.contains("*")) {
            String[] operands = number.split(" [*] ");
            return getNumberOfRoot(monkeys, operands[0]) * getNumberOfRoot(monkeys, operands[1]);
        } else if (number.contains("/")) {
            String[] operands = number.split(" / ");
            return getNumberOfRoot(monkeys, operands[0]) / getNumberOfRoot(monkeys, operands[1]);
        } else if (number.contains("+")) {
            String[] operands = number.split(" [+] ");
            return getNumberOfRoot(monkeys, operands[0]) + getNumberOfRoot(monkeys, operands[1]);
        } else if (number.contains("-")) {
            String[] operands = number.split(" - ");
            return getNumberOfRoot(monkeys, operands[0]) - getNumberOfRoot(monkeys, operands[1]);
        } else {
            throw new RuntimeException("No operator");
        }
    }

    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
