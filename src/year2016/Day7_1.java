package year2016;

import java.util.List;

public class Day7_1 {
    public String part1(List<String> input)  {
        int validIps = 0;

        for (String ip : input) {
            if (supportsTLS(ip)) {
                validIps++;
            }
        }

        return String.valueOf(validIps);
    }

    private boolean supportsTLS(String ip) {
        boolean hasABBA = false;
        while (!ip.isEmpty()) {

            if (ip.startsWith("[")) {
                String subIp = ip.substring(1, ip.indexOf("]"));
                ip = ip.substring(ip.indexOf("]") + 1);

                if (containsABBA(subIp)) {
                    return false;
                }
            } else if (ip.contains("[")) {
                String subIp = ip.substring(0, ip.indexOf("["));
                ip = ip.substring(ip.indexOf("["));

                if (!hasABBA && containsABBA(subIp)) {
                    hasABBA = true;
                }
            } else {
                if (! hasABBA && containsABBA(ip)) {
                    hasABBA = true;
                }
                ip = "";
            }
        }

        return hasABBA;
    }

    private boolean containsABBA(String subIp) {
        for (int i = 0; i < subIp.length()-3; i++) {
            if (subIp.charAt(i) == subIp.charAt(i+3)
                    && subIp.charAt(i+1) == subIp.charAt(i+2)
                    && subIp.charAt(i) != subIp.charAt(i+1)) {
                return true;
            }
        }

        return false;
    }
}
