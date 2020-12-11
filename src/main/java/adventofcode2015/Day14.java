package adventofcode2015;

import static java.lang.Integer.parseInt;

import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14 {

    static public class ReindeerSpeed {
        public final String name;
        public final int flyingSpeed;
        public final int maxFlyingTime;
        public final int restTime;

        public ReindeerSpeed(String name, int flyingSpeed, int maxFlyingTime,
                int restTime) {
            this.name = name;
            this.flyingSpeed = flyingSpeed;
            this.maxFlyingTime = maxFlyingTime;
            this.restTime = restTime;
        }

        int distanceFlownIn(int seconds) {
            int numFlyAndRestSessions = seconds / (maxFlyingTime + restTime);
            int secondsAfterLastRest = seconds % (maxFlyingTime + restTime);
            return flyingSpeed * maxFlyingTime * numFlyAndRestSessions
                    + flyingSpeed
                            * Integer.min(secondsAfterLastRest, maxFlyingTime);
        }

        static ReindeerSpeed parse(String l) {
            var pattern = Pattern.compile(
                    "(\\S+) can fly ([0-9]+) km/s for ([0-9]+) seconds, but then must rest for ([0-9]+) seconds.");
            var m = pattern.matcher(l);
            m.matches();
            return new ReindeerSpeed(m.group(1), parseInt(m.group(2)),
                    parseInt(m.group(3)), parseInt(m.group(4)));
        }
    }

    public static void main(String[] strings) {
        final int finishTime = 2503;
        var reindeer = Util.inputLinesForDay(14).stream()
                .map(ReindeerSpeed::parse).collect(Collectors.toList());
        var furthestReindeer = reindeer.stream()
                .max((r1, r2) -> Integer.compare(r1.distanceFlownIn(finishTime),
                        r1.distanceFlownIn(finishTime)))
                .get();
        System.out.println("Day 14 part 1: "
                + furthestReindeer.distanceFlownIn(finishTime));

        HashMap<String, Integer> reindeerScores = new HashMap<>();
        for (int second = 1; second <= 2503; ++second) {
            final int finalSecond = second;
            final int furthestDistance = reindeer.stream()
                    .mapToInt(r -> r.distanceFlownIn(finalSecond)).max()
                    .getAsInt();
            for (ReindeerSpeed r : reindeer) {
                if (r.distanceFlownIn(second) == furthestDistance) {
                    reindeerScores.put(r.name,
                            reindeerScores.getOrDefault(r.name, 0) + 1);
                }
            }
        }
        int maxScore = reindeerScores.values().stream().mapToInt(s -> s).max()
                .getAsInt();
        System.out.println("Day 14 part 2: " + maxScore);
    }

}
