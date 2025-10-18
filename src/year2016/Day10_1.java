package year2016;

import java.util.*;

public class Day10_1 {
    private final List<Integer> VALUES = List.of(61, 17);

    private static class Bot {
        String id;
        String lower;
        String higher;
        List<Integer> values = new ArrayList<>();
    }

    public String part1(List<String> input)  {
        Map<String, Bot> bots = initBots(input);

        while (true) {
            List<Bot> botsList = new ArrayList<>((bots.values().stream().filter(bot -> bot.values.size() == 2).toList()));

            for (Bot bot : botsList) {
                if (new HashSet<>(bot.values).containsAll(VALUES)) {
                    return bot.id;
                }

                moveChip(bots, bot.lower, bot.values.get(0) < bot.values.get(1) ? bot.values.get(0) : bot.values.get(1));
                moveChip(bots, bot.higher, bot.values.get(0) > bot.values.get(1) ? bot.values.get(0) : bot.values.get(1));
                bots.get(bot.id).values = new ArrayList<>();
            }
        }
    }

    private Map<String, Bot> initBots(List<String> instructions) {
        Map<String, Bot> bots = new HashMap<>();

        for (String instruction : instructions) {
            String[] splitInstruction = instruction.split(" ");

            switch (splitInstruction[0]) {
                case "bot" -> {
                    Bot bot = bots.computeIfAbsent(splitInstruction[1], key -> new Bot());
                    bot.id = splitInstruction[1];
                    bot.lower = splitInstruction[5] + " " + splitInstruction[6];
                    bot.higher = splitInstruction[10] + " " + splitInstruction[11];
                }
                case "value" -> {
                    Bot bot = bots.computeIfAbsent(splitInstruction[5], key -> new Bot());
                    bot.id = splitInstruction[5];
                    bot.values.add(Integer.parseInt(splitInstruction[1]));
                }
            }
        }

        return bots;
    }

    private void moveChip(Map<String, Bot> bots, String to, int value) {
        if (to.split(" ")[0].equals("bot")) {
            bots.get(to.split(" ")[1]).values.add(value);
        }
    }
}
