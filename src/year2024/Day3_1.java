package year2024;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3_1 {
    public String part1(List<String> lines) {
        String input = String.join("", lines);
        int result = 0;

        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }

        return String.valueOf(result);
    }
}
