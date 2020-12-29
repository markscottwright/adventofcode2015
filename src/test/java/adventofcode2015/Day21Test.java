package adventofcode2015;

import static org.junit.Assert.*;

import org.junit.Test;

public class Day21Test {

    @Test
    public void test() {
        var player = new Day21.Combatant(8, 5, 5);
        var boss = new Day21.Combatant(12, 7, 2);
        
        while (player.isAlive()) {
            boss = boss.takeDamage(player);
            if (!boss.isAlive())
                break;
            player = player.takeDamage(boss);
            System.out.print("Player:" + player.hitPoints);
            System.out.println("  Boss:" + boss.hitPoints);
        }
        
        if (player.isAlive())
            System.out.println("Player wins");
        else
            System.out.println("Boss wins");

    }

}
