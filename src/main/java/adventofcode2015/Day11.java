package adventofcode2015;

public class Day11 {
    static boolean containsStraight(char[] s) {
        for (int i = 0; i < s.length - 2; i++) {
            if (s[i] == s[i + 1] - 1 && s[i] == s[i + 2] - 2)
                return true;
        }
        return false;
    }

    static boolean containsIOL(char[] s) {
        for (int i = 0; i < s.length; i++) {
            if (s[i] == 'i' || s[i] == 'o' || s[i] == 'l')
                return true;
        }
        return false;
    }

    static boolean containsTwoPairs(char[] s) {
        for (int i = 0; i < s.length - 1; ++i) {
            if (s[i] == s[i + 1]) {
                for (int j = i + 2; j < s.length - 1; ++j) {
                    if (s[j] == s[j + 1] && s[j] != s[i]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static boolean passwordIsValid(char[] s) {
        return !containsIOL(s) && containsStraight(s) && containsTwoPairs(s);
    }

    static char[] increment(char[] s) {
        boolean carry = true;
        for (int i = s.length - 1; carry && i >= 0; --i) {
            if (carry) {
                if (s[i] == 'z') {
                    s[i] = 'a';
                    carry = true;
                } else {
                    s[i] += 1;
                    carry = false;
                }
            }
        }
        return s;
    }

    static char[] nextValidPassword(char[] s) {
        do {
            increment(s);
        } while (!passwordIsValid(s));
        return s;
    }

    static String nextValidPassword(String password) {
        char[] s = password.toCharArray();
        do {
            increment(s);
        } while (!passwordIsValid(s));
        return new String(s);
    }

    public static void main(String[] args) {
        System.out.println("Day 11 part 1: " + nextValidPassword("hxbxwxba"));
        System.out.println("Day 11 part 2: "
                + nextValidPassword(nextValidPassword("hxbxwxba")));
    }

    public static boolean passwordIsValid(String s) {
        return passwordIsValid(s.toCharArray());
    }
}
