package year2023;

import javax.script.ScriptException;
import java.util.*;
import java.util.function.Function;

public class Day19_1 {
    record Rule(Function<Part, Boolean> condition, String sendTo) {}
    record Part(int x, int m, int a, int s) {}

    public String part1(List<String> input) throws ScriptException {
        Map<String, List<Rule>> workflows = new HashMap<>();
        List<Part> parts = new ArrayList<>();

        boolean readWorkflow = true;
        for (String line : input) {
            if (line.isEmpty()) {
                readWorkflow = false;
                continue;
            }

            if (readWorkflow) {
                String name = line.substring(0, line.indexOf("{"));
                workflows.put(name, getRules(line));
            } else {
                parts.add(getPart(line));
            }
        }

        int result = 0;
        for (Part part : parts) {

            String workflow = "in";
            while (true) {
                if (workflow.equals("R")) {
                    break;
                }

                else if (workflow.equals("A")) {
                    result += part.x + part.m + part.a + part.s;
                    break;
                }

                List<Rule> rules = workflows.get(workflow);
                for (Rule rule : rules) {
                    if (rule.condition.apply(part)) {
                        workflow = rule.sendTo;
                        break;
                    }
                }
            }
        }

        return String.valueOf(result);
    }

    private List<Rule> getRules(String line) {
        List<Rule> rules = new ArrayList<>();
        String[] ruleStrings = line.substring(line.indexOf("{") + 1, line.indexOf("}")).split(",");

        for (String ruleString : ruleStrings) {
            String[] splitRule = ruleString.split(":");
            Function<Part, Boolean> condition;
            String sendTo;

            if (splitRule.length == 1) {
                condition = part -> true;
                sendTo = splitRule[0];
            } else {
                condition = getCondition(splitRule[0]);
                sendTo = splitRule[1];
            }

            rules.add(new Rule(condition, sendTo));
        }

        return rules;
    }

    private Part getPart(String line) {
        String[] categories = line.substring(1, line.length()-1).split(",");
        int x = 0;
        int m = 0;
        int a = 0;
        int s = 0;

        for (String category : categories) {
            String[] splitCategory = category.split("=");
            String key = splitCategory[0];
            int value = Integer.parseInt(splitCategory[1]);

            switch (key) {
                case "x" -> x = value;
                case "m" -> m = value;
                case "a" -> a = value;
                case "s" -> s = value;
            }
        }

        return new Part(x, m, a, s);
    }

    private Function<Part, Boolean> getCondition(String conditionStr) {
        int comparison = Integer.parseInt(conditionStr.substring(2));

        if (conditionStr.contains("<")) {
            return switch (conditionStr.charAt(0)) {
                case 'x' -> part -> part.x < comparison;
                case 'm' -> part -> part.m < comparison;
                case 'a' -> part -> part.a < comparison;
                case 's' -> part -> part.s < comparison;
                default -> throw new RuntimeException("Part category unknow: " + conditionStr.charAt(0));
            };
        } else if (conditionStr.contains(">")) {
            return switch (conditionStr.charAt(0)) {
                case 'x' -> part -> part.x > comparison;
                case 'm' -> part -> part.m > comparison;
                case 'a' -> part -> part.a > comparison;
                case 's' -> part -> part.s > comparison;
                default -> throw new RuntimeException("Part category unknow: " + conditionStr.charAt(0));
            };
        } else {
            throw new RuntimeException("Unknow condition: " + conditionStr);
        }
    }
}
