package year2016;

import java.util.*;

public class Day13_1 {
    private final int SIZE = 300;

    private class Node implements Comparable<Node> {
        int x;
        int y;
        int steps;

        public Node(int x, int y, int steps) {
            this.x = x;
            this.y = y;
            this.steps = steps;
        }

        public List<Node> getNeighbours(boolean[][] grid) {
            List<Node> neighbours = new ArrayList<>();

            if (this.y + 1 < SIZE && grid[this.y + 1][this.x]) {
                neighbours.add(new Node(this.x, this.y + 1, this.steps));
            }
            if (this.y - 1 >= 0 && grid[this.y - 1][this.x]) {
                neighbours.add(new Node(this.x, this.y - 1, this.steps));
            }
            if (this.x + 1 < SIZE && grid[this.y][this.x + 1]) {
                neighbours.add(new Node(this.x + 1, this.y, this.steps));
            }
            if (this.x - 1 >= 0 && grid[this.y][this.x - 1]) {
                neighbours.add(new Node(this.x - 1, this.y, this.steps));
            }

            return neighbours;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Node other = (Node) obj;
            if (this.x != other.x)
                return false;
            return this.y == other.y;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.steps, o.steps);
        }
    }

    public String part1(List<String> input)  {
        boolean[][] grid = new boolean[300][300];
        int number = Integer.parseInt(input.getFirst());

        //if result not found, size up
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                int result = x*x + 3*x + 2*x*y + y + y*y + number;
                String binary = Integer.toBinaryString(result);
                grid[y][x] = countMatches(binary, "1") % 2 == 0;
            }
        }

        return String.valueOf(getMinimumSteps(new Node(1, 1, 0), new Node(31, 39, 0), grid));
    }

    private int getMinimumSteps(Node start, Node end, boolean[][] grid) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Map<Node, Integer> distances = new HashMap<>();

        queue.offer(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int dist = current.steps + 1;

            List<Node> neighbours = current.getNeighbours(grid);
            for (final Node neighbour : neighbours) {

                if (dist < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    distances.put(neighbour, dist);
                    queue.remove(neighbour);
                    neighbour.steps = dist;
                    queue.add(neighbour);

                }
            }
        }
        return distances.entrySet().stream().filter(entry -> entry.getKey().equals(end)).toList().stream()
                .map(Map.Entry::getValue).mapToInt(d -> d).min().getAsInt();
    }

    public int countMatches(String s, String c){
        return s.length() - s.replace(c, "").length();
    }
}
