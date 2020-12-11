package adventofcode2015;

public class Day11 {
    static boolean containsStraight(String s) {
        for (int i = 0; i < s.length() - 2; i++) {
            if (s.charAt(i) == s.charAt(i + 1)
                    && s.charAt(i) == s.charAt(i + 2))
                return true;
        }
        return false;
    }

    static boolean containsIOL(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'l')
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        double part1 = Math.pow(26, 8);
        System.out.println("Day 11 part 1: " + part1);
    }
}
