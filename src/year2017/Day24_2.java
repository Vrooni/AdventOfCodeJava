package year2017;

import java.util.List;

public class Day24_2 {
    record Score(int strength, int length) { }

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

    public String part2(List<String> input) {
        List<Component> components = input.stream()
                .map(line -> {
                    String[] splitLine = line.split("/");
                    return new Component(
                            Integer.parseInt(splitLine[0]),
                            Integer.parseInt(splitLine[1])
                    );
                })
                .toList();

        return String.valueOf(getMaxScore(components,0, 0).strength);
    }

    private Score getMaxScore(List<Component> components, int port1, int port2) {
        Score maxScore = new Score(0, 0);

        for (Component component : components) {
            if (component.connects(port2)) {
                int p1 = component.port1 == port2 ? component.port1 : component.port2;
                int p2 = component.port1 == port2 ? component.port2 : component.port1;
                component.visited = true;
                maxScore = max(maxScore, getMaxScore(components, p1, p2));
                component.visited = false;
            }
        }

        return new Score(port1 + port2 + maxScore.strength, maxScore.length + 1);
    }

    private Score max(Score score1, Score score2) {
        if (score1.length == score2.length) {
            return score1.strength > score2.strength ? score1 : score2;
        } else {
            return score1.length > score2.length ? score1 : score2;
        }
    }
}