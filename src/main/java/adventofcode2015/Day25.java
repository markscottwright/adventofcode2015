package adventofcode2015;

public class Day25 {

    public static void main(String[] strings) {
        long codeNum = 20151125;
        int diagonalRow = 2;
        outer: do {
            for (int row = diagonalRow; row >= 1; --row) {
                codeNum = codeNum * 252533L % 33554393L;
                int col = diagonalRow - row + 1;
                if (row == 2947 && col == 3029 ) {
                    System.out.println(
                            String.format("Day 25 part 1: %d", codeNum));
                    break outer;
                }
            }
        } while (diagonalRow++ <= 10000);

    }

}
