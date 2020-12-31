package adventofcode2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day5 {
    static boolean stringIsNice(String s) {
        if (List.of("ab", "cd", "pq", "xy").stream()
                .anyMatch(a -> s.contains(a)))
            return false;

        return (containsTwiceInARow(s) && containsThreeVowels(s));
    }

    private static boolean containsThreeVowels(String s) {
        int numVowels = 0;
        for (char c : s.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
                numVowels++;
            if (numVowels > 2)
                return true;
        }

        return false;
    }

    private static boolean containsTwiceInARow(String s) {
        for (int i = 0; i < s.length() - 1; ++i)
            if (s.charAt(i) == s.charAt(i + 1))
                return true;

        return false;
    }

    static boolean stringIsNicePart2(String s) {
        return containsTwoPair(s) && containsPairWithOneBetween(s);
    }

    static boolean containsPairWithOneBetween(String s) {
        for (int i = 0; i < s.length() - 2; ++i) {
            if (s.charAt(i) == s.charAt(i + 2))
                return true;
        }
        return false;
    }

    static boolean containsTwoPair(String s) {
        for (int i = 0; i < s.length() - 2; ++i) {
            String possiblePair = s.substring(i, i + 2);
            if (s.substring(i + 2).contains(possiblePair))
                return true;
        }
        return false;
    }

    private static final String INPUT = "src/main/resources/day5.txt";

    public static void main(String[] args) throws IOException {
        var numNiceStrings = Files.readAllLines(Paths.get(INPUT)).stream()
                .filter(Day5::stringIsNice).count();
        System.out.println("Day 5 part 1: " + numNiceStrings);

        var numNiceStrings2 = Files.readAllLines(Paths.get(INPUT)).stream()
                .filter(Day5::stringIsNicePart2).count();
        System.out.println("Day 5 part 2: " + numNiceStrings2);
    }
}
