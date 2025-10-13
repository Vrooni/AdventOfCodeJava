package year2015;

import java.util.ArrayList;
import java.util.List;

public class Day18_1 {

    public String part1(List<String> lines) {
        List<List<Boolean>> lights = readInput(lines);

        List<List<Boolean>> switchedLights = switchLights(lights, 100);
        int lightsOn = countLightsOn(switchedLights);

        return String.valueOf(lightsOn);
    }

    private List<List<Boolean>> readInput(List<String> input) {
        List<List<Boolean>> lights = new ArrayList<>();

        for (String line : input) {
            List<Boolean> lightsRow = new ArrayList<>();

            for (String l : line.split("")) {
                lightsRow.add(l.equals("#"));
            }

            lights.add(lightsRow);
        }

        return lights;
    }

    private List<List<Boolean>> switchLights(List<List<Boolean>> lights, int times) {
        for (int i = 0; i < times; i++) {
            lights = switchLights(lights);
        }

        return lights;
    }

    private List<List<Boolean>> switchLights(List<List<Boolean>> lights) {
        List<List<Boolean>> switchedLights = new ArrayList<>();

        for (int i = 0; i < lights.size(); i++) {
            List<Boolean> switchedLine = new ArrayList<>();

            for (int j = 0; j < lights.get(i).size(); j++) {

                int neighbours = 0;
                for (int k = Math.max(0, i-1); k <= Math.min(lights.get(i).size()-1, i+1); k++) {
                    for (int l = Math.max(0, j-1); l <= Math.min(lights.get(i).size()-1, j+1); l++) {

                        if (k != i || l != j) {
                            if (lights.get(k).get(l)) {
                                neighbours++;
                            }
                        }
                    }
                }

                if (lights.get(i).get(j)) {
                    switchedLine.add(neighbours == 2 || neighbours == 3);
                } else {
                    switchedLine.add(neighbours == 3);
                }
            }

            switchedLights.add(switchedLine);
        }

        return switchedLights;
    }

    private int countLightsOn(List<List<Boolean>> lights) {
        int count = 0;

        for (List<Boolean> lightsRow : lights) {
            for (Boolean light : lightsRow) {
                if (light) {
                    count++;
                }
            }
        }

        return count;
    }
}
