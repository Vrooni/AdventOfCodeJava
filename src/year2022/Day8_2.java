package year2022;

import java.util.List;

public class Day8_2 {

    public String part2(List<String> lines) {
        int maxVisibleTrees = 0;
        for (int y = 1; y < lines.size()-1; y++) {
            for (int x = 1; x < lines.getFirst().length()-1; x++) {

                //look left
                int productVisibleTrees;
                int countVisibleTrees = 0;
                for (int k = x-1; k >= 0; k--) {
                    if (Character.getNumericValue(lines.get(y).charAt(k)) >= Character.getNumericValue(lines.get(y).charAt(x))) {
                        countVisibleTrees++;
                        break;
                    }

                    countVisibleTrees++;
                }
                productVisibleTrees = countVisibleTrees;

                //look right
                countVisibleTrees = 0;
                for (int k = x+1; k < lines.getFirst().length(); k++) {
                    if (Character.getNumericValue(lines.get(y).charAt(k)) >= Character.getNumericValue(lines.get(y).charAt(x))) {
                        countVisibleTrees++;
                        break;
                    }

                    countVisibleTrees++;
                }
                productVisibleTrees *= countVisibleTrees;

                //look up
                countVisibleTrees = 0;
                for (int k = y-1; k >= 0 ; k--) {
                    if (Character.getNumericValue(lines.get(k).charAt(x)) >= Character.getNumericValue(lines.get(y).charAt(x))) {
                        countVisibleTrees++;
                        break;
                    }

                    countVisibleTrees++;
                }
                productVisibleTrees *= countVisibleTrees;

                //look down
                countVisibleTrees = 0;
                for (int k = y+1; k < lines.size() ; k++) {
                    if (Character.getNumericValue(lines.get(k).charAt(x)) >= Character.getNumericValue(lines.get(y).charAt(x))) {
                        countVisibleTrees++;
                        break;
                    }

                    countVisibleTrees++;
                }
                productVisibleTrees *= countVisibleTrees;

                maxVisibleTrees = Math.max(maxVisibleTrees, productVisibleTrees);
            }
        }

        return String.valueOf(maxVisibleTrees);
    }
}
