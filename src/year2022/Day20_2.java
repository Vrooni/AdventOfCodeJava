package year2022;

import java.util.ArrayList;
import java.util.List;

public class Day20_2 {
    record Value(int startIndex, long value) {}

    public String part2(List<String> lines) {
        List<Value> list = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            list.add(new Value(i, Long.parseLong(lines.get(i)) * 811589153));
        }

        long result = 0;
        for (int i = 0; i < 10; i++) {
            result = mixList(list);
        }

        return String.valueOf(result);
    }

    private long mixList(List<Value> list) {
        for (int i = 0; i < list.size(); i++) {
            Value value = null;
            int index = 0;

            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).startIndex == i) {
                    value = list.get(j);
                    index = j;
                    break;
                }
            }

            int newIndex = (int) ((index+value.value) % (list.size()-1));

            if (newIndex < 0) {
                newIndex = list.size() - 1 + newIndex;
            }

            list.remove(index);
            list.add(newIndex, value);
        }

        int index0 = -1;
        for (int j = 0; j < list.size(); j++) {
            if (list.get(j).value == 0) {
                index0 = j;
                break;
            }
        }

        return list.get((index0 + 1000) % list.size()).value
                + list.get((index0 + 2000) % list.size()).value
                + list.get((index0 + 3000) % list.size()).value;
    }
}
