package adventofcode2015;

import static adventofcode2015.Day11.passwordIsValid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Day11Test {

    @Test
    public void test() {
        assertThat(passwordIsValid("hijklmmn")).isFalse();
        assertThat(passwordIsValid("abbceffg")).isFalse();
        assertThat(passwordIsValid("abbcegjk")).isFalse();
        assertThat(passwordIsValid("abcdffaa")).isTrue();
        assertThat(passwordIsValid("ghjaabcc")).isTrue();
    }

    @Test
    public void testNextValidPassword() throws Exception {
        assertThat(Day11.nextValidPassword("ghijklmn")).isEqualTo("ghjaabcc");
    }
}
