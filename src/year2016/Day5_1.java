package year2016;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Day5_1 {
    private final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public String part1(List<String> input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String roomId = input.getFirst();
        StringBuilder password = new StringBuilder();

        MessageDigest md = MessageDigest.getInstance("MD5");

        for (int i = 0; password.length() != 8; i++) {
            byte[] roomIdIndex = (roomId + i).getBytes(StandardCharsets.UTF_8);
            String hash = bytesToHex(md.digest(roomIdIndex));

            if (hash.startsWith("00000")) {
                password.append(hash.charAt(5));
            }
        }

        return password.toString();
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
