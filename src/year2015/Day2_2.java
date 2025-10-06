package year2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day2_2 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        int ribbon = 0;
        for (String line : lines) {
            int[] dimensions = Arrays.stream(line.split("x")).mapToInt(Integer::parseInt).sorted().toArray();

            ribbon += perimeter(dimensions[0], dimensions[1]) + dimensions[0] * dimensions[1] * dimensions[2];
        }

        System.out.println(ribbon);
    }

    private static int perimeter(int a, int b) {
        return a + a + b + b;
    }
}
