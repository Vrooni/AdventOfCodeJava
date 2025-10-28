package year2017;

import java.util.*;

public class Day18_1 {

    public String part1(List<String> instructions) {
        Map<String, Long> registers = new HashMap<>();

        int index = 0;
        long playedSound = 0;

        boolean receivedSound = false;

        while (!receivedSound) {
            String[] splitInstruction = instructions.get(index).split(" ");

            switch (splitInstruction[0]) {
                case "snd" -> playedSound = getValue(registers, splitInstruction[1]);
                case "set" -> registers.put(splitInstruction[1], getValue(registers, splitInstruction[2]));
                case "add" -> registers.put(splitInstruction[1], getValue(registers, splitInstruction[1]) + getValue(registers, splitInstruction[2]));
                case "mul" -> registers.put(splitInstruction[1], getValue(registers, splitInstruction[1]) * getValue(registers, splitInstruction[2]));
                case "mod" -> registers.put(splitInstruction[1], getValue(registers, splitInstruction[1]) % getValue(registers, splitInstruction[2]));
                case "rcv" -> receivedSound = getValue(registers, splitInstruction[1]) != 0;
                case "jgz" -> index += getValue(registers, splitInstruction[1]) > 0 ? (int) (getValue(registers, splitInstruction[2]) - 1) : 0;
            }

            index++;
        }

        return String.valueOf(playedSound);
    }

    private long getValue(Map<String, Long> registers, String key) {
        return isNumeric(key) ? Long.parseLong(key) : registers.getOrDefault(key, 0L);
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
