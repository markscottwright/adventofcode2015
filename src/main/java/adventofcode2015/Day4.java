package adventofcode2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day4 {
    private static final String INPUT = "yzbqklnj";
    private static MessageDigest digester;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        digester = MessageDigest.getInstance("MD5");
        System.out.println("Day 4 part 1: " + part1());
        System.out.println("Day 4 part 2: " + part2());
    }

    private static long part1() throws NoSuchAlgorithmException {
        long i = 0;
        while (true) {
            byte[] digest = digester.digest((INPUT + i).getBytes());
            if (digest[0] == 0 && digest[1] == 0 && (digest[2] & 0xf0) == 0)
                break;
            i++;
        }
        return i;
    }

    private static long part2() throws NoSuchAlgorithmException {
        long i = 0;
        while (true) {
            byte[] digest = digester.digest((INPUT + i).getBytes());
            if (digest[0] == 0 && digest[1] == 0 && digest[2] == 0)
                break;
            i++;
        }
        return i;
    }
}
