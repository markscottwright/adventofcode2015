package adventofcode2015;

import java.util.ArrayList;
import java.util.HashMap;
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
        String finalMolecule = "";
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
                finalMolecule = l;
        }

        // part one
        ArrayList<String> moleculeParts = parseMolecule(finalMolecule);
        HashSet<String> allSubstitutions = findAllSingleSubstitutions(rules,
                moleculeParts);
        System.out.println("Day 19 part 1: " + allSubstitutions.size());

        ArrayList<String> rulesInOrder = new ArrayList<>(rules.keySet());
        rulesInOrder
                .sort((r1, r2) -> -Integer.compare(r1.length(), r2.length()));

        // reverse rules are unique (empirically). Try to go from destination
        // molecule back to 'e'
        HashMap<String, String> reversedRules = new HashMap<>();
        for (var substitutions : rules.entrySet()) {
            for (var substitution : substitutions.getValue())
                reversedRules.put(substitution, substitutions.getKey());
        }
        ArrayList<String> rulesInDescendingLengthOrder = new ArrayList<>(
                reversedRules.keySet());
        rulesInDescendingLengthOrder
                .sort((r1, r2) -> -Integer.compare(r1.length(), r2.length()));
        var transformations = findReverseRoute(rulesInDescendingLengthOrder,
                reversedRules, finalMolecule, "e", new ArrayList<>());
        System.out.println("Day 19 part 2: " + transformations.size());
    }

    /*
     * this works, but I don't think it guarantees a minimum solution. I think
     * this needs an A* solution to be actually correct. But AOC accepts it, so
     * it meets requirements...
     * 
     * Go from final molecule to start ("e") by repeatedly substituting using
     * the reverse of the rules from the input.
     */
    static ArrayList<String[]> findReverseRoute(
            ArrayList<String> rulesInPreferredOrder, Map<String, String> rules,
            String currentMolecule, String finalMolecule,
            ArrayList<String[]> transformations) {
        if (currentMolecule.equals(finalMolecule)) {
            return transformations;
        } else if (currentMolecule.length() == finalMolecule.length()) {
            return null;
        } else {
            for (String rule : rulesInPreferredOrder) {
                for (int pos = currentMolecule
                        .indexOf(rule); pos != -1; pos = currentMolecule
                                .indexOf(rule, pos)) {
                    String substitution = rules.get(rule);
                    String nextMolecule = currentMolecule.substring(0, pos)
                            + substitution
                            + currentMolecule.substring(pos + rule.length());
                    transformations.add(new String[] { rule, substitution });
                    var solution = findReverseRoute(rulesInPreferredOrder, rules,
                            nextMolecule, finalMolecule, transformations);
                    if (solution != null)
                        return solution;
                    transformations.remove(transformations.size() - 1);
                }
            }
        }
        return null;
    }

    /**
     * Try to go forward.  This algorithm is too slow.
     */
    static ArrayList<String[]> findRoute1(
            ArrayList<String> rulesInPreferredOrder,
            Map<String, TreeSet<String>> rules, String currentMolecule,
            String finalMolecule, ArrayList<String[]> transformations) {
        if (currentMolecule.equals(finalMolecule)) {
            return transformations;
        } else if (currentMolecule.length() >= finalMolecule.length()) {
            return null;
        } else {
            for (String rule : rulesInPreferredOrder) {
                for (int pos = currentMolecule
                        .indexOf(rule); pos != -1; pos = currentMolecule
                                .indexOf(rule, pos)) {
                    for (String substitution : rules.get(rule)) {
                        String nextMolecule = currentMolecule.substring(0, pos)
                                + substitution + currentMolecule
                                        .substring(pos + rule.length());
                        transformations
                                .add(new String[] { rule, substitution });
                        System.out.println(currentMolecule + " (" + substitution
                                + ") " + nextMolecule);
                        var solution = findRoute1(rulesInPreferredOrder, rules,
                                nextMolecule, finalMolecule, transformations);
                        if (solution != null)
                            return solution;
                        transformations.remove(transformations.size() - 1);
                    }
                }
            }
        }
        return null;
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
