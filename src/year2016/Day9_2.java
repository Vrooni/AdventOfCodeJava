package year2016;

import java.util.List;

public class Day9_2 {
    public String part2(List<String> input)  {
        String file = input.getFirst();
        long decompressedLength = getDecompressedLength(file);
        return String.valueOf(decompressedLength);
    }

    private long getDecompressedLength(String file) {
        long decompressedLength = 0;

        while (file.contains("(")) {
            int mStart = file.indexOf("(");
            int mEnd = file.indexOf(")");

            String marker = file.substring(mStart+1, mEnd);

            int length = Integer.parseInt(marker.split("x")[0]);
            int times = Integer.parseInt(marker.split("x")[1]);
            int sStart = mEnd + 1;
            int sEnd = sStart + length;

            String partFile = file.substring(sStart, sEnd);
            if (partFile.contains("(")) {
                decompressedLength += times * getDecompressedLength(partFile);
            } else {
                decompressedLength += (long) length * times;
            }

            file = file.substring(sEnd);
        }

        return decompressedLength;
    }
}
