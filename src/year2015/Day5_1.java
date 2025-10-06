import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day5_1 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        int niceStrings = 0;
        for (String line : lines) {
            if (getVowels(line) < 3) {
                continue;
            }

            if (!hasDoubleLetters(line)) {
                continue;
            }

            if (line.contains("ab") || line.contains("cd") || line.contains("pq") || line.contains("xy")) {
                continue;
            }

            niceStrings++;
        }

        System.out.println(niceStrings);
    }

    private static int getVowels(String s) {
        int vowels = 0;
        for (char c : s.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowels++;
            }

            if (vowels == 3) {
                return vowels;
            }
        }

        return vowels;
    }

    private static boolean hasDoubleLetters(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                return true;
            }
        }

        return false;
    }
}
