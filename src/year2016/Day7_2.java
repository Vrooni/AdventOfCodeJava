package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day7_2 {
    public String part2(List<String> input)  {
        int validIps = 0;

        for (String ip : input) {
            if (supportsSSL(ip)) {
                validIps++;
            }
        }

        return String.valueOf(validIps);
    }

    private boolean supportsSSL(String ip) {
        List<String> ABAs = new ArrayList<>();
        List<String> BABs = new ArrayList<>();

        while (!ip.isEmpty()) {

            if (ip.startsWith("[")) {
                String subIp = ip.substring(1, ip.indexOf("]"));
                ip = ip.substring(ip.indexOf("]") + 1);

                addABAS(BABs, subIp);
            } else if (ip.contains("[")) {
                String subIp = ip.substring(0, ip.indexOf("["));
                ip = ip.substring(ip.indexOf("["));

                addABAS(ABAs, subIp);
            } else {
                addABAS(ABAs, ip);
                ip = "";
            }
        }

        for (String aba : ABAs) {
            String bab = String.valueOf(aba.charAt(1)) + aba.charAt(0) + aba.charAt(1);
            if (BABs.contains(bab)) {
                return true;
            }
        }

        return false;
    }

    private void addABAS(List<String> ABAs, String subIp) {
        for (int i = 0; i < subIp.length()-2; i++) {
            if (subIp.charAt(i) == subIp.charAt(i+2) && subIp.charAt(i) != subIp.charAt(i+1)) {
                ABAs.add(subIp.substring(i, i+3));
            }
        }
    }
}
