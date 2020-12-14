package adventofcode2015;

import static java.lang.Integer.parseInt;

import java.util.Map;
import java.util.TreeMap;

public class Day16 {

    static class AuntSue {
        @Override
        public String toString() {
            return "AuntSue [sueNumber=" + sueNumber + ", qualities="
                    + qualities + "]";
        }

        private int sueNumber;
        private Map<String, Integer> qualities;

        public AuntSue(int sueNumber, Map<String, Integer> qualities) {
            this.sueNumber = sueNumber;
            this.qualities = qualities;
        }

        static AuntSue parse(String l) {
            var sueAndQualities = l.split(":", 2);
            TreeMap<String, Integer> qualities = new TreeMap<>();
            int sueNumber = parseInt(sueAndQualities[0].split(" ")[1]);
            for (String quality : sueAndQualities[1].split(", ")) {
                qualities.put(quality.split(":")[0].strip(),
                        parseInt(quality.split(":")[1].strip()));
            }
            return new AuntSue(sueNumber, qualities);
        }

        boolean matches(Map<String, Integer> reading) {
            for (String qualityName : qualities.keySet()) {
                if (reading.containsKey(qualityName) && reading
                        .get(qualityName) != qualities.get(qualityName))
                    return false;
            }
            return true;
        }

        boolean matchesPart2(Map<String, Integer> reading) {
            for (String qualityName : qualities.keySet()) {
                // "the cats and trees readings indicates that there are greater
                // than that many"
                if (qualityName.equals("cats") || qualityName.equals("trees")) {
                    if (reading.containsKey(qualityName) && reading
                            .get(qualityName) >= qualities.get(qualityName)) {
                        return false;
                    }
                }
                // "while the pomeranians and goldfish readings indicate that
                // there are fewer than that many"
                else if (qualityName.equals("pomeranians")
                        || qualityName.equals("goldfish")) {
                    if (reading.containsKey(qualityName) && reading
                            .get(qualityName) <= qualities.get(qualityName)) {
                        return false;
                    }
                }

                else if (reading.containsKey(qualityName) && reading
                        .get(qualityName) != qualities.get(qualityName)) {
                    return false;
                }

            }
            return true;
        }
    }

    public static void main(String[] strings) {
        var reading = new TreeMap<String, Integer>();
        reading.put("children", 3);
        reading.put("cats", 7);
        reading.put("samoyeds", 2);
        reading.put("pomeranians", 3);
        reading.put("akitas", 0);
        reading.put("vizslas", 0);
        reading.put("goldfish", 5);
        reading.put("trees", 3);
        reading.put("cars", 2);
        reading.put("perfumes", 1);

        var theAuntSue = Util.parseAndCollectForDay(16, AuntSue::parse).stream()
                .filter(a -> a.matches(reading)).findFirst();
        System.out.println("Day 16 part 1: " + theAuntSue.get().sueNumber);

        var theAuntSue2 = Util.parseAndCollectForDay(16, AuntSue::parse)
                .stream().filter(a -> a.matchesPart2(reading)).findFirst();
        System.out.println("Day 16 part 2: " + theAuntSue2.get().sueNumber);
    }

}
