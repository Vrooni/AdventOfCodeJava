package year2018;

import java.util.*;

public class Day16_1 {
    private record Sample(int[] input, int[] outcome, int[] instruction, List<Opcode> opcodes) { }

    private enum Opcode {
        ADDR, ADDI, MULR, MULI, BANR, BANI, BORR, BORI, SETR, SETI, GTIR, GTRI, GTRR, EQIR, EQRI, EQRR
    }

    public String part1(List<String> input) {
        List<Sample> samples = new ArrayList<>();
        List<int[]> tests = new ArrayList<>();

        readInput(input, samples, tests);
        return String.valueOf(samples.stream().filter(sample -> sample.opcodes.size() >= 3).count());
    }

    private void readInput(List<String> input, List<Sample> samples, List<int[]> tests) {
        int i;
        for (i = 0; input.get(i).startsWith("Before:"); i += 4) {
            samples.add(new Sample(
                    Arrays.stream(input.get(i)
                                    .replace("Before: ", "")
                                    .replace("[", "")
                                    .replace("]", "").split(", "))
                            .mapToInt(Integer::parseInt)
                            .toArray(),
                    Arrays.stream(input.get(i+2)
                                    .replace("After:  ", "")
                                    .replace("[", "")
                                    .replace("]", "").split(", "))
                            .mapToInt(Integer::parseInt)
                            .toArray(),
                    Arrays.stream(input.get(i+1).split(" "))
                            .mapToInt(Integer::parseInt)
                            .toArray(),
                    new ArrayList<>()
            ));
        }

        for (i = i+2; i < input.size(); i++) {
            tests.add(Arrays.stream(input.get(i).split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray()
            );
        }

        samples.forEach(this::getPossibleOpcodes);
    }

    private void getPossibleOpcodes(Sample sample) {
        getPossibleCalculations(sample);
        getPossibleGates(sample);
        getPossibleAssignments(sample);
        getPossibleComparisons(sample);
    }

    private void getPossibleCalculations(Sample sample) {
        int a = sample.instruction[1];
        int b = sample.instruction[2];
        int c = sample.instruction[3];

        //addr
        int[] result = sample.input.clone();
        result[c] = result[a] + result[b];
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.ADDR);
        }

        //addi
        result = sample.input.clone();
        result[c] = result[a] + b;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.ADDI);
        }

        //mulr
        result = sample.input.clone();
        result[c] = result[a] * result[b];
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.MULR);
        }

        //muli
        result = sample.input.clone();
        result[c] = result[a] * b;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.MULI);
        }
    }

    private void getPossibleGates(Sample sample) {
        int a = sample.instruction[1];
        int b = sample.instruction[2];
        int c = sample.instruction[3];

        //banr
        int[] result = sample.input.clone();
        result[c] = result[a] & result[b];
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.BANR);
        }

        //bani
        result = sample.input.clone();
        result[c] = result[a] & b;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.BANI);
        }

        //borr
        result = sample.input.clone();
        result[c] = result[a] | result[b];
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.BORR);
        }

        //bori
        result = sample.input.clone();
        result[c] = result[a] | b;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.BORI);
        }
    }


    private void getPossibleAssignments(Sample sample) {
        int a = sample.instruction[1];
        int c = sample.instruction[3];

        //setr
        int[] result = sample.input.clone();
        result[c] = result[a];
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.SETR);
        }

        //seti
        result = sample.input.clone();
        result[c] = a;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.SETI);
        }
    }

    private void getPossibleComparisons(Sample sample) {
        int a = sample.instruction[1];
        int b = sample.instruction[2];
        int c = sample.instruction[3];

        //gtir
        int[] result = sample.input.clone();
        result[c] = a > result[b] ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.GTIR);
        }

        //gtri
        result = sample.input.clone();
        result[c] = result[a] > b ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.GTRI);
        }

        //gtrr
        result = sample.input.clone();
        result[c] = result[a] > result[b] ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.GTRR);
        }

        //eqir
        result = sample.input.clone();
        result[c] = a == result[b] ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.EQIR);
        }

        //eqri
        result = sample.input.clone();
        result[c] = result[a] == b ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.EQRI);
        }

        //eqrr
        result = sample.input.clone();
        result[c] = result[a] == result[b] ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.EQRR);
        }
    }
}
