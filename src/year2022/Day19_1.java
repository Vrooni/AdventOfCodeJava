package year2022;

import java.util.*;
import java.util.stream.IntStream;

public class Day19_1 {
    record Blueprint(int id, int oreCosts, int clayCosts, int[] obsidianCosts, int[] geodeCosts) {}
    record Resource(int ore, int clay, int obsidian, int geodes) { }
    enum Robot {
        ORE, CLAY, OBSIDIAN, GEODE, NONE
    }
    public String part1(List<String> lines) {
        List<Blueprint> blueprints = new ArrayList<>();

        for (String line : lines) {
            line = line.replaceAll("[^0-9]", " ");
            line = line.trim();
            line = line.replaceAll(" +", " ");

            String[] numbersAsStr = line.split(" ");
            int[] numbers = Arrays.stream(numbersAsStr).mapToInt(Integer::parseInt).toArray();

            blueprints.add(new Blueprint(numbers[0], numbers[1], numbers[2], new int[]{numbers[3], numbers[4]}, new int[]{numbers[5], numbers[6]}));
        }

        Map<Robot, Integer> activeRobots = new HashMap<>();
        activeRobots.put(Robot.ORE, 1);
        activeRobots.put(Robot.CLAY, 0);
        activeRobots.put(Robot.OBSIDIAN, 0);
        activeRobots.put(Robot.GEODE, 0);

        int total = IntStream.range(0, blueprints.size()).parallel().map(index -> {
            Blueprint blueprint = blueprints.get(index);
            int result = getObsidian(blueprint, activeRobots, new HashSet<>(), new Resource(0, 0, 0, 0), 0, 0);
            System.out.println(index + ": " + result);

            return (index + 1) * result;
        }).sum();

        return String.valueOf(total);
    }

    private static int getObsidian(Blueprint blueprint, Map<Robot, Integer> activeRobots, Set<Robot> possibleRobot, Resource resource, int minutes, int bestOverall) {
        if (minutes == 24) {
            return resource.geodes;
        }

        int remaining = 24 - minutes;
        int bestCase = resource.geodes + activeRobots.get(Robot.GEODE) * remaining + (remaining * (remaining + 1)) / 2;
        if (bestCase <= bestOverall) {
            return 0;
        }

        //possible robots
        Set<Robot> newPossibleRobots = new HashSet<>();

        if (resource.ore >= blueprint.oreCosts) {
            newPossibleRobots.add(Robot.ORE);
        }
        if (resource.ore >= blueprint.clayCosts) {
            newPossibleRobots.add(Robot.CLAY);
        }
        if (resource.ore >= blueprint.obsidianCosts[0] && resource.clay >= blueprint.obsidianCosts[1]) {
            newPossibleRobots.add(Robot.OBSIDIAN);
        }
        if (resource.ore >= blueprint.geodeCosts[0] && resource.obsidian >= blueprint.geodeCosts[1]) {
            newPossibleRobots.add(Robot.GEODE);
        }

        //optimizing
        int possibles = newPossibleRobots.size();
        newPossibleRobots.removeAll(possibleRobot);

        if (possibles != 4) {
            newPossibleRobots.add(Robot.NONE);
        }

        //increase resources
        Resource newResources = new Resource(
                resource.ore + activeRobots.get(Robot.ORE),
                resource.clay + activeRobots.get(Robot.CLAY),
                resource.obsidian + activeRobots.get(Robot.OBSIDIAN),
                resource.geodes + activeRobots.get(Robot.GEODE)
        );

        //create new robot
        int geode = -1;
        for (Robot robotToBuild : newPossibleRobots) {
            Map<Robot, Integer> newActiveRobots = new HashMap<>(activeRobots);
            Resource currentResources = newResources;

            if (robotToBuild != Robot.NONE) {
                newActiveRobots.put(robotToBuild, activeRobots.get(robotToBuild) + 1);

                //decrease ores
                switch (robotToBuild) {
                    case ORE -> currentResources = new Resource(
                            newResources.ore - blueprint.oreCosts,
                            newResources.clay, newResources.obsidian,
                            newResources.geodes);
                    case CLAY -> currentResources = new Resource(
                            newResources.ore - blueprint.clayCosts,
                            newResources.clay,
                            newResources.obsidian,
                            newResources.geodes);
                    case OBSIDIAN -> currentResources = new Resource(
                            newResources.ore - blueprint.obsidianCosts[0],
                            newResources.clay - blueprint.obsidianCosts[1],
                            newResources.obsidian,
                            newResources.geodes);
                    case GEODE -> currentResources = new Resource(
                            newResources.ore - blueprint.geodeCosts[0],
                            newResources.clay,
                            newResources.obsidian - blueprint.geodeCosts[1],
                            newResources.geodes);
                }

                geode = Math.max(geode, getObsidian(blueprint, newActiveRobots, new HashSet<>(), currentResources, minutes + 1, Math.max(geode, bestOverall)));
            } else {
                geode = Math.max(geode, getObsidian(blueprint, newActiveRobots, newPossibleRobots, currentResources, minutes + 1, Math.max(geode, bestOverall)));

            }
        }

        return geode;
    }
}
