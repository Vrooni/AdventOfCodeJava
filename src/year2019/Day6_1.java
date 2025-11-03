package year2019;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6_1 {

    public String part1(List<String> input) {
        Map<String, String> objectOrbits = new HashMap<>();

        for (String line : input) {
            objectOrbits.put(line.split("\\)")[1], line.split("\\)")[0]);
        }

        int orbits = 0;
        for (Map.Entry<String, String> entry : objectOrbits.entrySet()) {
            String currentObject = entry.getValue();

            while (currentObject != null) {
                orbits++;
                currentObject = objectOrbits.get(currentObject);
            }
        }

        return String.valueOf(orbits);
    }
}
