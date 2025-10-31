package year2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day8_2 {
    private record Node(List<Node> children, List<Integer> metadata) {}

    public String part2(List<String> input) {
        List<Integer> lines = Arrays.stream(input.getFirst()
                .split(" "))
                .map(Integer::parseInt)
                .toList();

        Node head = getNode(new ArrayList<>(lines));

        return String.valueOf(getValue(head));
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

    private int getValue(Node node) {
        if (node.children.isEmpty()) {
            return sum(node.metadata);
        }

        int value = 0;
        for (Integer index : node.metadata) {
            value += index > 0 && index <= node.children.size()
                    ? getValue(node.children.get(index-1))
                    : 0;
        }

        return value;
    }

    public int sum(List<Integer> list) {
        int sum = 0;

        for (Integer i : list) {
            sum += i;
        }

        return sum;
    }
}
