package adventofcode2015;

import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day6 {
    static public class Instruction {
        @Override
        public String toString() {
            return "Instruction [instructionType=" + instructionType + ", left="
                    + left + ", top=" + top + ", right=" + right + ", bottom="
                    + bottom + "]";
        }

        static Pattern pattern = Pattern.compile(
                "(toggle|turn on|turn off) ([0-9]+),([0-9]+) through ([0-9]+),([0-9]+)");
        private String instructionType;
        private int left;
        private int top;
        private int right;
        private int bottom;

        public Instruction(String instructionType, int left, int top, int right,
                int bottom) {
            this.instructionType = instructionType;
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public static Instruction parse(String line) {
            var matcher = pattern.matcher(line);
            if (!matcher.matches())
                throw new RuntimeException("Bad input:" + line);
            return new Instruction(matcher.group(1), parseInt(matcher.group(2)),
                    parseInt(matcher.group(3)), parseInt(matcher.group(4)),
                    parseInt(matcher.group(5)));
        }

        public void apply(XmasLightGrid xmasLightGrid) {
            if (instructionType.equals("turn on")) {
                for (int x = left; x <= right; ++x) {
                    for (int y = top; y <= bottom; ++y)
                        xmasLightGrid.grid[y][x] = true;
                }
            } else if (instructionType.equals("turn off")) {
                for (int x = left; x <= right; ++x) {
                    for (int y = top; y <= bottom; ++y)
                        xmasLightGrid.grid[y][x] = false;
                }
            } else if (instructionType.equals("toggle")) {
                for (int x = left; x <= right; ++x) {
                    for (int y = top; y <= bottom; ++y)
                        xmasLightGrid.grid[y][x] = !xmasLightGrid.grid[y][x];
                }
            } else {
                Util.fail("Unknown instructionType");
            }
        }

        public void apply(VariableLightGrid variableLightGrid) {
            if (instructionType.equals("turn on")) {
                for (int x = left; x <= right; ++x) {
                    for (int y = top; y <= bottom; ++y)
                        variableLightGrid.grid[y][x] += 1;
                }
            } else if (instructionType.equals("turn off")) {
                for (int x = left; x <= right; ++x) {
                    for (int y = top; y <= bottom; ++y) {
                        if (variableLightGrid.grid[y][x] > 0)
                            variableLightGrid.grid[y][x] -= 1;
                    }
                }
            } else if (instructionType.equals("toggle")) {
                for (int x = left; x <= right; ++x) {
                    for (int y = top; y <= bottom; ++y)
                        variableLightGrid.grid[y][x] += 2;
                }
            } else {
                Util.fail("Unknown instructionType");
            }

        }
    }

    static public class XmasLightGrid {
        boolean[][] grid = new boolean[1000][];

        public XmasLightGrid() {
            for (int i = 0; i < grid.length; ++i)
                grid[i] = new boolean[1000];
        }

        void apply(Instruction i) {
            i.apply(this);
        }

        int numOn() {
            int count = 0;
            for (boolean[] row : grid) {
                for (boolean b : row) {
                    if (b)
                        count++;
                }
            }
            return count;
        }
    }

    static public class VariableLightGrid {
        int[][] grid = new int[1000][];

        public VariableLightGrid() {
            for (int i = 0; i < grid.length; ++i)
                grid[i] = new int[1000];
        }

        void apply(Instruction i) {
            i.apply(this);
        }

        long totalBrightness() {
            long brightness = 0;
            for (int[] row : grid) {
                for (int b : row) {
                    brightness += b;
                }
            }
            return brightness;
        }
    }

    public static void main(String[] args) throws IOException {
        var instructions = Files
                .readAllLines(Paths.get("src/main/resources/day6.txt")).stream()
                .map(Instruction::parse).collect(Collectors.toList());

        XmasLightGrid part1Grid = new XmasLightGrid();
        instructions.forEach(part1Grid::apply);
        System.out.println("Day 6 part 1: " + part1Grid.numOn());
        
        VariableLightGrid part2Grid = new VariableLightGrid();
        instructions.forEach(part2Grid::apply);
        System.out.println("Day 6 part 2: " + part2Grid.totalBrightness());
    }
}
