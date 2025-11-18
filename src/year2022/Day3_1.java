package year2022;

import java.util.List;

public class Day3_1 {

    public String part1(List<String> lines) {
        int priorities = 0;
        for (String line : lines) {
            String compartment1 = line.substring(0, line.length()/2);
            String compartment2 = line.substring(line.length()/2);

            char sameItem = getSameChar(compartment1, compartment2);

            priorities += Character.isLowerCase(sameItem)
                    ? (int) sameItem - 96
                    : (int) sameItem - 38;
        }

        return String.valueOf(priorities);
    }

    private char getSameChar(String s1, String s2) {
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    return s1.charAt(i);
                }
            }
        }

        throw new RuntimeException("No similar character");
    }
}
