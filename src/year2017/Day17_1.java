package year2017;

import java.util.ArrayList;
import java.util.List;

public class Day17_1 {

    public String part1(List<String> lines) {
        int times = Integer.parseInt(lines.getFirst());
        List<Integer> buffer = new ArrayList<>();
        buffer.add(0);
        int index = 0;

        for (int i = 1; i <= 2017; i++) {
            index = (index + times) % i + 1;

            if (index >= buffer.size()) {
                buffer.add(i);
            } else {
                buffer.add(index, i);
            }
        }

        return String.valueOf(buffer.get(index + 1));
    }
}
