package year2023;

import java.util.*;

public class Day20_2 {
    private enum Type {
        FLIP_FLOP, CONJUNCTION, BROADCAST
    }

    private record Module(Type type, List<String> outputs) {}

    private record BinaryCounter(String conjunction, List<Binary> chain) {}
    private record Binary(String flipFlop, boolean sendToConjunction) {}

    /**
     * Let's make some analysis!
     * Structure of the modules:
     * <p>
     * broadcaster sends to 4 flip-flops
     * broadcaster -> cn, kh, pf, sj
     * <p>
     * this 4 flip-flops each are the beginning of a big chains of flip-flops
     * cn -> vm -> zl -> cm -> ls -> fj -> zt -> fq -> bn -> hx -> jx -> np
     * kh -> dk -> cv -> sq -> gb -> kr -> ln -> gj -> fh -> sx -> cq -> fb
     * pf -> nv -> ht -> rr -> rp -> bc -> cf -> zc -> rq -> dx -> qn -> hn
     * sj -> fv -> km -> gq -> pp -> jr -> xn -> zp -> xv -> xz -> gz -> lq
     * <p>
     * mostly each flip-flop sends to a conjunction module (those with ! does not)
     * tz <- cn, vm, zl, cm, !ls, fj, zt, fq, bn, hx, jx, np
     * xf <- kh, !dk, dv, !sq, gb, !kr, ln, qj, !fh, sx, cq, fb
     * tg <- pf, nv, ht, rr, !rp, !bc, cf, zc, rq, dx, qn, hn
     * mq <- sj, !fv, !km, !gq, pp, jr, !xn, zp, !xv, xz, gz, lq
     * <p>
     * the result of the conjunction which connects to the chain goes into a single conjunction, which acts like a inverter
     * tz -> fk
     * xf -> ff
     * tg -> mm
     * mq -> lh
     * <p>
     * these four conjunctions goes into another conjunction
     * nr <- mm, ff, fk, lh
     * <p>
     * and these conjunction send the result to rx
     * nr -> rx
     * <p>
     * This leads us to the conclusion:
     * rx receive a low pulse if the 4 result conjunctions receives high pules
     * the result conjunction receives high pules, if the result of the chain sends a low pulse
     * this means, the conjunction which connects to the chain has to receive a high pulse from all the connected flip-flops
     * for each chain, there is a binary presentation of the times, the button has been pressed
     * e.g. first chain: 111101111111
     * for each chain the number of needed button presses is the decimal number
     * so the overall button presses leads to the lcm of the chain button presses
     * Ta-da!
     */
    public String part2(List<String> input) {
        Map<String, Module> modules = getModules(input);
        List<BinaryCounter> binaryCounters = new ArrayList<>();

        Module broadcaster = modules.get("broadcaster");
        for (String flipFlop : broadcaster.outputs) {
            binaryCounters.add(getBinaryChain(flipFlop, modules));
        }

        List<Integer> buttonClicks = new ArrayList<>();
        for (BinaryCounter binaryCounter : binaryCounters) {
            buttonClicks.add(getButtonClicks(binaryCounter));
        }

        long lcm = buttonClicks.getFirst();
        for (int i = 1; i < buttonClicks.size(); i++) {
            int b = buttonClicks.get(i);

            lcm = lcm(lcm, b);
        }

        return String.valueOf(lcm);
    }

    public long lcm(long a, long b) {
        long max = Math.max(a, b);
        long min = Math.min(a, b);

        long lcm = max;
        while (lcm % min != 0) {
            lcm += max;
        }

        return lcm;
    }

    private int getButtonClicks(BinaryCounter binaryCounter) {
        StringBuilder binaryNumber = new StringBuilder();

        for (Binary binary : binaryCounter.chain) {
            binaryNumber.append(binary.sendToConjunction ? "1" : "0");
        }

        return Integer.parseInt(binaryNumber.reverse().toString(), 2);
    }

    private BinaryCounter getBinaryChain(String flipFlopName, Map<String, Module> modules) {
        List<Binary> chain = new ArrayList<>();
        String conjunction = modules.get(flipFlopName).outputs.stream()
                .filter(module -> modules.get(module).type == Type.CONJUNCTION)
                .findFirst().get();

        while (true) {
            Module flipFlop = modules.get(flipFlopName);
            Optional<String> nextFlipFlop = flipFlop.outputs.stream()
                    .filter(module -> modules.get(module).type == Type.FLIP_FLOP)
                    .findFirst();

            boolean sendToConjunction = modules.get(flipFlopName).outputs.stream()
                    .anyMatch(module -> module.equals(conjunction));
            chain.add(new Binary(flipFlopName, sendToConjunction));

            if (nextFlipFlop.isEmpty()) {
                break;
            }

            flipFlopName = nextFlipFlop.get();
        }

        return new BinaryCounter(conjunction, chain);
    }

    private Map<String, Module> getModules(List<String> input) {
        Map<String, Module> modules = new HashMap<>();

        for (String line : input) {
            Type type;
            String replaceTarget;

            if (line.startsWith("%")) {
                type = Type.FLIP_FLOP;
                replaceTarget = "%";
            } else if (line.startsWith("&")) {
                type = Type.CONJUNCTION;
                replaceTarget = "&";
            } else if (line.startsWith("broadcaster")) {
                type = Type.BROADCAST;
                replaceTarget = "";
            } else {
                throw new RuntimeException("Unknown Module: " + line);
            }

            String[] splitLine = line.split(" -> ");

            String name = splitLine[0].replace(replaceTarget, "");
            List<String> outputs = new ArrayList<>(Arrays.asList(splitLine[1].split(", ")));

            modules.put(name, new Module(type, outputs));
        }

        return modules;
    }
}

