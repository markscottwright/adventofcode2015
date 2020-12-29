package adventofcode2015;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class Day23Test {

    @Test
    public void test() {
        //@formatter:off
        String instruction = "inc a\r\n"
                + "jio a, +2\r\n"
                + "tpl a\r\n"
                + "inc a";
        //@formatter:on
        var instructions = Arrays.stream(instruction.split("\r\n"))
                .map(Day23.Instruction::parse).collect(Collectors.toList());

        Day23.Computer c = new Day23.Computer();
        while (c.executeOneInstruction(instructions)) {
        }
        assertThat(c.registerA).isEqualTo(2);
    }

}
