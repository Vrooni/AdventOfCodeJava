package year2015;

import java.util.*;

public class Day19_1 {

    public String part1(List<String> lines) {
        List<String> input = new ArrayList<>(lines);
        Map<String, String> maps = new HashMap<>();
        String sentenceToMap = input.removeLast();
        input.removeLast(); //empty line

        for (String line : input) {
            String[] splitLine = line.split(" => ");
            maps.put(splitLine[1], splitLine[0]);
        }

        return String.valueOf(getSolutions(maps, sentenceToMap, true).size());
    }

    private Set<String> getSolutions(Map<String, String> maps, String sentenceToMap, boolean invert) {
        Set<String> molecules = new HashSet<>();

        for (Map.Entry<String, String> entry : maps.entrySet()) {
            String key = invert ? entry.getValue() : entry.getKey();
            String value = invert ? entry.getKey() : entry.getValue();

            for (int i = sentenceToMap.indexOf(key); i != -1; i = sentenceToMap.indexOf(key, i+1)) {
                String mappedSentence = sentenceToMap.substring(0, i) + value + sentenceToMap.substring(i + key.length());
                molecules.add(mappedSentence);
            }
        }

        return molecules;
    }
}
