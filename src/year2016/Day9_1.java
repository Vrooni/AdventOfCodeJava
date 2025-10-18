package year2016;

import java.util.List;

public class Day9_1 {
    public String part1(List<String> input)  {
        String file = input.getFirst();
        StringBuilder decompressed = new StringBuilder();

        while (!file.isEmpty()) {
            int mStart = file.indexOf("(");
            int mEnd = file.indexOf(")");

            String marker = file.substring(mStart+1, mEnd);

            int length = Integer.parseInt(marker.split("x")[0]);
            int times = Integer.parseInt(marker.split("x")[1]);
            int sStart = mEnd + 1;
            int sEnd = sStart + length;

            decompressed.append(file, 0, mStart);
            for (int i = 0; i < times; i++) {
                decompressed.append(file, sStart, sEnd);
            }

            file = file.substring(sEnd);
        }

        return String.valueOf(decompressed.length());
    }
}
