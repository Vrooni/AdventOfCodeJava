package year2018;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day9_2 {
    public String part2(List<String> lines) {
        String[] input = lines.getFirst().split(" ");

        int players = Integer.parseInt(input[0]);
        int lastMarble = Integer.parseInt(input[6]);

        return String.valueOf(getMaxScore(players, lastMarble * 100));
    }

    private long getMaxScore(int players, int lastMarble) {
        List<Long> scores = new ArrayList<>(Collections.nCopies(players, 0L));
        ArrayDeque<Long> circle = new ArrayDeque<>();
        circle.addLast(0L);

        for (long marble = 1; marble <= lastMarble; marble++) {
            if (marble % 23 == 0) {
                rotate(circle, -7);
                int player = (int) marble % players;
                long score = scores.get(player) + marble + circle.removeFirst();
                scores.set(player, score);
            } else {
                rotate(circle, 2);
                circle.addFirst(marble);
            }
        }

        return scores.stream().mapToLong(i -> i).max().getAsLong();
    }

    private void rotate(ArrayDeque<Long> circle, int steps) {
        if (steps < 0) {
            for (int i = steps; i < 0; i++) {
                long e = circle.removeLast();
                circle.addFirst(e);
            }
        } else {
            for (int i = 0; i < steps; i++) {
                long e = circle.removeFirst();
                circle.addLast(e);
            }
        }
    }
}
