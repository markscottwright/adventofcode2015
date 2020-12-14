package adventofcode2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Util {

    public static void fail(String message) {
        throw new RuntimeException(message);
    }

    public static List<String> inputLinesForDay(int dayNum) {
        String filePath = "src/main/resources/day" + dayNum + ".txt";
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read " + filePath);
        }
    }

    public static List<String> parseToGroups(Pattern p, String s) {
        Matcher matcher = p.matcher(s);
        if (!matcher.matches())
            throw new RuntimeException("Can't parse " + s + " with " + p);
        ArrayList<String> groups = new ArrayList<>();
        for (int i = 1; i < matcher.groupCount() + 1; ++i)
            groups.add(matcher.group(i));
        return groups;
    }

    public static <T> List<T> parseAndCollectForDay(
            int day,
            Function<String, T> parser) {
        return inputLinesForDay(day).stream().map(parser)
                .collect(Collectors.toList());
    }

}
