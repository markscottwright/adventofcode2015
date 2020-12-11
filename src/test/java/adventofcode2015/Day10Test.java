package adventofcode2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Day10Test {

    @Test
    public void test() {
        assertThat(Day10.lookAndSay("1")).isEqualTo("11");
        assertThat(Day10.lookAndSay("11")).isEqualTo("21");
        assertThat(Day10.lookAndSay("21")).isEqualTo("1211");
        assertThat(Day10.lookAndSay("1211")).isEqualTo("111221");
        assertThat(Day10.lookAndSay("111221")).isEqualTo("312211");
    }

}
