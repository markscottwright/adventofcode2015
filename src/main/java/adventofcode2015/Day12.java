package adventofcode2015;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Day12 {

    public static class PeekableInputStream {
        private InputStream is;
        int buffer = 0;
        boolean useBuffer = false;

        public PeekableInputStream(InputStream is) {
            this.is = is;
        }

        int read() throws IOException {
            if (useBuffer) {
                useBuffer = false;
                return buffer;
            } else {
                buffer = is.read();
                return buffer;
            }
        }

        void push() {
            useBuffer = true;
        }

        void skipWhitespace() throws IOException {
            int c;
            while ((c = read()) >= 0) {
                if (!Character.isWhitespace(c)) {
                    push();
                    break;
                }
            }
        }
    }

    static public class SimpleJsonParser {

        public ArrayList<Integer> parse(File source)
                throws FileNotFoundException,
                IOException {
            ArrayList<Integer> numbers = new ArrayList<>();
            try (InputStream is = new FileInputStream(source)) {
                PeekableInputStream s = new PeekableInputStream(is);
                int c;
                while ((c = s.read()) >= 0) {
                    if (Character.isWhitespace(c))
                        continue;
                    if (c == '{')
                        numbers.addAll(readMap(s));
                    else if (c == '[')
                        numbers.addAll(readArray(s));
                }
            }
            return numbers;
        }

        private ArrayList<Integer> readArray(PeekableInputStream s)
                throws IOException {
            ArrayList<Integer> numbers = new ArrayList<>();
            int c;
            while ((c = s.read()) >= 0) {
                if (c == ']')
                    return numbers;
                else if (c == '{')
                    numbers.addAll(readMap(s));
                else if (c == '[')
                    numbers.addAll(readArray(s));
                else if (c == '"')
                    readString(s);
                else if (c == '-')
                    numbers.add(-readPositiveNumber(s));
                else if (Character.isDigit(c)) {
                    s.push();
                    numbers.add(readPositiveNumber(s));
                }
            }
            return numbers;
        }

        private int readPositiveNumber(PeekableInputStream s)
                throws IOException {
            int c;
            int value = 0;
            while ((c = s.read()) >= 0) {
                if (Character.isDigit(c))
                    value = value * 10 + (c - '0');
                else {
                    s.push();
                    break;
                }
            }
            return value;
        }

        private String readString(PeekableInputStream s) throws IOException {
            int c;
            StringBuffer out = new StringBuffer();
            while ((c = s.read()) >= 0 && c != '"')
                out.append((char) c);
            return out.toString();
        }

        private ArrayList<Integer> readMap(PeekableInputStream s)
                throws IOException {
            boolean mapContainsRed = false;
            ArrayList<Integer> numbers = new ArrayList<>();
            int c;
            while ((c = s.read()) >= 0) {
                if (c == '}')
                    break;
                if (c == ',')
                    c = s.read();
                assert (c == '"');
                readString(s);

                c = s.read();
                assert c == ':';

                c = s.read();
                if (c == '{')
                    numbers.addAll(readMap(s));
                else if (c == '[')
                    numbers.addAll(readArray(s));
                else if (c == '"') {
                    String keyValue = readString(s);
                    if (keyValue.equals("red")) {
                        mapContainsRed = true;
                    }
                } else if (c == '-')
                    numbers.add(-readPositiveNumber(s));
                else if (Character.isDigit(c)) {
                    s.push();
                    numbers.add(readPositiveNumber(s));
                } else
                    Util.fail(String.format("Unexpected %c", c));
            }

            if (mapContainsRed)
                numbers.clear();
            return numbers;
        }
    }

    public static void main(String[] args) throws IOException {
        // this very naive solution works because accordiing to the puzzle
        // description "You will not encounter any strings containing numbers."
        File source = new File("src/main/resources/day12.txt");
        long sumOfAllNumbers = sumOfAllNumbersInFile(source);
        System.out.println("Day 12 part 1: " + sumOfAllNumbers);

        System.out.println("Day 12 part 2: " + new SimpleJsonParser()
                .parse(source).stream().mapToLong(lo -> (long) lo).sum());
    }

    private static long sumOfAllNumbersInFile(File source)
            throws FileNotFoundException {
        long sumOfAllNumbers = 0;
        try (Scanner scanner = new Scanner(source)) {
            scanner.useDelimiter("[^-0-9]+");
            while (scanner.hasNextInt()) {
                sumOfAllNumbers += scanner.nextInt();
            }
        }
        return sumOfAllNumbers;
    }
}
