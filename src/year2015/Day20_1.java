package year2015;

import java.util.List;

public class Day20_1 {

    public String part1(List<String> lines) {
        int input = Integer.parseInt(lines.getFirst());
        for (int houseNr = 1; ; houseNr++) {
            int presents = 0;

            for (int i = 1; i <= Math.sqrt(houseNr); i++) {
                if (houseNr % i == 0) {
                    presents += i * 10;
                    if (houseNr/i != i) {
                        presents += (houseNr/i) * 10;
                    }
                }
            }

            if (presents >= input) {
                return String.valueOf(houseNr);
            }
        }
    }
}
