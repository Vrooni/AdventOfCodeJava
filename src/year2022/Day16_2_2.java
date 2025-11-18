package year2022;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO ist eine andere Lösung
public class Day16_2_2 {
    public record State(Valve valve, int minute, List<Valve> openValves, int nrOfOtherPlayers) {
    }

    public class Valve implements Comparable<Valve> {
        private final String name;
        private int flowRate;

        private final Set<String> neighbourNames = new HashSet<>();

        public Valve(final String name, final int flowRate) {
            this.name = name;
            this.flowRate = flowRate;
        }

        public void addNeighbourName(final String neighbourName) {
            neighbourNames.add(neighbourName);
        }

        public String getName() {
            return this.name;
        }

        public int getFlowRate() {
            return this.flowRate;
        }

        public void setFlowRate(final int flowRate) {
            this.flowRate = flowRate;
        }

        public Set<String> getNeighbourNames() {
            return this.neighbourNames;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
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
            final Valve other = (Valve) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int compareTo(final Valve o) {
            return Integer.compare(this.flowRate, o.flowRate);
        }
    }

    private Map<State, Integer> cache = new HashMap<>();

    private List<Valve> parseValves(final List<String> input) {
        final List<Valve> valves = new ArrayList<>();
        final Pattern namePattern = Pattern.compile("([A-Z]{2})");
        final Pattern flowPattern = Pattern.compile("\\d+");
        for (final String line : input) {
            final Matcher flowMatcher = flowPattern.matcher(line);
            flowMatcher.find();
            final int flow = Integer.parseInt(flowMatcher.group());

            final Matcher nameMatcher = namePattern.matcher(line);
            nameMatcher.find();
            final String name = nameMatcher.group();
            final Valve valve = new Valve(name, flow);
            while (nameMatcher.find()) {
                final String neighbourName = nameMatcher.group();
                valve.addNeighbourName(neighbourName);
            }
            valves.add(valve);
        }
        return valves;
    }

    public int calcPressure(final Valve start, final int minute, final List<Valve> opened, final List<Valve> valves, final int nrOfOtherPlayers) {
        if (minute == 0) {
            final Valve aa = valves.stream().filter(f -> f.getName().equals("AA")).findFirst()
                    .orElseThrow(NoSuchElementException::new);
            return nrOfOtherPlayers > 0 ? calcPressure(aa, 26, opened, valves, nrOfOtherPlayers - 1) : 0;
        }
        final State state = new State(start, minute, opened, nrOfOtherPlayers);
        if (cache.keySet().contains(state)) {
            return cache.get(state);
        }

        int max = 0;
        if (start.getFlowRate() > 0 && !opened.contains(start)) {
            opened.add(start);
            // Make sure the opened valves are sorted, to make the cache work
            Collections.sort(opened);
            final int val = (minute - 1) * start.getFlowRate()
                    + calcPressure(start, minute - 1, opened, valves, nrOfOtherPlayers);
            opened.remove(start);
            max = val;
        }

        for (final String n : start.getNeighbourNames()) {
            final Valve neighbour = valves.stream().filter(v -> v.getName().equals(n)).findFirst()
                    .orElseThrow(NoSuchElementException::new);
            max = Math.max(max, calcPressure(neighbour, minute - 1, opened, valves, nrOfOtherPlayers));
        }
        cache.put(state, max);

        return max;

    }

    public String part2(final List<String> input) {
        final List<Valve> valves = parseValves(input);
        final Valve start = valves.stream().filter(f -> f.getName().equals("AA")).findFirst()
                .orElseThrow(NoSuchElementException::new);
        cache = new HashMap<>();
        return String.valueOf(calcPressure(start, 26, new ArrayList<>(), valves, 1));
    }
}