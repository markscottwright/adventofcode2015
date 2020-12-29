package adventofcode2015;

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

        Combatant takeDamage(Combatant opponent) {
            return new Combatant(
                    hitPoints - Math.max(1, opponent.damage - armor), damage,
                    armor);
        }

        boolean isAlive() {
            return hitPoints > 0;
        }
    }
    
    

    public static void main(String[] strings) {
        System.out.println("Day 21 part 1: ");
        Combatant player = new Combatant(103, 9, 2);
        Combatant boss = new Combatant(100, 8, 1);
        
        while (player.isAlive()) {
            boss = boss.takeDamage(player);
            if (!boss.isAlive())
                break;
            player = player.takeDamage(boss);
        }
        
        System.out.println(player.isAlive());
        System.out.println(boss.isAlive());
    }

}
