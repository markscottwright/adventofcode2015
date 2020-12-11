package adventofcode2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Day14Test {

    @Test
    public void test() {
        var comet = new Day14.ReindeerSpeed("Comet", 14, 10, 127);
        var dancer = new Day14.ReindeerSpeed("Dancer", 16, 11, 162);

        assertThat(comet.distanceFlownIn(1)).isEqualTo(14);
        assertThat(comet.distanceFlownIn(10)).isEqualTo(140);
        assertThat(comet.distanceFlownIn(11)).isEqualTo(140);
        assertThat(comet.distanceFlownIn(1000)).isEqualTo(1120);
        assertThat(dancer.distanceFlownIn(1000)).isEqualTo(1056);
    }

}
