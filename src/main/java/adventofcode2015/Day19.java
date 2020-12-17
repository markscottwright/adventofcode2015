package adventofcode2015;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class Day19 {

    public static void main(String[] args) {

        // parse input file
        boolean inRules = true;
        Map<String, TreeSet<String>> rules = new TreeMap<>();
        String inputMolecule = "";
        for (String l : Util.inputLinesForDay(19)) {
            if (l.isEmpty())
                inRules = false;
            if (inRules) {
                String from = l.split(" => ")[0].strip();
                String to = l.split(" => ")[1].strip();
                if (!rules.containsKey(from))
                    rules.put(from, new TreeSet<>());
                rules.get(from).add(to);
            } else
                inputMolecule = l;
        }

        ArrayList<String> moleculeParts = parseMolecule(inputMolecule);
        HashSet<String> allSubstitutions = findAllSingleSubstitutions(rules,
                moleculeParts);
        System.out.println("Day 19 part 1: " + allSubstitutions.size());
        
        // BFS or A* for part 2?
    }

    private static HashSet<String> findAllSingleSubstitutions(
            Map<String, TreeSet<String>> rules,
            ArrayList<String> moleculeParts) {
        HashSet<String> out = new HashSet<>();
        for (int i = 0; i < moleculeParts.size(); ++i) {
            if (rules.containsKey(moleculeParts.get(i))) {
                for (String substitution : rules.get(moleculeParts.get(i))) {
                    String m = String.join("", moleculeParts.subList(0, i))
                            + substitution + String.join("", moleculeParts
                                    .subList(i + 1, moleculeParts.size()));
                    out.add(m);
                }
            }
        }
        return out;
    }

    private static ArrayList<String> parseMolecule(String inputMolecule) {
        ArrayList<String> moleculeParts = new ArrayList<>();
        Pattern moleculeParser = Pattern.compile("[A-Z][a-z]*");
        var m = moleculeParser.matcher(inputMolecule);
        while (m.find())
            moleculeParts.add(m.group());
        return moleculeParts;
    }

}
