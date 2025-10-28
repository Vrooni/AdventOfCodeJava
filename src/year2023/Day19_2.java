package year2023;

import java.util.*;

public class Day19_2 {
    private enum OPERATION { GREATER, LOWER, GREATER_EQUALS, LOWER_EQUALS }
    record Condition(String category, OPERATION operation, int comparison) {}

    record Rule(Condition condition, String sendTo) {}
    record TreeBranch(List<Condition> conditions) {
        TreeBranch copy() {
            return new TreeBranch(new ArrayList<>(this.conditions));
        }
    }

    record Range(int from, int to) {
        int length() {
            return to - from + 1;
        }
    }

    public String part2(List<String> input) {
        Map<String, List<Rule>> workflows = new HashMap<>();

        for (String line : input) {
            if (line.isEmpty()) {
                break;
            }

            String name = line.substring(0, line.indexOf("{"));
            workflows.put(name, getRules(line));
        }

        List<TreeBranch> treeBranches = new ArrayList<>();
        getTreeBranches(treeBranches, new TreeBranch(new ArrayList<>()), workflows, "in", 0);

        long result = 0;
        for (TreeBranch treeBranch : treeBranches) {
            Range x = new Range(1, 4000);
            Range m = new Range(1, 4000);
            Range a = new Range(1, 4000);
            Range s = new Range(1, 4000);

            for (Condition condition : treeBranch.conditions) {
                switch (condition.category) {
                    case "x" -> x = getNewRange(x, condition);
                    case "m" -> m = getNewRange(m, condition);
                    case "a" -> a = getNewRange(a, condition);
                    case "s" -> s = getNewRange(s, condition);
                    default -> throw new RuntimeException("Unknown category: " + condition.category);
                }
            }

            System.out.println();
            result += (long) x.length() * m.length() * a.length() * s.length();
        }

        return String.valueOf(result);
    }

    private List<Rule> getRules(String line) {
        List<Rule> rules = new ArrayList<>();
        String[] ruleStrings = line.substring(line.indexOf("{") + 1, line.indexOf("}")).split(",");

        for (String ruleString : ruleStrings) {
            String[] splitRule = ruleString.split(":");
            Condition condition;
            String sendTo;

            if (splitRule.length == 1) {
                condition = null;
                sendTo = splitRule[0];
            } else {
                condition = getCondition(splitRule[0]);
                sendTo = splitRule[1];
            }

            rules.add(new Rule(condition, sendTo));
        }

        return rules;
    }

    private Condition getCondition(String condition) {
        if (condition.contains("<")) {
            String[] splitCondition = condition.split("<");
            return new Condition(splitCondition[0], OPERATION.LOWER, Integer.parseInt(splitCondition[1]));
        }

        else if (condition.contains(">")) {
            String[] splitCondition = condition.split(">");
            return new Condition(splitCondition[0], OPERATION.GREATER, Integer.parseInt(splitCondition[1]));
        }

        else {
            throw new RuntimeException("Cannot create condition: " + condition);
        }
    }

    private void getTreeBranches(List<TreeBranch> treeBranches, TreeBranch treeBranch,
                                 Map<String, List<Rule>> workflows, String workflow, int ruleIndex) {
        if (workflow.equals("A")) {
            treeBranches.add(treeBranch);
            return;
        } else if (workflow.equals("R")) {
            return;
        }

        List<Rule> rules = workflows.get(workflow);
        Rule rule = rules.get(ruleIndex);

        Condition condition = rule.condition;
        if (condition == null) {
            getTreeBranches(treeBranches, treeBranch, workflows, rule.sendTo, 0);
            return;
        }

        TreeBranch trueBranch = treeBranch.copy();
        trueBranch.conditions.add(condition);
        getTreeBranches(treeBranches, trueBranch, workflows, rule.sendTo, 0);

        TreeBranch falseBranch = treeBranch.copy();
        falseBranch.conditions.add(invert(condition));
        getTreeBranches(treeBranches, falseBranch, workflows, workflow, ruleIndex + 1);
    }

    private Condition invert(Condition condition) {
        OPERATION operation =  switch (condition.operation) {
            case OPERATION.LOWER -> OPERATION.GREATER_EQUALS;
            case OPERATION.GREATER -> OPERATION.LOWER_EQUALS;
            default -> throw new RuntimeException("Unknow operation: " + condition.operation);
        };

        return new Condition(condition.category, operation, condition.comparison);
    }

    private Range getNewRange(Range range, Condition condition) {
        int from = range.from;
        int to = range.to;

        switch (condition.operation) {
            case LOWER -> to = Math.min(to, condition.comparison - 1);
            case LOWER_EQUALS -> to = Math.min(to, condition.comparison);
            case GREATER -> from = Math.max(from, condition.comparison + 1);
            case GREATER_EQUALS -> from = Math.max(from, condition.comparison);
        }

        return new Range(from, to);
    }
}