package adventofcode2015;

public class Day10 {
    public static String lookAndSay(String s) {
        StringBuffer out = new StringBuffer();
        int start = 0;
        int end;
        while (start < s.length()) {
            end = start + 1;
            while (end < s.length() && s.charAt(end) == s.charAt(start))
                end++;
            out.append("" + (end - start));
            out.append(s.charAt(start));
            start = end;
        }

        return out.toString();
    }

    public static void main(String[] args) {
        String s = "1321131112";
        for (int i = 0; i < 40; ++i) {
            s = lookAndSay(s);
        }
        System.out.println("Day 10 part 1: " + s.length());
        
        s = "1321131112";
        for (int i = 0; i < 50; ++i) {
            s = lookAndSay(s);
        }
        System.out.println("Day 10 part 2: " + s.length());
    }
}
