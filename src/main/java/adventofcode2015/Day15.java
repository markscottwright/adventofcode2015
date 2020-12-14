package adventofcode2015;

import static adventofcode2015.Util.parseAndCollectForDay;
import static java.lang.Integer.parseInt;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Day15 {
    private static final class IngredientOptimizerFor500Calories
            extends IngredientOptimizer {
        private IngredientOptimizerFor500Calories(
                List<Ingredient> ingredients) {
            super(ingredients);
        }

        @Override
        protected void rememberIfBest(HashMap<Ingredient, Integer> amounts) {
            int calories = 0;
            for (var ingredientAndAmount : amounts.entrySet()) {
                calories += ingredientAndAmount.getKey().calories
                        * ingredientAndAmount.getValue();
            }
            if (calories == 500)
                super.rememberIfBest(amounts);
        }
    }

    static public class Ingredient {
        static Pattern parser = Pattern.compile(
                "([a-zA-Z]+): capacity ([-0-9]+), durability ([-0-9]+), flavor ([-0-9]+), texture ([-0-9]+), calories ([-0-9]+)");
        public final String name;
        public final int capacity;
        public final int durability;
        public final int flavor;
        public final int texture;
        public final int calories;

        public Ingredient(String name, int capacity, int durability, int flavor,
                int texture, int calories) {
            this.name = name;
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }

        static Ingredient parse(String s) {
            List<String> groups = Util.parseToGroups(parser, s);
            return new Ingredient(groups.get(0), parseInt(groups.get(1)),
                    parseInt(groups.get(2)), parseInt(groups.get(3)),
                    parseInt(groups.get(4)), parseInt(groups.get(5)));
        }

        @Override
        public String toString() {
            return "Ingredient [name=" + name + ", capacity=" + capacity
                    + ", durability=" + durability + ", flavor=" + flavor
                    + ", texture=" + texture + ", calories=" + calories + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Ingredient other = (Ingredient) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }
    }

    static class IngredientOptimizer {
        private List<Ingredient> ingredients;
        private HashMap<Ingredient, Integer> bestSolution = new HashMap<>();
        private long bestScore = Long.MIN_VALUE;

        public IngredientOptimizer(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

        public HashMap<Ingredient, Integer> optimizeIngrediants() {
            optimizeIngredients(new ArrayList<>(ingredients), new HashMap<>(),
                    100);
            return bestSolution;
        }

        private void optimizeIngredients(
                ArrayList<Ingredient> remainingIngredients,
                HashMap<Ingredient, Integer> amounts, int amountRemaining) {
            Ingredient ingredientToVary = remainingIngredients.remove(0);
            if (remainingIngredients.size() == 0) {
                amounts.put(ingredientToVary, amountRemaining);
                rememberIfBest(amounts);
            } else {
                for (int i = 0; i < amountRemaining; ++i) {
                    amounts.put(ingredientToVary, i);
                    optimizeIngredients(remainingIngredients, amounts,
                            amountRemaining - i);
                }
            }
            remainingIngredients.add(0, ingredientToVary);
        }

        protected void rememberIfBest(HashMap<Ingredient, Integer> amounts) {
            long score = score(amounts);
            if (score > getBestScore()) {
                bestScore = score;
                bestSolution = new HashMap<Ingredient, Integer>(amounts);
            }
        }

        public static long score(HashMap<Ingredient, Integer> amounts) {
            if (amounts.values().stream().mapToInt(v -> v).sum() != 100)
                throw new RuntimeException("Bad ingredients:" + amounts);

            long capacity = 0;
            long durability = 0;
            long flavor = 0;
            long texture = 0;
            for (var ingredientAndAmount : amounts.entrySet()) {
                capacity += ingredientAndAmount.getKey().capacity
                        * ingredientAndAmount.getValue();
                durability += ingredientAndAmount.getKey().durability
                        * ingredientAndAmount.getValue();
                flavor += ingredientAndAmount.getKey().flavor
                        * ingredientAndAmount.getValue();
                texture += ingredientAndAmount.getKey().texture
                        * ingredientAndAmount.getValue();
            }
            long score = max(0, capacity) * max(0, durability) * max(0, flavor)
                    * max(0, texture);
            return score;
        }

        public long getBestScore() {
            return bestScore;
        }
    }

    public static void main(String[] strings) {
        List<Ingredient> ingredients = parseAndCollectForDay(15,
                Ingredient::parse);
        var ingredientOptimizer = new IngredientOptimizer(ingredients);
        ingredientOptimizer.optimizeIngrediants();
        System.out.println("Day 15 part 1: " + ingredientOptimizer.getBestScore());

        var ingredientOptimizerFor500Calories = new IngredientOptimizerFor500Calories(
                ingredients);
        ingredientOptimizerFor500Calories.optimizeIngrediants();
        System.out.println("Day 15 part 2: "
                + ingredientOptimizerFor500Calories.getBestScore());

    }
}
