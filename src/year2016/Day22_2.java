package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day22_2 {
    private record Node(int x, int y) {}

    public String part2(List<String> input)  {
        input = input.subList(2, input.size());

        int maxX = input.stream().map(s -> Integer.parseInt(s
                .split("\\s+")[0]
                .split("-")[1].
                replace("x", "")
        )).max(Integer::compare).get();

        int maxAvail = input.stream().map(s -> Integer.parseInt(s
                .split("\\s+")[3]
                .replace("T", "")
        )).max(Integer::compare).get();

        List<Node> wall = new ArrayList<>();
        Node emptyNode = new Node(-1, -1);

        for (String s : input) {
            String[] splitData = s.split("\\s+");

            int x = Integer.parseInt(s.split("\\s+")[0].split("-")[1].replace("x", ""));
            int y = Integer.parseInt(s.split("\\s+")[0].split("-")[2].replace("y", ""));

            int used = Integer.parseInt(splitData[2].replace("T", ""));

            if (used == 0) {
                emptyNode = new Node(x, y);
            } else if (used > maxAvail) {
                wall.add(new Node(x, y));
            }
        }

        int steps = 0;
        int wallWidth = 1 + Math.min(
                emptyNode.x - wall.getFirst().x,
                wall.getLast().x - emptyNode.x
        );

        steps += emptyNode.y + (maxX - emptyNode.x);    //way to up right
        steps += 2*wallWidth;                           //way around wall
        steps += 5 * (maxX - 1);                        //spiral way to left


        return String.valueOf(steps);
    }
}
