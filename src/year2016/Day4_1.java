package year2016;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4_1 {

    public String part1(List<String> input)  {
        int sum = 0;

        room: for (String room : input) {
            String end = room.substring(room.lastIndexOf("-") + 1);

            String[] letters = room.substring(0, room.lastIndexOf("-")).replaceAll("-", "").split("");
            String[] checksum = end.substring(end.indexOf("[")+1, end.indexOf("]")).split("");
            int id = Integer.parseInt(end.substring(0, end.indexOf("[")));

            Map<String, Integer> lettersCount = getLetterCounts(letters);

            for (String letter : checksum) {
                int letterCount = lettersCount.containsKey(letter) ? lettersCount.remove(letter) : 0;

                if (letterCount < Collections.max(lettersCount.values())) {
                    continue room;
                }
            }

            sum += id;
        }

        return String.valueOf(sum);
    }

    private Map<String, Integer> getLetterCounts(String[] letters) {
        Map<String, Integer> lettersCount = new HashMap<>();

        for (String letter : letters) {
            int count = lettersCount.computeIfAbsent(letter, key -> 0);
            lettersCount.put(letter, count + 1);
        }

        return lettersCount;
    }
}
