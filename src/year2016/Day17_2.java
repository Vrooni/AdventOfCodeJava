package year2016;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Day17_2 {
    private final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    private record Position(int x, int y, String path) implements Comparable<Position> {

        @Override
        public int compareTo(Position o) {
            return Integer.compare(this.path.length(), o.path.length());
        }
    }

    public String part2(List<String> input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int maxPath = Integer.MIN_VALUE;
        Queue<Position> queue = new PriorityQueue<>();
        queue.offer(new Position(0, 0, ""));

        while (!queue.isEmpty()) {
            Position position = queue.poll();

            if (position.x == 3 && position.y == 3) {
                maxPath = Math.max(maxPath, position.path.length());
                continue;
            }

            MessageDigest md = MessageDigest.getInstance("MD5");
            String hash = bytesToHex(md.digest((input.getFirst() + position.path).getBytes(StandardCharsets.UTF_8))).toLowerCase();

            if (hash.charAt(0) > 'a' && position.y > 0) {
                queue.offer(new Position(position.x, position.y-1, position.path + "U"));
            }

            if (hash.charAt(1) > 'a' && position.y < 3) {
                queue.offer(new Position(position.x, position.y+1, position.path + "D"));
            }

            if (hash.charAt(2) > 'a' && position.x > 0) {
                queue.offer(new Position(position.x-1, position.y, position.path + "L"));
            }

            if (hash.charAt(3) > 'a' && position.x < 3) {
                queue.offer(new Position(position.x+1, position.y, position.path + "R"));
            }
        }

        return String.valueOf(maxPath);
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
