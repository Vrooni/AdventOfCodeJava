package year2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23_1 {
    private final Map<String, Integer> register = new HashMap<>();

    public String part1(List<String> instructions)  {
        instructions = new ArrayList<>(instructions);
        register.put("a", 7);
        register.put("b", 0);
        register.put("c", 0);
        register.put("d", 0);

        for (int i = 0; i < instructions.size(); i++) {
            String[] instruction = instructions.get(i).split(" ");

            try {
                switch (instruction[0]) {
                    case "cpy" -> register.put(instruction[2], getValue(instruction[1]));
                    case "inc" -> register.put(instruction[1], getValue(instruction[1]) + 1);
                    case "dec" -> register.put(instruction[1], getValue(instruction[1]) - 1);
                    case "jnz" -> i += getValue(instruction[1]) == 0 ? 0 : getValue(instruction[2]) - 1;
                    case "tgl" -> {
                        int index = i + getValue(instruction[1]);

                        if (index > 0 && index < instructions.size()) {
                            String[] instructionToToggle = instructions.get(index).split(" ");

                            switch (instructionToToggle.length) {
                                case 2 -> instructions.set(index, (instructionToToggle[0].equals("inc") ? "dec " : "inc ") + instructionToToggle[1]);
                                case 3 -> instructions.set(index, (instructionToToggle[0].equals("jnz") ? "cpy " : "jnz ") + instructionToToggle[1] + " " + instructionToToggle[2]);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                return instructions.get(i) + " is not valid";
            }
        }

        return String.valueOf(register.get("a"));
    }

    private int getValue(String s) {
        return register.containsKey(s) ? register.get(s) : Integer.parseInt(s);
    }
}
