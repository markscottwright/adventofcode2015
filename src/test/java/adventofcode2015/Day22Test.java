package adventofcode2015;

import org.junit.Test;

import adventofcode2015.Day22.SpellHistory;

public class Day22Test {

    @Test
    public void testPart1Example() {
        var start = Day22.GameState.start(10, 250, 13, 8);
        SpellHistory bestSolution = new Day22.SpellHistory();
        Day22.canFindBetterSolution(bestSolution, new SpellHistory(), start, false);
        System.out.println(bestSolution);
        
        start = Day22.GameState.start(10, 250, 14, 8);
        bestSolution.clear();
        Day22.canFindBetterSolution(bestSolution, new SpellHistory(), start, false);
        System.out.println(bestSolution);
    }

}
