package adventofcode2015;

import java.util.List;

public class Day17 {

    public static void main(String[] strings) {
        List<Integer> containers = Util.parseAndCollectForDay(17,
                Integer::parseInt);
        ContainerFiller filler = new ContainerFiller(containers, 150);
        System.out.println("Day 17 part 1: " + filler.numValidCombinations());
        System.out.println("Day 17 part 2: " + filler.getNumMinimumSolutions());
    }

}
