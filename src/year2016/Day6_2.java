package year2016;

import java.util.*;
import java.util.stream.Collectors;

public class Day6_2 {
    public String part2(List<String> input)  {
        List<Map<Character, Integer>> lettersOccurrences = init(input.getFirst().length());

        for (String line : input) {
            for (int i = 0; i < line.length(); i++) {
                Map<Character, Integer> map = lettersOccurrences.get(i);
                map.put(line.charAt(i), map.computeIfAbsent(line.charAt(i), k -> 0) + 1);
            }
        }

        return lettersOccurrences.stream().map(map ->
                getKeyByValue(map, Collections.min(map.values())).toString()).collect(Collectors.joining());
    }

    private List<Map<Character, Integer>> init(int size) {
        List<Map<Character, Integer>> lettersOccurrences = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            lettersOccurrences.add(new HashMap<>());
        }

        return lettersOccurrences;
    }

    private <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }

        throw new RuntimeException("No key found for value: " + value);
    }
}
