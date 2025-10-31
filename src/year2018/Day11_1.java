package year2018;

import java.util.List;

public class Day11_1 {
    private final Integer[][] memory = new Integer[300][300];

    private record Result(int x, int y, int totalPower, int size) {}


    public String part1(List<String> input) {
        int serialNumber = Integer.parseInt(input.getFirst());
        int[][] grid = new int[300][300];

        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                int x = j+1;
                int y = i+1;

                int rackId = x + 10;
                int powerLevel = (y * rackId + serialNumber) * rackId;
                powerLevel = powerLevel >= 100 ? (powerLevel / 100) % 10 : 0;
                grid[i][j] = powerLevel - 5;
            }
        }

        Result totalPower = getMaxTotalPower(grid);
        return totalPower.x + "," + totalPower.y;
    }

    private Result getMaxTotalPower(int[][] grid) {
        int totalPower = Integer.MIN_VALUE;
        int x = 0;
        int y = 0;

        for (int i = 0; i <= 300 - 3; i++) {
            for (int j = 0; j <= 300 - 3; j++) {
                int currentTotalPower = getTotalPower(grid, j, i);

                if (currentTotalPower > totalPower) {
                    totalPower = currentTotalPower;
                    x = j+1;
                    y = i+1;
                }
            }
        }

        return new Result(x, y, totalPower, 3);
    }

    private int getTotalPower(int[][] grid, int x, int y) {
        int totalPower = 0;
        Integer squareTotalPower = memory[y][x];

        if (squareTotalPower == null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    totalPower += grid[y + i][x + j];
                }
            }
        } else {
            totalPower = squareTotalPower;
            for (int i = 0; i < 3; i++) {
                totalPower += grid[y + i][x + 3 -1];
            }
            for (int i = 0; i < 3 -1; i++) {
                totalPower += grid[y + 3 -1][x + i];
            }
        }

        memory[y][x] = totalPower;
        return totalPower;
    }
}
