package year2018;

import java.util.*;

public class Day3_1 {
    public record Position(int x, int y) {}
    private record Claim(String id, List<Position> squares) {}

    public String part1(List<String> input) {
        List<Claim> claims = input.stream().map(this::getClaim).toList();
        Map<Position, Integer> usageCount = new HashMap<>();

        for (Claim claim : claims) {
            for (Position square : claim.squares) {
                usageCount.put(square, usageCount.getOrDefault(square, 0) + 1);
            }
        }

        return String.valueOf(usageCount.values().stream().filter(usage -> usage > 1).count());
    }

    private Claim getClaim(String claim) {
        String[] splitClaim = claim.split(" ");

        String id = splitClaim[0].replace("#", "");
        String[] distance = splitClaim[2].replace(":", "").split(",");
        String[] size = splitClaim[3].split("x");

        Position from = new Position(
                Integer.parseInt(distance[0]),
                Integer.parseInt(distance[1])
        );
        Position to = new Position(
                from.x() + Integer.parseInt(size[0]),
                from.y() + Integer.parseInt(size[1])
        );

        List<Position> squares = new ArrayList<>();
        for (int i = from.y(); i < to.y(); i++) {
            for (int j = from.x(); j < to.x(); j++) {
                squares.add(new Position(j, i));
            }
        }

        return new Claim(id, squares);
    }
}
