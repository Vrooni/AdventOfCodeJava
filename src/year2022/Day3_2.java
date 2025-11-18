package year2022;

import java.util.List;

public class Day3_2 {
    public String part2(List<String> lines) {
        int priorities = 0;
        for (int i = 0; i < lines.size(); i+=3) {
            char sameItem = getSameChar(lines.get(i), lines.get(i+1), lines.get(i+2));

            priorities += Character.isLowerCase(sameItem)
                    ? (int) sameItem - 96
                    : (int) sameItem - 38;
        }

        return String.valueOf(priorities);
    }

    private char getSameChar(String s1, String s2, String s3) {
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                for (int k = 0; k < s3.length(); k++) {
                    if (s1.charAt(i) == s2.charAt(j) && s2.charAt(j) == s3.charAt(k)) {
                        return s1.charAt(i);
                    }
                }
            }
        }

        throw new RuntimeException("No similar character");
    }
}
