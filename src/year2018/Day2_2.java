package year2018;

import java.util.List;

public class Day2_2 {
    public String part2(List<String> ids) {
        for (int i = 0; i < ids.size() - 1; i++) {
            for (int j = i+1; j < ids.size(); j++) {
                String id1 = ids.get(i);
                String id2 = ids.get(j);

                if (id1.length() != id2.length()) {
                    continue;
                }

                int differs = 0;
                int differsIndex = 0;
                for (int k = 0; k < id1.length(); k++) {
                    if (id1.charAt(k) != id2.charAt(k)) {
                        differs++;
                        differsIndex = k;
                    }
                }

                if (differs == 1) {
                    return id1.substring(0, differsIndex) + id1.substring(differsIndex + 1);
                }
            }
        }

        return "-1";
    }
}
