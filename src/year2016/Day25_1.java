package year2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day25_1 {
    private static final Map<String, Integer> register = new HashMap<>();

    public String part1(List<String> instructions)  {
        List<Integer> output = new ArrayList<>();

        for (int j = 0; true; j++) {
            register.put("a", j);
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
                        case "out" -> output.add(getValue(instruction[1]));
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

                    if (output.size() == 10) {
                        if (output.equals(List.of(0, 1, 0, 1, 0, 1, 0, 1, 0, 1))) {
                            return String.valueOf(j);
                        } else {
                            output = new ArrayList<>();
                            break;
                        }
                    }

                } catch (Exception ex) {
                    return String.valueOf(instructions.get(i) + " is not valid");
                }
            }
        }
    }

    private static int getValue(String s) {
        return register.containsKey(s) ? register.get(s) : Integer.parseInt(s);
    }
}
