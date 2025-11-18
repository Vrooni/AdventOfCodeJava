package year2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2_2 {
    public String part2(List<String> lines) {
        Map<String, Map<String, Integer>> myMovePoints = new HashMap<>();
        Map<String, Integer> movePoints;

        //A
        movePoints = Map.of("X", 3, "Y", 1 + 3, "Z", 2 + 6);
        myMovePoints.put("A", movePoints);

        //B
        movePoints = Map.of("X", 1, "Y", 2 + 3, "Z", 3 + 6);
        myMovePoints.put("B", movePoints);

        //C
        movePoints = Map.of("X", 2, "Y", 3 + 3, "Z", 1 + 6);
        myMovePoints.put("C", movePoints);

        int points = 0;
        for (String line : lines) {
            String[] moves = line.split(" ");

            String otherMove = moves[0];
            String myWinningStatus = moves[1];

            points += myMovePoints.get(otherMove).get(myWinningStatus);
        }

        return String.valueOf(points);
    }
}
