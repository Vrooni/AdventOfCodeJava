import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2_1 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        int wrappingPaper = 0;
        for (String line : lines) {
            String[] dimensions = line.split("x");

            int l = Integer.parseInt(dimensions[0]);
            int w = Integer.parseInt(dimensions[1]);
            int h = Integer.parseInt(dimensions[2]);

            wrappingPaper += area(l, w, h) + extraArea(l, w, h);
        }

        System.out.println(wrappingPaper);
    }

    private static int area(int l, int w, int h) {
        return 2 * l * w + 2 * w * h + 2 * h * l;
    }

    private static int extraArea(int l, int w, int h) {
        return Math.min(Math.min(l * w, l * h), w * h);
    }
}
