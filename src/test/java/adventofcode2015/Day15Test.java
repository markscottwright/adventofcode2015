package adventofcode2015;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import org.junit.Test;

import adventofcode2015.Day15.Ingredient;
import adventofcode2015.Day15.IngredientOptimizer;

public class Day15Test {

    @Test
    public void test() {
        Ingredient butterscotch = new Ingredient("Butterscotch", -1, -2, 6, 3,
                8);
        Ingredient cinnamon = new Ingredient("cinnamon", 2, 3, -2, -1, 3);
        HashMap<Ingredient, Integer> mixture = new HashMap<>();
        mixture.put(butterscotch, 44);
        mixture.put(cinnamon, 56);
        assertThat(IngredientOptimizer.score(mixture)).isEqualTo(62842880L);
    }

}
