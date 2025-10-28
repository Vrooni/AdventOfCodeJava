package year2017;

import java.util.List;

public class Day24_1 {

    private static class Component {
        int port1;
        int port2;
        boolean visited = false;

        public Component(int port1, int port2) {
            this.port1 = port1;
            this.port2 = port2;
        }

        public boolean connects(int port) {
            return !this.visited && (this.port1 == port || this.port2 == port);
        }
    }

    public String part1(List<String> input) {
        List<Component> components = input.stream()
                .map(line -> {
                    String[] splitLine = line.split("/");
                    return new Component(
                            Integer.parseInt(splitLine[0]),
                            Integer.parseInt(splitLine[1])
                    );
                })
                .toList();

        return String.valueOf(getMaxStrength(components, 0, 0));
    }

    private int getMaxStrength(List<Component> components, int port1, int port2) {
        int maxStrenght = 0;

        for (Component component : components) {
            if (component.connects(port2)) {
                int p1 = component.port1 == port2 ? component.port1 : component.port2;
                int p2 = component.port1 == port2 ? component.port2 : component.port1;
                component.visited = true;
                maxStrenght = Math.max(maxStrenght, getMaxStrength(components, p1, p2));
                component.visited = false;
            }
        }

        return  port1 + port2 + maxStrenght;
    }
}