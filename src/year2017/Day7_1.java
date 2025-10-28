package year2017;

import java.util.*;

public class Day7_1 {

    public String part1(List<String> input) {
        List<String> programNames = new ArrayList<>(input.stream().map(line -> line.split(" ")[0]).toList());

        for (String line : input) {
            String[] splitLine = line.split("->");

            if (splitLine.length > 1) {
                String[] balances = splitLine[1].replaceAll(" ", "").split(",");

                for (String balancedProgram : balances) {
                    programNames.remove(balancedProgram);
                }
            }
        }

        return programNames.getFirst();
    }
}
