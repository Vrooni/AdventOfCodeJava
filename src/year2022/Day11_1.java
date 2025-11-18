package year2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Day11_1 {
    static class Monkey {
        List<Long> items = new ArrayList<>();
        Function<Long, Long> operation;
        Function<Long, Integer> test;
        int inspectedItems = 0;
    }

    public String part1(List<String> lines) {
        List<Monkey> monkeys = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Monkey monkey = new Monkey();
            String line = lines.get(i * 7 + 1).split(": ")[1];
            String[] list = line.split(", ");
            List<Long> items = new ArrayList<>();

            for (String item : list) {
                items.add(Long.parseLong(item));
            }

            monkey.items = items;


            line = lines.get(i * 7 + 2).split(": new = old ")[1];
            list = line.split(" ");

            monkey.operation = getMonkeyOperation(list);

            Function<Long, Integer> test = null;
            line = lines.get(i * 7 + 3).split(": divisible by ")[1];
            String line1 = lines.get(i * 7 + 4).split("If true: throw to monkey ")[1];
            String line2 = lines.get(i * 7 + 5).split("If false: throw to monkey ")[1];
            String finalLine = line;

            test = (value) -> value % Long.parseLong(finalLine) == 0 ? Integer.parseInt(line1) : Integer.parseInt(line2);

            monkey.test = test;


            monkeys.add(monkey);
        }

        for (int i = 0; i < 20; i++) {
            for (Monkey monkey : monkeys) {

                for (long item : monkey.items) {
                    item = monkey.operation.apply(item);
                    monkey.inspectedItems++;

                    int nextMonkey = monkey.test.apply(item);
                    monkeys.get(nextMonkey).items.add(item);
                }

                monkey.items = new ArrayList<>();
            }
        }

        monkeys.sort(Comparator.comparingInt((Monkey i) -> i.inspectedItems).reversed());
        return String.valueOf(monkeys.get(0).inspectedItems * monkeys.get(1).inspectedItems);
    }

    private static Function<Long, Long> getMonkeyOperation(String[] list) {
        Function<Long, Long> function = null;

        switch (list[0]) {
            case "+" -> function = (old) -> (old + (list[1].equals("old") ? old : Long.parseLong(list[1]))) / 3;
            case "-" -> function = (old) -> (old - (list[1].equals("old") ? old : Long.parseLong(list[1]))) / 3;
            case "*" -> function = (old) -> (old * (list[1].equals("old") ? old : Long.parseLong(list[1]))) / 3;
            case "/" -> function = (old) -> (old / (list[1].equals("old") ? old : Long.parseLong(list[1]))) / 3;
        }
        return function;
    }
}
