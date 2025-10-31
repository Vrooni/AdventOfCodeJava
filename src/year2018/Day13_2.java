package year2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day13_2 {
    public record Position(int x, int y) {}

    public enum Direction {
        UP, RIGHT, DOWN, LEFT;

        public Direction next() {
            return values()[(this.ordinal() + 1) % values().length];
        }

        public Direction previous() {
            return values()[(this.ordinal() + values().length - 1) % values().length];
        }
    }


    private enum Decision {
        LEFT, STRAIGHT, RIGHT;

        public Decision next() {
            return values()[(this.ordinal() + 1) % values().length];
        }
    }
    private static class Cart implements Comparable<Cart> {
        Position position;
        Direction direction;
        Decision decision = Decision.LEFT;
        boolean crashed = false;

        public Cart(Position position, Direction direction) {
            this.position = position;
            this.direction = direction;
        }

        @Override
        public int compareTo(Cart o) {
            int compareY = Integer.compare(this.position.y(), o.position.y());
            return compareY == 0 ? Integer.compare(this.position.x(), o.position.x()) : compareY;
        }
    }

    public String part2(List<String> input) {
        List<List<Character>> rails = new ArrayList<>();
        List<Cart> carts = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            List<Character> railLine = new ArrayList<>();

            for (int j = 0; j < input.getFirst().length(); j++) {
                char rail = input.get(i).charAt(j);

                switch (rail) {
                    case '^' -> {
                        carts.add(new Cart(new Position(j, i), Direction.UP));
                        railLine.add('|');
                    }
                    case 'v' -> {
                        carts.add(new Cart(new Position(j, i), Direction.DOWN));
                        railLine.add('|');
                    }
                    case '<' -> {
                        carts.add(new Cart(new Position(j, i), Direction.LEFT));
                        railLine.add('-');
                    }
                    case '>' -> {
                        carts.add(new Cart(new Position(j, i), Direction.RIGHT));
                        railLine.add('-');
                    }
                    default -> railLine.add(rail);
                }
            }

            rails.add(railLine);
        }

        while (true) {
            for (Cart cart : carts) {
                move(cart, rails);
                checkCollision(carts);
            }

            carts = new ArrayList<>(carts.stream().filter(cart -> !cart.crashed).toList());

            if (carts.size() == 1) {
                Position lastCart = carts.getFirst().position;
                return lastCart.x() + "," + lastCart.y();
            }

            Collections.sort(carts);
        }
    }

    private void move(Cart cart, List<List<Character>> rails) {
        cart.position = switch (cart.direction) {
            case UP -> new Position(cart.position.x(), cart.position.y()-1);
            case DOWN -> new Position(cart.position.x(), cart.position.y()+1);
            case LEFT -> new Position(cart.position.x()-1, cart.position.y());
            case RIGHT -> new Position(cart.position.x()+1, cart.position.y());
        };

        char rail = rails.get(cart.position.y()).get(cart.position.x());
        switch (rail) {
            case '/' -> {
                switch (cart.direction) {
                    case UP, DOWN -> cart.direction = cart.direction.next();
                    case LEFT, RIGHT -> cart.direction = cart.direction.previous();
                }
            }
            case '\\' -> {
                switch (cart.direction) {
                    case UP, DOWN -> cart.direction = cart.direction.previous();
                    case LEFT, RIGHT -> cart.direction = cart.direction.next();
                }
            }
            case '+' -> {
                cart.direction = switch (cart.decision) {
                    case LEFT -> cart.direction.previous();
                    case STRAIGHT -> cart.direction;
                    case RIGHT -> cart.direction.next();
                };
                cart.decision = cart.decision.next();
            }
        }
    }

    private void checkCollision(List<Cart> carts) {
        for (int i = 0; i < carts.size()-1; i++) {
            for (int j = i+1; j < carts.size(); j++) {
                Position pos1 = carts.get(i).position;
                Position pos2 = carts.get(j).position;

                if (pos1.x() == pos2.x() && pos1.y() == pos2.y()) {
                    carts.get(i).crashed = true;
                    carts.get(j).crashed = true;
                    return;
                }
            }
        }

    }
}
