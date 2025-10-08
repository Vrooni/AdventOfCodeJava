package year2015;

import java.util.List;

public class Day8_1 {
    public String part1(List<String> lines) {
        int charCount = 0;
        int memCount = 0;

        for (String line : lines) {

            int chars = line.length();
            int mems = line.length() - 2;

            for (int i = 0; i < line.length()-1; i++) {
                if (line.charAt(i) == '\\') {
                    switch (line.charAt(i+1)) {
                        case 'x' -> {
                            mems -= 3;
                            i += 3;
                        }
                        case '\\', '\"' -> {
                            mems -= 1;
                            i += 1;
                        }
                        default -> System.out.println("You forgo" + line);
                    }
                }
            }

            charCount += chars;
            memCount += mems;
        }

        return String.valueOf(charCount - memCount);
    }
}
