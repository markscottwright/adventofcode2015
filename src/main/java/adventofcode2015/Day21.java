package adventofcode2015;

import java.util.ArrayList;
import java.util.Collections;

public class Day21 {

    static public class Combatant {

        int hitPoints;
        private int damage;
        private int armor;

        public Combatant(int hitPoints, int damage, int armor) {
            this.hitPoints = hitPoints;
            this.damage = damage;
            this.armor = armor;
        }

        Combatant takeDamageFrom(Combatant opponent) {
            return new Combatant(
                    hitPoints - Math.max(1, opponent.damage - armor), damage,
                    armor);
        }

        boolean isAlive() {
            return hitPoints > 0;
        }

        boolean wouldWinFightWith(Combatant opponent) {
            Combatant me = this;
            while (me.isAlive()) {
                opponent = opponent.takeDamageFrom(me);
                if (!opponent.isAlive())
                    return true;
                me = me.takeDamageFrom(opponent);
            }
            return false;
        }
    }

    enum Weapon {
        Dagger(8, 4), Shortsword(10, 5), Warhammer(25, 6), Longsword(40,
                7), Greataxe(74, 8);

        int cost;
        int damage;

        Weapon(int cost, int damage) {
            this.cost = cost;
            this.damage = damage;
        }
    }

    enum Armor {
        None(0, 0), Leather(13, 1), Chainmail(31, 2), Splintmail(53,
                3), Bandedmail(75, 4), Platemail(102, 5);

        int cost;
        int armor;

        Armor(int cost, int armor) {
            this.cost = cost;
            this.armor = armor;
        }
    }

    enum Ring {
        None(0, 0, 0), Damage1(25, 1, 0), Damage2(50, 2, 0), Damage3(100, 3,
                0), Defense1(20, 0, 1), Defense2(40, 0, 2), Defense3(80, 0, 3);

        int cost;
        int damage;
        int armor;

        Ring(int cost, int damage, int armor) {
            this.cost = cost;
            this.damage = damage;
            this.armor = armor;
        }
    }

    static class Outfitted {
        Combatant combatant;
        int cost;

        static ArrayList<Outfitted> enumerate() {
            ArrayList<Outfitted> all = new ArrayList<>();
            for (Weapon weapon : Weapon.values()) {
                for (Armor armor : Armor.values()) {
                    for (Ring ring1 : Ring.values()) {
                        for (Ring ring2 : Ring.values()) {
                            if (!ring1.equals(ring2) || ring1 == Ring.None) {
                                Outfitted outfitted = new Outfitted();
                                outfitted.cost = weapon.cost + armor.cost
                                        + ring1.cost + ring2.cost;
                                outfitted.combatant = new Combatant(100,
                                        weapon.damage + ring1.damage
                                                + ring2.damage,
                                        armor.armor + ring1.armor
                                                + ring2.armor);
                                all.add(outfitted);
                            }
                        }
                    }
                }
            }
            return all;
        }
    }

    public static void main(String[] strings) {
        Combatant boss = new Combatant(103, 9, 2);
        ArrayList<Outfitted> allPlayers = Outfitted.enumerate();
        allPlayers.sort((c1, c2) -> Integer.compare(c1.cost, c2.cost));
        for (Outfitted player : allPlayers) {
            if (player.combatant.wouldWinFightWith(boss)) {
                System.out.print("Day 21 part 1: ");
                System.out.println(player.cost);
                break;
            }
        }
        
        Collections.reverse(allPlayers);
        for (Outfitted player : allPlayers) {
            if (!player.combatant.wouldWinFightWith(boss)) {
                System.out.print("Day 21 part 2: ");
                System.out.println(player.cost);
                break;
            }
        }
    }

}
