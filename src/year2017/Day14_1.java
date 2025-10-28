package year2017;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;

public class Day14_1 {

    public String part1(List<String> lines) {
        String key = lines.getFirst();
        int used = 0;

        for (int i = 0; i < 128; i++) {
            String hash = getKnotHash(key + "-" + i);
            String binaryHash = new BigInteger(hash, 16).toString(2);
            used += (int) binaryHash.chars().filter(c -> c == '1').count();
        }

        return String.valueOf(used);
    }

    private String getKnotHash(String key) {
        byte[] bytes = key.getBytes();
        int[] intArray = new int[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            intArray[i] = bytes[i] & 0xFF;
        }

        int[] numbers = IntStream.rangeClosed(0, 255).toArray();
        List<Integer> lengths = new ArrayList<>(Arrays.stream(intArray).boxed().toList());
        lengths.addAll(List.of(17, 31, 73, 47, 23));

        int currentPosition = 0;
        int skipSize = 0;

        for (int j = 0; j < 64; j++) {
            for (Integer length : lengths) {
                for (int i = 0; i < length/2; i++) {
                    int index1 = (currentPosition + i) % numbers.length;
                    int index2 = (currentPosition + length - i - 1) % numbers.length;

                    int temp = numbers[index1];
                    numbers[index1] = numbers[index2];
                    numbers[index2] = temp;
                }

                currentPosition = (currentPosition + length + skipSize++) % numbers.length;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numbers.length; i += 16) {
            int xor = numbers[i];

            for (int j = 1; j < 16; j++) {
                xor = xor ^ numbers[i+j];
            }

            String hexXor = Integer.toHexString(xor);
            hexXor = hexXor.length() == 1 ? "0" + hexXor : hexXor;
            result.append(hexXor);
        }

        return result.toString();
    }
}
