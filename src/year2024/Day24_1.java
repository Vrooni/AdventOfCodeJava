package year2024;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day24_1 {
    private static class Gate {
        String input1;
        String operation;
        String input2;
        String output;

        public Gate(String input1, String operation, String input2, String output) {
            this.input1 = input1;
            this.operation = operation;
            this.input2 = input2;
            this.output = output;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Gate gate)) return false;
            return Objects.equals(input1, gate.input1)
                    && Objects.equals(operation, gate.operation)
                    && Objects.equals(input2, gate.input2)
                    && Objects.equals(output, gate.output);
        }

        @Override
        public int hashCode() {
            return Objects.hash(input1, operation, input2, output);
        }

        @Override
        public String toString() {
            return input1 + " " + operation + " " + input2 + " -> " + output;
        }
    }

    public String part1(List<String> input) {
        Map<String, Boolean> wires = new HashMap<>();
        List<String> outputWires = new ArrayList<>();
        List<Gate> gates = new ArrayList<>();

        readInput(input, wires, gates, outputWires);
        String result = getResult(wires, outputWires, gates);

        return String.valueOf(Long.parseLong(result, 2));
    }

    private String getResult(Map<String, Boolean> wires, List<String> outputWires, List<Gate> gates) {
        while (!wires.keySet().containsAll(outputWires)) {
            for (Gate gate : gates) {
                Boolean wire1 = wires.get(gate.input1);
                Boolean wire2 = wires.get(gate.input2);

                if (wire1 != null && wire2 != null) {
                    boolean result = switch (gate.operation) {
                        case "OR" -> wire1 || wire2;
                        case "XOR" -> !wire1.equals(wire2);
                        case "AND" -> wire1 && wire2;
                        default -> false;
                    };

                    wires.put(gate.output, result);
                }
            }
        }

        List<String> sortedWires = wires.keySet().stream()
                .filter(entry -> entry.startsWith("z"))
                .sorted((o1, o2) -> Integer.compare(
                        Integer.parseInt(o2.replace("z", "")),
                        Integer.parseInt(o1.replace("z", ""))
                ))
                .toList();

        return sortedWires.stream().map(wire -> wires.get(wire) ? "1" : "0").collect(Collectors.joining(""));
    }

    private void readInput(List<String> input, Map<String, Boolean> wires, List<Gate> gates, List<String> outputWires) {
        Pattern wirePattern = Pattern.compile("^(.*): ([01])");
        Pattern gatePattern = Pattern.compile("^(.*) (OR|XOR|AND) (.*) -> (.*)");

        for (String line : input) {
            Matcher wireMatcher = wirePattern.matcher(line);
            Matcher gateMatcher = gatePattern.matcher(line);

            if (wireMatcher.find()) {
                wires.put(wireMatcher.group(1), wireMatcher.group(2).equals("1"));
            }

            if (gateMatcher.find()) {
                gates.add(new Gate(gateMatcher.group(1), gateMatcher.group(2), gateMatcher.group(3), gateMatcher.group(4)));
            }
        }

        for (Gate gate : gates) {
            if (gate.output.startsWith("z")) {
                outputWires.add(gate.output);
            }
        }
    }
}
