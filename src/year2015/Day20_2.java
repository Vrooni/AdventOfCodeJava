package year2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day20_2 {

    public String part2(List<String> lines) {
        int input = Integer.parseInt(lines.getFirst());
        Map<Integer, Integer> deliverInformation = new HashMap<>(); //Information on how many houses the elf has already supplied.

        for (int houseNr = 1; ; houseNr++) {
            int presents = 0;

            for (int i = 1; i <= Math.sqrt(houseNr); i++) {

                if (houseNr % i == 0) {
                    int delivers = deliverInformation.computeIfAbsent(i, k -> 0)+1;

                    if (delivers <= 50) {
                        presents += i * 11;
                        deliverInformation.put(i, delivers);
                    }

                    delivers = deliverInformation.computeIfAbsent(houseNr/i, k -> 0)+1;

                    if (houseNr/i != i && delivers <= 50) {
                        presents += (houseNr/i) * 11;
                        deliverInformation.put(houseNr/i, delivers);
                    }
                }
            }

            if (presents >= input) {
                return String.valueOf(houseNr);
            }
        }
    }
}
