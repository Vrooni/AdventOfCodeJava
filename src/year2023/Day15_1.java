package year2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day15_1 {

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get(args[0])).trim();
        List<String> lines = List.of(input.split(","));

        int result = sum(lines.stream().map(Day15_1::getHash).toList());
        System.out.println(result);
    }

    private static int getHash(String line) {
        int result = 0;

        for (char c : line.replaceAll("\n", "").toCharArray()) {
            result += c;
            result *= 17;
            result %= 256;
        }

        return result;
    }

    private static int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(a -> a).sum();
    }
}
