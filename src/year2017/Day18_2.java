package year2017;

import java.util.*;

public class Day18_2 {

    private enum State {
        RUN, WAIT, TERMINATED
    }

    private static class Program {
        Map<String, Long> registers = new HashMap<>();
        Queue<Long> queue = new LinkedList<>();
        State state = State.RUN;
        int index = 0;
        int send = 0;

        public void run(List<String> instructions, Program other) {
            while (state == State.RUN) {
                String[] splitInstruction = instructions.get(index).split(" ");

                switch (splitInstruction[0]) {
                    case "set" -> registers.put(splitInstruction[1], getValue(registers, splitInstruction[2]));
                    case "add" -> registers.put(splitInstruction[1], getValue(registers, splitInstruction[1]) + getValue(registers, splitInstruction[2]));
                    case "mul" -> registers.put(splitInstruction[1], getValue(registers, splitInstruction[1]) * getValue(registers, splitInstruction[2]));
                    case "mod" -> registers.put(splitInstruction[1], getValue(registers, splitInstruction[1]) % getValue(registers, splitInstruction[2]));
                    case "jgz" -> index += getValue(registers, splitInstruction[1]) > 0 ? (int) (getValue(registers, splitInstruction[2]) - 1) : 0;
                    case "snd" -> send(other, splitInstruction[1]);
                    case "rcv" -> receive(splitInstruction[1]);
                }

                index++;

                if (index < 0 || index >= instructions.size()) {
                    state = State.TERMINATED;
                }
            }
        }

        private void send(Program other, String key) {
            other.queue.add(getValue(registers, key));
            other.state = other.state == State.WAIT ? State.RUN : other.state;
            send++;
        }

        private void receive(String key) {
            if (queue.isEmpty()) {
                state = State.WAIT;
                index--;
                return;
            }

            registers.put(key, queue.poll());
        }

        private long getValue(Map<String, Long> registers, String key) {
            return isNumeric(key) ? Long.parseLong(key) : registers.getOrDefault(key, 0L);
        }

        public boolean isNumeric(String str) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    public String part2(List<String> instructions) {
        Program program0 = new Program();
        Program program1 = new Program();

        program0.registers.put("p", 0L);
        program1.registers.put("p", 1L);

        while (program0.state == State.RUN && program1.state != State.TERMINATED) {
            program0.run(instructions, program1);
            program1.run(instructions, program0);
        }

        return String.valueOf(program1.send);
    }
}
