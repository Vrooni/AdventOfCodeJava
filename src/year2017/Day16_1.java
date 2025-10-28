package year2017;

import java.util.List;

public class Day16_1 {
    public String part1(List<String> lines) {
        String input = lines.getFirst();
        String programs = "abcdefghijklmnop";

        for (String move : input.split(",")) {

            switch (move.charAt(0)) {
                case 's' -> {
                    int size = Integer.parseInt(move.substring(1));
                    int index = programs.length() - size;
                    programs = programs.substring(index) + programs.substring(0, index);
                }
                case 'x' -> {
                    String[] indexes = move.substring(1).split("/");
                    int index1 = Integer.parseInt(indexes[0]);
                    int index2 = Integer.parseInt(indexes[1]);

                    programs = swap(programs, index1, index2);
                }
                case 'p' -> {
                    String[] programNames = move.substring(1).split("/");
                    int index1 = programs.indexOf(programNames[0]);
                    int index2 = programs.indexOf(programNames[1]);

                    programs = swap(programs, index1, index2);
                }
            }
        }

        return programs;
    }

    public String swap(String s, int index1, int index2) {
        StringBuilder builder = new StringBuilder(s);

        char temp = builder.charAt(index1);
        builder.setCharAt(index1, builder.charAt(index2));
        builder.setCharAt(index2, temp);

        return builder.toString();
    }
}
