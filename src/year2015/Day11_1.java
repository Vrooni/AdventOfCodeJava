package year2015;

import java.util.List;

public class Day11_1 {

    public String part1(List<String> lines) {
        String input = lines.getFirst();
        String password = increasePassword(input, input.length()-1);

        while (!isValid(password)) {
            password = increasePassword(password, password.length()-1);
        }

        return password;
    }

    private String increasePassword(String password, int index) {
        char newLetter = password.charAt(index);
        newLetter++;

        if (newLetter <= 'z') {
            return replaceAt(password, index, newLetter);
        }

        newLetter = 'a';
        password = replaceAt(password, index, newLetter);
        return increasePassword(password, index-1);
    }

    private String replaceAt(String password, int index, char letter) {
        StringBuilder result = new StringBuilder(password);
        result.setCharAt(index, letter);

        return String.valueOf(result);
    }

    private boolean isValid(String password) {
        return hasIncreasingSequence(password) && hasNoMistakenLetters(password) && hasTwoPairs(password);
    }

    private boolean hasIncreasingSequence(String password) {
        boolean increasingSequence = false;

        for (int i = 0; i < password.length() - 2; i++) {
            if (password.charAt(i+2) - password.charAt(i+1) == 1 && password.charAt(i+1) - password.charAt(i) == 1) {
                increasingSequence = true;
                break;
            }
        }

        return increasingSequence;
    }

    private boolean hasNoMistakenLetters(String password) {
        return !password.contains("i") && !password.contains("o") && !password.contains("l");
    }

    private boolean hasTwoPairs(String password) {
        int pairs = 0;

        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i+1) == password.charAt(i)) {
                pairs++;
                i++;
            }
        }

        return pairs >= 2;
    }
}
