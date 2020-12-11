package adventofcode2015;

public class Day8 {
    static String unescape(String raw) {
        StringBuffer out = new StringBuffer();
        int i = 1; // skip opening '"'
        while (i < raw.length() - 1) {
            if (raw.charAt(i) != '\\') {
                out.append(raw.charAt(i++));
            } else {
                i++;
                if (raw.charAt(i) != 'x')
                    out.append(raw.charAt(i++));
                else {
                    out.append((char) ((raw.charAt(i + 1) - '0') * 10
                            + (raw.charAt(i + 2) - '0')));
                    i += 3;
                }
            }
        }
        return out.toString();
    }

    static String escape(String input) {
        StringBuffer out = new StringBuffer();
        out.append('"');
        for (char c : input.toCharArray()) {
            if (c == '"' || c == '\\')
                out.append('\\');
            out.append(c);
        }
        out.append('"');
        return out.toString();
    }

    public static void main(String[] args) {
        var inputLines = Util.inputLinesForDay(8);
        int part1 = inputLines.stream()
                .mapToInt(l -> l.length() - unescape(l).length()).sum();
        System.out.println("Day 8 part 1: " + part1);
        int part2 = inputLines.stream()
                .mapToInt(l -> escape(l).length() - l.length()).sum();
        System.out.println("Day 8 part 2: " + part2);
    }
}
