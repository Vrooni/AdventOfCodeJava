package year2018;

import java.util.*;

public class Day7_2 {
    record Step(String step, List<String> previousSteps, List<Step> nextSteps) implements Comparable<Step> {
        @Override
        public int compareTo(Step o) {
            return this.step.compareTo(o.step);
        }
    }

    private static class Worker {
        Step step = null;
        int time = -1;

        public boolean isWorking() {
            return step != null;
        }
    }

    public String part2(List<String> input) {
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

        List<Worker> workers = List.of(
                new Worker(),
                new Worker(),
                new Worker(),
                new Worker(),
                new Worker()
        );

        List<String> order = new ArrayList<>();
        Queue<Step> queue = new PriorityQueue<>();

        List<Step> firstSteps = steps.values().stream().filter(step -> step.previousSteps.isEmpty()).toList();
        queue.addAll(firstSteps);

        int time = 0;
        while (!queue.isEmpty() || workers.stream().anyMatch(Worker::isWorking)) {
            List<String> finishedSteps = new ArrayList<>();

            for (Worker worker : workers) {
                //give him a new job
                if (!worker.isWorking() && !queue.isEmpty()) {
                    Step current = queue.poll();
                    worker.step = current;
                    worker.time = (char) (current.step.charAt(0) - 64) + 60;
                }

                //let him work
                if (worker.isWorking()) {
                    worker.time--;
                }

                //he finished working
                if (worker.time == 0) {
                    order.add(worker.step.step);
                    finishedSteps.add(worker.step.step);
                    worker.step = null;
                    worker.time = -1;
                }
            }

            for (String finishedStep : finishedSteps) {
                for (Step nextStep : steps.get(finishedStep).nextSteps) {
                    if (new HashSet<>(order).containsAll(nextStep.previousSteps)) {
                        queue.add(nextStep);
                    }
                }
            }

            time++;
        }

        return String.valueOf(time);
    }
}
