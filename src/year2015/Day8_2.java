package year2015;

import java.util.List;

public class Day8_2 {
    public String part2(List<String> lines) {
        int charCount = 0;
        int extendCount = 0;

        for (String line : lines) {

            int chars = line.length();
            int ext = line.length() + 4;

            for (int i = 0; i < line.length()-1; i++) {
                if (line.charAt(i) == '\\') {
                    switch (line.charAt(i+1)) {
                        case 'x' -> {
                            ext += 1;
                            i += 3;
                        }
                        case '\\', '\"' -> {
                            ext += 2;
                            i += 1;
                        }
                        default -> System.out.println("You forgo" + line);
                    }
                }
            }

            charCount += chars;
            extendCount += ext;
        }

        return String.valueOf(extendCount - charCount);
    }
}