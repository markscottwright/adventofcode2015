package adventofcode2015;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class Day17Test {

    @Test
    public void testPart1() {
        int n = new ContainerFiller(List.of(20, 15, 10, 5, 5), 25)
                .numValidCombinations();
        assertThat(n).isEqualTo(4);
    }

}
