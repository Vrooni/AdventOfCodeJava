package year2016;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14_2 {
    private final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    private final MessageDigest md = MessageDigest.getInstance("MD5");

    public Day14_2() throws NoSuchAlgorithmException { }

    public String part2(List<String> input) throws UnsupportedEncodingException {
        String salt = input.getFirst();

        Map<String, String> hashes = new HashMap<>();
        int keys = 0;
        int index;

        for (index = 0; keys < 64; index++) {
            String key = salt + index;

            if (!hashes.containsKey(key)) {
                hashes.put(key, getHash(key));
            }

            if (isValid(salt, key, index, hashes)) {
                keys++;
            }
        }

        return String.valueOf(index - 1);
    }

    private boolean isValid(String salt, String key, int index, Map<String, String> hashes) {
        String hash = hashes.get(key);
        char triple = getTriple(hash);

        if (triple == ' ') {
            return false;
        }

        for (int i = index + 1; i <= index + 1000; i++) {
            String otherKey = salt + i;

            if (!hashes.containsKey(otherKey)) {
                hashes.put(otherKey, getHash(otherKey));
            }

            if (hashes.get(otherKey).contains(String.valueOf(triple).repeat(5))) {
                return true;
            }
        }

        return false;
    }

    private char getTriple(String hash) {
        for (int i = 0; i < hash.length() - 2; i++) {
            char c1 = hash.charAt(i);
            char c2 = hash.charAt(i + 1);
            char c3 = hash.charAt(i + 2);

            if (c1 == c2 && c1 == c3) {
                return c1;
            }
        }

        return ' ';
    }

    private String getHash(String key) {
        String hash = bytesToHex(md.digest(key.getBytes(StandardCharsets.UTF_8))).toLowerCase();

        for (int i = 0; i < 2016; i++) {
            hash = bytesToHex(md.digest(hash.getBytes(StandardCharsets.UTF_8))).toLowerCase();
        }

        return hash;
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