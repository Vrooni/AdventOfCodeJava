import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day5_2 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        int niceStrings = 0;
        for (String line : lines) {
            if (!hasTwoDoubles(line)) {
                continue;
            }

            if (!hasPalindrome(line)) {
                continue;
            }

            niceStrings++;
        }

        System.out.println(niceStrings);
    }

    private static boolean hasTwoDoubles(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            for (int j = i + 2; j < s.length() - 1; j++) {
                if (s.charAt(i) == s.charAt(j) && s.charAt(i + 1) == s.charAt(j + 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean hasPalindrome(String s) {
        for (int i = 0; i < s.length() - 2; i++) {
            for (int j = 0; j < s.length() - 1; j++) {
                if (s.charAt(i) == s.charAt(i + 2)) {
                    return true;
                }
            }
        }

        return false;
    }
}
