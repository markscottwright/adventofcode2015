package adventofcode2015;

import java.util.List;

public class Day18 {

    static class LightGrid {

        private boolean[][] grid;
        private boolean part2;

        public LightGrid(boolean[][] grid, boolean part2) {
            this.grid = grid;
            this.part2 = part2;
        }

        public static LightGrid parse(List<String> inputLinesForDay,
                boolean part2) {
            boolean[][] grid = new boolean[inputLinesForDay.size()][];
            int rowNum = 0;
            for (String line : inputLinesForDay) {
                boolean[] row = new boolean[line.length()];
                for (int i = 0; i < line.length(); i++) {
                    row[i] = line.charAt(i) == '#';
                }
                grid[rowNum] = row;
                rowNum++;
            }
            if (part2) {
                grid[0][0] = true;
                grid[0][grid[0].length - 1] = true;
                grid[grid.length - 1][0] = true;
                grid[grid.length - 1][grid[0].length - 1] = true;
            }
            return new LightGrid(grid, part2);
        }

        boolean isLit(int row, int col) {
            if (row < 0 || row >= grid.length)
                return false;
            else if (col < 0 || col >= grid[row].length)
                return false;
            else
                return grid[row][col];
        }

        int numNeighborsLit(int row, int col) {
            int n = 0;
            if (isLit(row - 1, col - 1))
                n++;
            if (isLit(row, col - 1))
                n++;
            if (isLit(row + 1, col - 1))
                n++;
            if (isLit(row - 1, col))
                n++;
            if (isLit(row + 1, col))
                n++;
            if (isLit(row - 1, col + 1))
                n++;
            if (isLit(row, col + 1))
                n++;
            if (isLit(row + 1, col + 1))
                n++;
            return n;
        }

        LightGrid update() {
            boolean[][] nextGrid = new boolean[grid.length][];
            for (int row = 0; row < grid.length; ++row) {
                nextGrid[row] = new boolean[grid[row].length];
                for (int col = 0; col < grid[row].length; ++col) {
                    int numNeighborsLit = numNeighborsLit(row, col);
                    if (isLit(row, col)
                            && (numNeighborsLit == 2 || numNeighborsLit == 3))
                        nextGrid[row][col] = true;
                    else if (!isLit(row, col) && numNeighborsLit == 3)
                        nextGrid[row][col] = true;
                    else
                        nextGrid[row][col] = false;
                }
            }
            if (part2) {
                nextGrid[0][0] = true;
                nextGrid[0][nextGrid[0].length - 1] = true;
                nextGrid[nextGrid.length - 1][0] = true;
                nextGrid[nextGrid.length - 1][nextGrid[0].length - 1] = true;
            }

            return new LightGrid(nextGrid, part2);
        }

        int numLit() {
            int n = 0;
            for (int row = 0; row < grid.length; ++row) {
                for (int col = 0; col < grid[row].length; ++col) {
                    if (isLit(row, col))
                        n++;
                }
            }
            return n;
        }
    }

    public static void main(String[] strings) {
        var lightGrid = LightGrid.parse(Util.inputLinesForDay(18), false);
        for (int i = 0; i < 100; ++i) {
            lightGrid = lightGrid.update();
        }
        System.out.println("Day 18 part 1: " + lightGrid.numLit());
        
        lightGrid = LightGrid.parse(Util.inputLinesForDay(18), true);
        for (int i = 0; i < 100; ++i) {
            lightGrid = lightGrid.update();
        }
        System.out.println("Day 18 part 2: " + lightGrid.numLit());
    }

}
