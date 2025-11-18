package year2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2_1 {
    public String part1(List<String> lines) {
        Map<String, Map<String, Integer>> myMovePoints = new HashMap<>();
        Map<String, Integer> movePoints;

        //X
        movePoints = Map.of("C", 6 + 1, "A", 3 + 1, "B", 1);
        myMovePoints.put("X", movePoints);

        //Y
        movePoints = Map.of("A", 6 + 2, "B", 3 + 2, "C", 2);
        myMovePoints.put("Y", movePoints);

        //Z
        movePoints = Map.of("B", 6 + 3, "C", 3 + 3, "A", 3);
        myMovePoints.put("Z", movePoints);

        int points = 0;
        for (String line : lines) {
            String[] moves = line.split(" ");

            String otherMove = moves[0];
            String myMove = moves[1];

            points += myMovePoints.get(myMove).get(otherMove);
        }

        return String.valueOf(points);
    }
}
