package year2022;

import java.util.*;
import java.util.stream.Stream;

public class Day19_2 {
    private enum Robot {
        ORE, CLAY, OBSIDIAN, GEODE
    }

    public record Blueprint(int id, int oreCost, int clayCost, int[] obsidianCost, int[] geodeCost) {}

    public record State(int minutes, Robot robotToBuy,
                        int ores, int clay, int obsidian, int geodes,
                        int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots) {}

    private Map<State, Integer> cache = new HashMap<>();

    public String part2(final List<String> input) {
        List<Blueprint> blueprints = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String line = input.get(i);
            line = line.replaceAll("[^0-9]", " ");
            line = line.trim();
            line = line.replaceAll(" +", " ");

            String[] numbersAsStr = line.split(" ");
            int[] numbers = Arrays.stream(numbersAsStr).mapToInt(Integer::parseInt).toArray();

            blueprints.add(new Blueprint(numbers[0], numbers[1], numbers[2],
                    new int[]{numbers[3], numbers[4]},
                    new int[]{numbers[5], numbers[6]})
            );
        }

        int result = 1;
        for (Blueprint blueprint : blueprints) {
            result *= getMaxGeodes(blueprint);
        }

        return String.valueOf(result);
    }

    private int getMaxGeodes(Blueprint blueprint) {
        cache = new HashMap<>();
        int maxGeodes = 0;

        for (Robot robot : Robot.values()) {
            int geodes = getMaxGeodesForType(blueprint, 0, robot,
                    0, 0, 0, 0,
                    1, 0, 0, 0
            );

            maxGeodes = Math.max(maxGeodes, geodes);
        }

        return maxGeodes;
    }

    private int getMaxGeodesForType(Blueprint blueprint, int minutes, Robot robotToBuy,
                                    int ores, int clay, int obsidian, int geodes,
                                    int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots) {
        if (minutes == 32) {
            return geodes;
        }

        State state = new State(minutes, robotToBuy,
                ores, clay, obsidian, geodes,
                oreRobots, clayRobots, obsidianRobots, geodeRobots
        );
        if (cache.containsKey(state)) {
            return cache.get(state);
        }

        // improvement: do not build robot, if we already gain enough material each minute
        if (!needToBuy(blueprint, robotToBuy, ores, clay, obsidian)) {
            return 0;
        }

        // gather materials until we can build that robot
        for (; minutes < 32; minutes++) {

            boolean buildRobot = canAfford(blueprint, robotToBuy, ores, clay, obsidian);
            ores += oreRobots;
            clay += clayRobots;
            obsidian += obsidianRobots;
            geodes += geodeRobots;

            if (buildRobot) {
                switch (robotToBuy) {
                    case ORE -> {
                        ores -= blueprint.oreCost();
                        oreRobots++;
                    }

                    case CLAY -> {
                        ores -= blueprint.clayCost();
                        clayRobots++;
                    }

                    case OBSIDIAN -> {
                        ores -= blueprint.obsidianCost[0];
                        clay -= blueprint.obsidianCost[1];
                        obsidianRobots++;
                    }

                    case GEODE -> {
                        ores -= blueprint.geodeCost[0];
                        obsidian -= blueprint.geodeCost[1];
                        geodeRobots++;
                    }
                }

                int maxGeodes = 0;
                for (Robot robot : Robot.values()) {
                    maxGeodes = Math.max(maxGeodes, getMaxGeodesForType(blueprint, minutes + 1, robot,
                            ores, clay, obsidian, geodes,
                            oreRobots, clayRobots, obsidianRobots, geodeRobots)
                    );
                }

                cache.put(state, maxGeodes);
                return maxGeodes;
            }
        }

        cache.put(state, geodes);
        return geodes;
    }

    private boolean needToBuy(Blueprint blueprint, Robot robot, int ores, int clay, int obsidian) {
        switch (robot) {
            case ORE -> {
                int oreNeeded = Stream.of(blueprint.oreCost(),
                                blueprint.clayCost(),
                                blueprint.obsidianCost()[0],
                                blueprint.geodeCost()[0])
                        .mapToInt(i -> i).max().getAsInt();

                if (ores >= oreNeeded) {
                    return false;
                }
            }

            case CLAY -> {
                if (clay >= blueprint.obsidianCost()[1]) {
                    return false;
                }
            }

            case OBSIDIAN -> {
                if (obsidian >= blueprint.geodeCost()[1]) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean canAfford(Blueprint blueprint, Robot robot, int ores, int clay, int obsidian) {
        switch (robot) {
            case ORE -> {
                return ores >= blueprint.oreCost();
            }

            case CLAY -> {
                return ores >= blueprint.clayCost();
            }

            case OBSIDIAN -> {
                return ores >= blueprint.obsidianCost[0] && clay >= blueprint.obsidianCost[1];
            }

            case GEODE -> {
                return ores >= blueprint.geodeCost[0] && obsidian >= blueprint.geodeCost[1];
            }
        }

        return false;
    }
}