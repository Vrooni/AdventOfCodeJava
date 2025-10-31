package year2018;

import java.util.*;

public class Day7_1 {
    record Step(String step, List<String> previousSteps, List<Step> nextSteps) implements Comparable<Step> {
        @Override
        public int compareTo(Step o) {
            return this.step.compareTo(o.step);
        }
    }

    public String part1(List<String> input) {
        Map<String, Step> steps = new HashMap<>();

        for (String instruction : input) {
            String[] splitInstruction = instruction.split(" ");
            Step step1 = steps.computeIfAbsent(splitInstruction[1], _ ->
                    new Step(
                            splitInstruction[1],
                            new ArrayList<>(),
                            new ArrayList<>()
                    )
            );

            Step step2 = steps.computeIfAbsent(splitInstruction[7], _ ->
                    new Step(
                            splitInstruction[7],
                            new ArrayList<>(),
                            new ArrayList<>()
                    )
            );

            step1.nextSteps.add(step2);
            step2.previousSteps.add(step1.step);
        }

        List<String> order = new ArrayList<>();
        Queue<Step> queue = new PriorityQueue<>();

        List<Step> firstSteps = steps.values().stream().filter(step -> step.previousSteps.isEmpty()).toList();
        queue.addAll(firstSteps);

        StringBuilder result = new StringBuilder();
        while (!queue.isEmpty()) {
            Step current = queue.poll();
            order.add(current.step);
            result.append(current.step);

            for (Step nextStep : current.nextSteps) {
                if (new HashSet<>(order).containsAll(nextStep.previousSteps)) {
                    queue.add(nextStep);
                }
            }
        }

        return result.toString();
    }
}
