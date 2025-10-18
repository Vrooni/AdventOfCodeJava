package year2016;

import java.util.List;

public class Day16_2 {

    public String part2(List<String> input)  {
        StringBuilder a = new StringBuilder(input.getFirst());
        int length = 35651584;

        while (a.length() < length) {
            String b = new StringBuilder(a).reverse().toString();
            b = flipBits(b);
            a.append("0").append(b);
        }

        String data = a.substring(0, length);
        String checksum = getChecksum(data);

        while (checksum.length() % 2 == 0) {
            checksum = getChecksum(checksum);
        }

        return checksum;
    }

    private String flipBits(String b) {
        StringBuilder flipped = new StringBuilder();

        for (char bit : b.toCharArray()) {
            flipped.append(bit == '0' ? "1" : "0");
        }

        return flipped.toString();
    }

    private String getChecksum(String data) {
        StringBuilder checksum = new StringBuilder();

        for (int i = 0; i < data.length() - 1; i += 2) {
            checksum.append(data.charAt(i) == data.charAt(i+1) ? 1 : 0);
        }

        return checksum.toString();
    }
}
