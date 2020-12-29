package adventofcode2015;

import java.util.ArrayList;

public class Day22 {

    static public class SpellHistory {

        ArrayList<Spell> spells = new ArrayList<Day22.Spell>();
        int mana = 0;

        public boolean isBetterThan(SpellHistory bestSolution) {
            return bestSolution.spells.isEmpty()
                    || totalMana() < bestSolution.totalMana();
        }

        private int totalMana() {
            return mana;
        }

        public void replace(SpellHistory newSpells) {
            spells.clear();
            spells.addAll(newSpells.spells);
            mana = spells.stream().mapToInt(s -> s.mana).sum();
        }

        public void add(Spell spell) {
            spells.add(spell);
            mana += spell.mana;
        }

        public void removeLast() {
            Spell removed = spells.remove(spells.size() - 1);
            mana -= removed.mana;
        }

        public void clear() {
            spells.clear();
            mana = 0;
        }

    }

    static class GameState {
        @Override
        public String toString() {
            return "GameState [wizardHitPoints=" + wizardHitPoints
                    + ", wizardMana=" + wizardMana + ", opponentHitPoints="
                    + opponentHitPoints + ", opponentDamage=" + opponentDamage
                    + ", shieldTurnsRemaining=" + shieldTurnsRemaining
                    + ", poisonTurnsRemaining=" + poisonTurnsRemaining
                    + ", rechargeTurnsRemaining=" + rechargeTurnsRemaining
                    + "]";
        }

        int wizardHitPoints;
        int wizardMana;
        int opponentHitPoints;
        int opponentDamage;
        int shieldTurnsRemaining = 0;
        int poisonTurnsRemaining = 0;
        int rechargeTurnsRemaining = 0;

        static GameState start(int wizardHitPoints, int wizardMana,
                int opponentHitPoints, int opponentDamage) {
            var out = new GameState();
            out.wizardHitPoints = wizardHitPoints;
            out.wizardMana = wizardMana;
            out.opponentDamage = opponentDamage;
            out.opponentHitPoints = opponentHitPoints;
            return out;
        }

        public boolean opponentWon() {
            return wizardHitPoints <= 0 && opponentHitPoints > 0;
        }

        public boolean wizardWon() {
            return wizardHitPoints > 0 && opponentHitPoints <= 0;
        }

        public boolean wizardCanCast(Spell spell) {
            if (wizardMana < spell.mana)
                return false;
            if (spell == Spell.Shield && shieldTurnsRemaining > 1)
                return false;
            if (spell == Spell.Poison && poisonTurnsRemaining > 1)
                return false;
            if (spell == Spell.Recharge && rechargeTurnsRemaining > 1)
                return false;
            return true;
        }

        public GameState wizardCasts(Spell spell, boolean hard) {

            GameState next = copy();
            next.wizardMana = wizardMana - spell.mana;

            if (hard)
                next.wizardHitPoints -= 1;
            if (next.opponentWon())
                return next;

            // player turn
            if (next.rechargeTurnsRemaining > 0) {
                next.wizardMana += 101;
                next.rechargeTurnsRemaining--;
            }
            if (next.poisonTurnsRemaining > 0) {
                next.opponentHitPoints -= 3;
                next.poisonTurnsRemaining--;
            }
            if (next.shieldTurnsRemaining > 0)
                next.shieldTurnsRemaining--;

            if (spell == Spell.MagicMissle) {
                next.opponentHitPoints -= 4;
            } else if (spell == Spell.Drain) {
                next.opponentHitPoints -= 2;
                next.wizardHitPoints += 2;
            } else if (spell == Spell.Shield) {
                next.shieldTurnsRemaining = 6;
            } else if (spell == Spell.Poison) {
                next.poisonTurnsRemaining = 6;
            } else if (spell == Spell.Recharge) {
                next.rechargeTurnsRemaining = 5;
            }

            // no need to check opponent's strike - he's dead.
            if (next.opponentHitPoints <= 0) {
                return next;
            }

            // opponent turn
            if (next.rechargeTurnsRemaining > 0) {
                next.wizardMana += 101;
                next.rechargeTurnsRemaining--;
            }
            if (next.poisonTurnsRemaining > 0) {
                next.opponentHitPoints -= 3;
                next.poisonTurnsRemaining--;
            }
            if (next.opponentHitPoints <= 0) {
                return next;
            }

            if (next.shieldTurnsRemaining > 0) {
                next.wizardHitPoints -= Math.max(1, opponentDamage - 7);
                next.shieldTurnsRemaining--;
            } else
                next.wizardHitPoints -= Math.max(1, opponentDamage);

            return next;
        }

        private GameState copy() {
            GameState out = new GameState();
            out.opponentDamage = opponentDamage;
            out.opponentHitPoints = opponentHitPoints;
            out.poisonTurnsRemaining = poisonTurnsRemaining;
            out.shieldTurnsRemaining = shieldTurnsRemaining;
            out.rechargeTurnsRemaining = rechargeTurnsRemaining;
            out.wizardHitPoints = wizardHitPoints;
            out.wizardMana = wizardMana;
            return out;
        }
    }

    static enum Spell {
        MagicMissle(53), Drain(73), Shield(113), Poison(173), Recharge(229);

        int mana;

        Spell(int mana) {
            this.mana = mana;
        }
    };

    static boolean canFindBetterSolution(SpellHistory bestSolution,
            SpellHistory spellsSoFar, GameState currentGameState,
            boolean hard) {

        // System.out.println(spellsSoFar);
        // System.out.println(currentGameState);

        if (!spellsSoFar.isBetterThan(bestSolution)
                || currentGameState.opponentWon())
            return false;

        if (currentGameState.wizardWon()) {
            assert spellsSoFar.isBetterThan(bestSolution);
            bestSolution.replace(spellsSoFar);
            return true;
        }

        boolean solutionFound = false;
        for (Spell spell : Spell.values()) {
            if (currentGameState.wizardCanCast(spell)) {
                GameState nextGameState = currentGameState.wizardCasts(spell,
                        hard);
                spellsSoFar.add(spell);
                if (canFindBetterSolution(bestSolution, spellsSoFar,
                        nextGameState, hard))
                    solutionFound = true;
                spellsSoFar.removeLast();
            }
        }

        return solutionFound;
    }

    public static void main(String[] strings) {
        SpellHistory bestSolution = new SpellHistory();
        GameState start = GameState.start(50, 500, 58, 9);
        canFindBetterSolution(bestSolution, new SpellHistory(), start, false);
        System.out.println("Day 22 part 1: " + bestSolution.totalMana());

        bestSolution.clear();
        canFindBetterSolution(bestSolution, new SpellHistory(), start, true);
        System.out.println("Day 22 part 2: " + bestSolution.totalMana());
    }

}
