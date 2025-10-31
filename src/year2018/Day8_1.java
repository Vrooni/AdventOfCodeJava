package year2018;

import java.util.*;

public class Day8_1 {
    private record Node(List<Node> children, List<Integer> metadata) {}

    public String part1(List<String> input) {
        List<Integer> lines = Arrays.stream(input.getFirst()
                .split(" "))
                .map(Integer::parseInt)
                .toList();

        Node head = getNode(new ArrayList<>(lines));

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        int metaData = 0;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            metaData += sum(current.metadata);
            queue.addAll(current.children);
        }

        return String.valueOf(metaData);
    }

    private Node getNode(List<Integer> input) {
        int sizeChildren = input.removeFirst();
        int sizeMetaData = input.removeFirst();

        List<Node> children = new ArrayList<>();
        for (int i = 0; i < sizeChildren; i++) {
            children.add(getNode(input));
        }

        List<Integer> metaData = new ArrayList<>();
        for (int i = 0; i < sizeMetaData; i++) {
            metaData.add(input.removeFirst());
        }

        return new Node(children, metaData);
    }

    public int sum(List<Integer> list) {
        int sum = 0;

        for (Integer i : list) {
            sum += i;
        }

        return sum;
    }
}
