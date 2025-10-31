package year2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12_1 {

    public String part1(List<String> input) {
        input = new ArrayList<>(input);

        String initial = input
                .removeFirst()
                .replace("initial state: ", "");

        Map<String, String> notes = new HashMap<>();
        for (String note : input.subList(1, input.size())) {
            String[] splitNote = note.split(" => ");
            notes.put(splitNote[0], splitNote[1]);
        }

        return String.valueOf(getPlants(initial, notes));
    }

    private int getPlants(String initial, Map<String, String> notes) {
        StringBuilder pots = new StringBuilder(initial);
        int firstIndex = 0;

        for (int i = 0; i < 20; i++) {
            if (!canSpread(pots)) {
                firstIndex += 20 - i;
                break;
            }

            //padding
            while (pots.indexOf("#") < 4) {
                pots.insert(0, ".");
                firstIndex--;
            }
            while (pots.length() - pots.lastIndexOf("#") < 5) {
                pots.append(".");
            }

            pots = getNextGen(pots, notes);
        }

        int plants = 0;

        for (int i = 0; i < pots.length(); i++) {
            plants += pots.charAt(i) == '#' ? i + firstIndex : 0;
        }

        return plants;
    }


    private StringBuilder getNextGen(StringBuilder pots, Map<String, String> notes) {
        StringBuilder nextGen = new StringBuilder();
        nextGen.append('.').append('.');

        for (int j = 2; j < pots.length() - 2; j++) {
            nextGen.append(notes.get(pots.substring(j-2, j+3)));
        }

        nextGen.append('.').append('.');
        return nextGen;
    }

    private boolean canSpread(StringBuilder pots) {
        int lastPlant = -5;

        for (int i = 0; i < pots.length(); i++) {
            if (pots.charAt(i) == '#') {
                if (i - lastPlant <= 4) {
                    return true;
                }
                lastPlant = i;
            }
        }

        return false;
    }
}
