package year2023;

import java.util.*;

public class Day20_1 {
    private int highPulses = 0;
    private int lowPulses = 0;

    private enum Type {
        FLIP_FLOP, CONJUNCTION, BROADCAST
    }

    private record Module(Type type, List<String> outputs) {}
    private record Signal(String formModule, String toModule, Boolean highPulse) {}

    public String part1(List<String> input) {

        Map<String, Module> modules = new HashMap<>();
        Map<String, Boolean> flipFlops = new HashMap<>();
        Map<String, Map<String, Boolean>> conjunctions = new HashMap<>();
        getModules(input, modules, flipFlops, conjunctions);

        for (int i = 0; i < 1000; i++) {
            pushButton(modules, flipFlops, conjunctions);
        }

        return String.valueOf(highPulses * lowPulses);
    }

    private void pushButton(Map<String, Module> modules,
                            Map<String, Boolean> flipFlops,
                            Map<String, Map<String, Boolean>> conjunctions) {
        Queue<Signal> signals = new LinkedList<>();
        signals.add(new Signal("button", "broadcaster", false));

        while (!signals.isEmpty()) {
            Signal signal = signals.poll();
            Module module = modules.get(signal.toModule);

            if (signal.highPulse) {
                highPulses++;
            } else {
                lowPulses++;
            }

            if (module == null) {
                continue;
            }

            if (module.type == Type.BROADCAST) {
                for (String output : module.outputs) {
                    signals.add(new Signal(signal.toModule, output, signal.highPulse));
                }
            }

            else if (module.type == Type.FLIP_FLOP) {
                if (signal.highPulse) {
                    continue;
                }

                boolean flipFlop = flipFlops.get(signal.toModule);
                flipFlops.put(signal.toModule, !flipFlop);

                for (String output : module.outputs) {
                    signals.add(new Signal(signal.toModule, output, !flipFlop));
                }
            }

            else {
                Map<String, Boolean> conjunction = conjunctions.get(signal.toModule);
                conjunction.put(signal.formModule, signal.highPulse);
                boolean highPulse = !conjunction.values().stream().allMatch(input -> input);

                for (String output : module.outputs) {
                    signals.add(new Signal(signal.toModule, output, highPulse));
                }
            }
        }
    }

    private void getModules(List<String> input,
                            Map<String, Module> modules,
                            Map<String, Boolean> flipFlops,
                            Map<String, Map<String, Boolean>> conjunctions) {
        for (String line : input) {
            Type type;
            String replaceTarget;

            if (line.startsWith("%")) {
                type = Type.FLIP_FLOP;
                replaceTarget = "%";
            } else if (line.startsWith("&")) {
                type = Type.CONJUNCTION;
                replaceTarget = "&";
            } else if (line.startsWith("broadcaster")) {
                type = Type.BROADCAST;
                replaceTarget = "";
            } else {
                throw new RuntimeException("Unknown Module: " + line);
            }

            String[] splitLine = line.split(" -> ");

            String name = splitLine[0].replace(replaceTarget, "");
            List<String> outputs = new ArrayList<>(Arrays.asList(splitLine[1].split(", ")));

            modules.put(name, new Module(type, outputs));
        }

        for (Map.Entry<String, Module> module : modules.entrySet()) {
            if (module.getValue().type == Type.FLIP_FLOP) {
                flipFlops.put(module.getKey(), false);
            }

            else if (module.getValue().type == Type.CONJUNCTION) {
                conjunctions.put(module.getKey(), getInputs(module.getKey(), modules));
            }
        }
    }

    private Map<String, Boolean> getInputs(String name, Map<String, Module> modules) {
        Map<String, Boolean> inputs = new HashMap<>();

        for (Map.Entry<String, Module> module2 : modules.entrySet()) {
            if (module2.getValue().outputs.contains(name)) {
                inputs.put(module2.getKey(), false);
            }
        }

        return inputs;
    }
}