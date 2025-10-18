package year2016;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12_1 {
    private final Map<String, Integer> register = new HashMap<>();

    public String part1(List<String> instructions)  {
        register.put("a", 0);
        register.put("b", 0);
        register.put("c", 0);
        register.put("d", 0);

        for (int i = 0; i < instructions.size(); i++) {
            String[] instruction = instructions.get(i).split(" ");

            switch (instruction[0]) {
                case "cpy" -> register.put(instruction[2], getValue(instruction[1]));
                case "inc" -> register.put(instruction[1], getValue(instruction[1]) + 1);
                case "dec" -> register.put(instruction[1], getValue(instruction[1]) - 1);
                case "jnz" -> i += getValue(instruction[1]) == 0 ? 0 : getValue(instruction[2]) - 1;
            }
        }

        return String.valueOf(register.get("a"));
    }

    private int getValue(String s) {
        return register.containsKey(s) ? register.get(s) : Integer.parseInt(s);
    }
}
