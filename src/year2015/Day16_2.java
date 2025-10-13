package year2015;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Day16_2 {
    private static class Aunt {
        int children = -1;
        int cats = -1;
        int samoyeds = -1;
        int pomeranians = -1;
        int akitas = -1;
        int vizslas = -1;
        int goldfish = -1;
        int trees = -1;
        int cars = -1;
        int perfumes = -1;
    }

    public String part2(List<String> lines) {
        List<Aunt> aunts = readInput(lines);

        for (int i = 0; i < aunts.size(); i++) {
            Aunt aunt = aunts.get(i);

            if ((aunt.children == 3 || aunt.children == -1) &&
                    (aunt.cats > 7 || aunt.cats == -1) &&
                    (aunt.samoyeds == 2 || aunt.samoyeds == -1) &&
                    (aunt.pomeranians < 3 || aunt.pomeranians == -1) &&
                    (aunt.akitas == 0 || aunt.akitas == -1) &&
                    (aunt.vizslas == 0 || aunt.vizslas == -1) &&
                    (aunt.goldfish < 5 || aunt.goldfish == -1) &&
                    (aunt.trees > 3 || aunt.trees == -1) &&
                    (aunt.cars == 2 || aunt.cars == -1) &&
                    (aunt.perfumes == 1 || aunt.perfumes == -1)
            ) {
                return String.valueOf(i + 1);
            }
        }

        return "-1";
    }

    private List<Aunt> readInput(List<String> input) {
        List<Aunt> aunts = new ArrayList<>();

        for (String line : input) {
            Aunt aunt = new Aunt();

            String shortenLine = line.split(": ", 2)[1];
            String[] information = shortenLine.split(", ");

            for (String info : information) {
                String[] fields = info.split(": ");

                for (int i = 0; i < fields.length; i += 2) {
                    try {
                        Field field = aunt.getClass().getDeclaredField(fields[i]);
                        field.setAccessible(true);
                        field.setInt(aunt, Integer.parseInt(fields[i+1]));
                    } catch (NoSuchFieldException | IllegalAccessException ex) {
                        System.out.println(ex);
                    }
                }
            }

            aunts.add(aunt);
        }

        return aunts;
    }
}
