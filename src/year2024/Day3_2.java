package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3_2 {
    public String part2(List<String> lines) {
        String input = String.join("", lines);

        List<Boolean> toggles = new ArrayList<>();
        boolean toggle = true;

        for (int i = 0; i < input.length(); i++) {
            if (input.startsWith("do()", i)) {
                toggle = true;
            } else if (input.startsWith("don't()", i)) {
                toggle = false;
            }

            toggles.add(toggle);
        }

        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(input);
        int result = 0;


        while (matcher.find()) {
            int index = matcher.start();

            if (toggles.get(index)) {
                result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }

        return String.valueOf(result);
    }
}
