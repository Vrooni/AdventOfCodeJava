package year2016;

import java.util.List;

public class Day21_2 {
    public String part2(List<String> input)  {
        String password = "fbgdceah";

        for (int i = input.size() - 1; i >= 0; i--) {
            password = execute(password, input.get(i));
        }

        return password;
    }

    private String execute(String password, String instruction) {

        if (instruction.startsWith("swap position")) {
            String[] splitInstruction = instruction.split(" ");

            int index1 = Integer.parseInt(splitInstruction[2]);
            int index2 = Integer.parseInt(splitInstruction[5]);

            password = swapLetters(password, index1, index2);
        }

        else if (instruction.startsWith("swap letter")) {
            String[] splitInstruction = instruction.split(" ");

            int index1 = password.indexOf(splitInstruction[2]);
            int index2 = password.indexOf(splitInstruction[5]);

            password = swapLetters(password, index1, index2);
        }

        else if (instruction.startsWith("rotate based")) {
            password = findReverseRotation(password, instruction.split(" ")[6]);
        }

        else if (instruction.startsWith("rotate")) {
            String[] splitInstruction = instruction.split(" ");
            password = rotate(password, Integer.parseInt(splitInstruction[2]), !splitInstruction[1].equals("left"));
        }

        else if (instruction.startsWith("reverse")) {
            StringBuilder builder = reverse(password, instruction);

            password = builder.toString();
        }

        else {
            String[] splitInstruction = instruction.split(" ");
            StringBuilder builder = new StringBuilder(password);

            int index1 = Integer.parseInt(splitInstruction[5]);
            int index2 = Integer.parseInt(splitInstruction[2]);

            char c = builder.charAt(index1);
            builder.deleteCharAt(index1);

            password = safeInsert(builder, index2, c);
        }

        return password;
    }

    private StringBuilder reverse(String password, String instruction) {
        String[] splitInstruction = instruction.split(" ");
        StringBuilder builder = new StringBuilder(password);

        int index1 = Integer.parseInt(splitInstruction[2]);
        int index2 = Integer.parseInt(splitInstruction[4]);

        while (index1 < index2) {
            char temp = builder.charAt(index1);
            builder.setCharAt(index1, builder.charAt(index2));
            builder.setCharAt(index2, temp);

            index1++;
            index2--;
        }
        return builder;
    }

    private String swapLetters(String password, int index1, int index2) {
        StringBuilder builder = new StringBuilder(password);

        char temp = builder.charAt(index1);
        builder.setCharAt(index1, builder.charAt(index2));
        builder.setCharAt(index2, temp);

        return builder.toString();
    }

    private String rotate(String password, int offset, boolean backwards) {
        offset = offset % password.length();

        return backwards
                ? password.substring(offset) + password.substring(0, offset)
                : password.substring(password.length() - offset) + password.substring(0, password.length() - offset);
    }

    private String findReverseRotation(String password, String letter) {
        for (int i = 1; true; i++) {
            password = rotate(password, 1, true);

            int index = password.indexOf(letter);
            int offset = index >= 4 ? index + 2 : index + 1;

            if (offset == i) {
                return password;
            }
        }
    }

    private String safeInsert(StringBuilder builder, int index, char c) {
        if (builder.length() == index) {
            builder.append(c);
        } else {
            builder.insert(index, c);
        }

        return builder.toString();
    }
}
