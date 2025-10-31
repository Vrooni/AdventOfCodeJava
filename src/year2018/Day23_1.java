package year2018;

import java.util.*;

public class Day23_1 {
    private record NanoBot(int x, int y, int z, int radius) {}

    public String part1(List<String> input) {
        List<NanoBot> nanoBots = readInput(input);
        nanoBots.sort(Comparator.comparing(NanoBot::radius, Comparator.reverseOrder()));

        NanoBot strongest = nanoBots.getFirst();
        int inRange = 0;

        for (NanoBot nanoBot : nanoBots) {
            if (Math.abs(strongest.x - nanoBot.x) + Math.abs(strongest.y - nanoBot.y) + Math.abs(strongest.z - nanoBot.z) <= strongest.radius) {
                inRange++;
            }
        }

        return String.valueOf(inRange);
    }

    private List<NanoBot> readInput(List<String> input) {
        List<NanoBot> nanoBots = new ArrayList<>();

        for (String nanoBot : input) {
            String pos = nanoBot.split(", ")[0];
            String radius = nanoBot.split(", ")[1];

            String[] splitPos = pos.replace("pos=<", "").replace(">", "").split(",");

            nanoBots.add(new NanoBot(
                    Integer.parseInt(splitPos[0]),
                    Integer.parseInt(splitPos[1]),
                    Integer.parseInt(splitPos[2]),
                    Integer.parseInt(radius.replace("r=", ""))
            ));
        }

        return nanoBots;
    }
}
