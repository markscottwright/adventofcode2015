package adventofcode2015;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.Collections2;

public class Day13 {

    static public class AttendeeHappinessEffect {
        static AttendeeHappinessEffect parse(String l) {
            Pattern pattern = Pattern.compile(
                    "(\\S+) would (gain|lose) ([0-9]+) happiness units by sitting next to (\\S+).");
            Matcher m = pattern.matcher(l);
            m.matches();

            if (m.group(2).equals("gain"))
                return new AttendeeHappinessEffect(m.group(1), m.group(4),
                        Integer.parseInt(m.group(3)));
            else
                return new AttendeeHappinessEffect(m.group(1), m.group(4),
                        -Integer.parseInt(m.group(3)));
        }
        
        private String attendee;
        private String neighbor;
        private int happinessDifference;

        public AttendeeHappinessEffect(String attendee, String neighbor,
                int happinessDifference) {
            this.attendee = attendee;
            this.neighbor = neighbor;
            this.happinessDifference = happinessDifference;
        }
    }

    public static void main(String[] strings) {
        var rules = Util.inputLinesForDay(13).stream()
                .map(AttendeeHappinessEffect::parse)
                .collect(Collectors.toList());
        var fasterRules = toFastRules(rules);
        
        var attendees = rules.stream().map(r -> r.attendee)
                .collect(Collectors.toSet());
        
        var tableLayouts = Collections2.permutations(attendees);
        var maxHappiness = tableLayouts.stream()
                .mapToInt(table -> runRules(table, fasterRules)).max();
        System.out.println("Day 13 part 1: " + maxHappiness.getAsInt());

        // add me as a neutral party
        fasterRules.put("me", new HashMap<>());
        for (var attendee : attendees) {
            fasterRules.get("me").put(attendee, 0);
            fasterRules.get(attendee).put("me", 0);
        }
        attendees.add("me");
        
        tableLayouts = Collections2.permutations(attendees);
        maxHappiness = tableLayouts.stream()
                .mapToInt(table -> runRules(table, fasterRules)).max();
        System.out.println("Day 13 part 2: " + maxHappiness.getAsInt());
    }

    private static int runRules(List<String> table,
            HashMap<String, HashMap<String, Integer>> fasterRules) {

        int n = table.size();
        int happiness = 0;
        happiness += fasterRules.get(table.get(0)).get(table.get(n - 1));
        happiness += fasterRules.get(table.get(0)).get(table.get(1));
        for (int i = 1; i < table.size() - 1; ++i) {
            happiness += fasterRules.get(table.get(i)).get(table.get(i - 1));
            happiness += fasterRules.get(table.get(i)).get(table.get(i + 1));
        }
        happiness += fasterRules.get(table.get(n - 1)).get(table.get(n - 2));
        happiness += fasterRules.get(table.get(n - 1)).get(table.get(0));
        return happiness;
    }

    private static HashMap<String, HashMap<String, Integer>> toFastRules(
            List<AttendeeHappinessEffect> rules) {
        HashMap<String, HashMap<String, Integer>> fasterRules = new HashMap<>();
        for (var rule : rules) {
            if (!fasterRules.containsKey(rule.attendee))
                fasterRules.put(rule.attendee, new HashMap<>());
            fasterRules.get(rule.attendee).put(rule.neighbor,
                    rule.happinessDifference);
        }
        return fasterRules;
    }

}
