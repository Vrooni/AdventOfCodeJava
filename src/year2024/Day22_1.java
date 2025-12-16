package year2024;

import java.util.*;

public class Day22_1 {
    public String part1(List<String> input) {
        List<Integer> secretNumbers = input.stream().map(Integer::parseInt).toList();
        long result = 0;

        for (long secretNumber : secretNumbers) {
            for (int i = 0; i < 2000; i++) {
                secretNumber = getNextSecretNumber(secretNumber);
            }

            result += secretNumber;
        }

        return String.valueOf(result);
    }

    private long getNextSecretNumber(long secretNumber) {
        long value = secretNumber * 64;
        secretNumber = mix(secretNumber, value);
        secretNumber = prune(secretNumber);

        value = secretNumber / 32;
        secretNumber = mix(secretNumber, value);
        secretNumber = prune(secretNumber);

        value = secretNumber * 2048;
        secretNumber = mix(secretNumber, value);
        secretNumber = prune(secretNumber);

        return secretNumber;
    }

    private long mix(long secretNumber, long number) {
        return number ^ secretNumber;
    }

    private long prune(long secretNumber) {
        return secretNumber % 16777216;
    }
}
