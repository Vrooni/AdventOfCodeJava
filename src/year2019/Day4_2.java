package year2019;

import java.util.List;

public class Day4_2 {

    public String part2(List<String> input) {
        String range = input.getFirst();
        int from = Integer.parseInt(range.split("-")[0]);
        int to = Integer.parseInt(range.split("-")[1]);

        int validPasswords = 0;

        for (int i = from; i <= to; i++) {
            if (isValid(i)) {
                validPasswords++;
            }
        }

        return String.valueOf(validPasswords);
    }

    private boolean isValid(int i) {
        boolean adjacent = false;
        char[] password = String.valueOf(i).toCharArray();

        for (int j = 1; j < password.length; j++) {
            if (password[j-1] > password[j]) {
                return false;
            }

            if (password[j - 1] == password[j]
                    && (j == 1 || password[j - 2] != password[j])
                    && (j == password.length - 1 || password[j + 1] != password[j])) {
                adjacent = true;
            }
        }

        return adjacent;
    }
}
