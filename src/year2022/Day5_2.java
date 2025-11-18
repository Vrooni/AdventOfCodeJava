package year2022;

import java.util.ArrayList;
import java.util.List;

public class Day5_2 {
    static class Move {
        final int amount;
        final int from;
        final int to;

        Move(String line) {
            line = line.replaceAll(" ","");
            line = line.replace("move", "");
            String[] temp1 = line.split("from");
            String[] temp2 = temp1[1].split("to");

            this.amount = Integer.parseInt(temp1[0]);
            this.from = Integer.parseInt(temp2[0]) - 1;
            this.to = Integer.parseInt(temp2[1]) - 1;
        }

        public void move(List<List<Character>> crates) {
            for (int i = 0; i < amount; i++) {
                crates.get(to).addFirst(crates.get(from).getFirst());
                crates.get(from).removeFirst();
            }
        }

        public void moveInOne(List<List<Character>> crates) {
            for (int i = amount - 1; i >= 0; i--) {
                crates.get(to).addFirst(crates.get(from).get(i));
                crates.get(from).remove(i);
            }
        }
    }

    public String part2(List<String> lines) {
        List<List<Character>> crates = new ArrayList<>();

        boolean cratesReady = false;
        for (String line : lines) {
            if (!cratesReady) {
                cratesReady = addCrates(line, crates);
            }

            if (line.startsWith("move")) {
                Move move = new Move(line);
                move.moveInOne(crates);
            }
        }

        StringBuilder result = new StringBuilder();
        for (List<Character> crate : crates) {
            result.append(crate.getFirst());
        }

        return result.toString();
    }

    private boolean addCrates(String line, List<List<Character>> crates) {
        for (int i = 1; i < line.length(); i+=4) {
            char stack = line.charAt(i);

            if (stack  == '1') {
                return true;
            } else if (stack == ' ') {
                continue;
            }

            while (crates.size() <= i/4) {
                crates.add(new ArrayList<>());
            }
            crates.get(i/4).add(line.charAt(i));
        }

        return false;
    }
}
