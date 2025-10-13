package year2015;

import java.util.*;

public class Day19_2 {

    public String part2(List<String> lines) {
        List<String> input = new ArrayList<>(lines);
        Map<String, String> maps = new HashMap<>();
        String sentenceToMap = input.removeLast();
        input.removeLast(); //empty line

        for (String line : input) {
            String[] splitLine = line.split(" => ");
            maps.put(splitLine[1], splitLine[0]);
        }

        int count = 0;

        while (true) {
            boolean done = true;

            for (Map.Entry<String, String> entry : maps.entrySet()) {
                int index = sentenceToMap.indexOf(entry.getKey());

                if (index != -1) {
                    sentenceToMap = sentenceToMap.substring(0, index) + entry.getValue() + sentenceToMap.substring(index + entry.getKey().length());
                    done = false;
                    count++;
                }
            }

            if (done) {
                break;
            }
        }

        return String.valueOf(count);
    }
}
