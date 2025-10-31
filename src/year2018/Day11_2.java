package year2018;

import java.util.List;

public class Day11_2 {
    private Integer[][] memory = new Integer[300][300];

    private record Result(int x, int y, int totalPower, int size) {}


    public String part2(List<String> input) {
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

        Result totalPower = getMaxTotalPower(grid, 3);

        memory = new Integer[300][300];
        for (int i = 1; i <= 300; i++) {
            Result currentTotalPower = getMaxTotalPower(grid, i);
            totalPower = currentTotalPower.totalPower > totalPower.totalPower ? currentTotalPower : totalPower;
        }

        return totalPower.x + "," + totalPower.y + "," + totalPower.size;
    }

    private Result getMaxTotalPower(int[][] grid, int size) {
        int totalPower = Integer.MIN_VALUE;
        int x = 0;
        int y = 0;

        for (int i = 0; i <= 300 - size; i++) {
            for (int j = 0; j <= 300 - size; j++) {
                int currentTotalPower = getTotalPower(grid, j, i, size);

                if (currentTotalPower > totalPower) {
                    totalPower = currentTotalPower;
                    x = j+1;
                    y = i+1;
                }
            }
        }

        return new Result(x, y, totalPower, size);
    }

    private int getTotalPower(int[][] grid, int x, int y, int size) {
        int totalPower = 0;
        Integer squareTotalPower = memory[y][x];

        if (squareTotalPower == null) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    totalPower += grid[y + i][x + j];
                }
            }
        } else {
            totalPower = squareTotalPower;
            for (int i = 0; i < size; i++) {
                totalPower += grid[y + i][x + size-1];
            }
            for (int i = 0; i < size-1; i++) {
                totalPower += grid[y + size-1][x + i];
            }
        }

        memory[y][x] = totalPower;
        return totalPower;
    }
}
