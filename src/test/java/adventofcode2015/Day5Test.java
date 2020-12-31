package adventofcode2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Day5Test {

    @Test
    public void testPart2() {
        assertThat(Day5.stringIsNicePart2("qjhvhtzxzqqjkmpb")).isTrue();
        assertThat(Day5.stringIsNicePart2("xxyxxz")).isTrue();
        assertThat(Day5.stringIsNicePart2("uurcxstgmygtbstg")).isFalse();
        assertThat(Day5.stringIsNicePart2("ieodomkazucvgmuy")).isFalse();
        
        assertThat(Day5.containsTwoPair("xyxy")).isTrue();
        assertThat(Day5.containsTwoPair("aabcdefgaa")).isTrue();
        assertThat(Day5.containsTwoPair("aaa")).isFalse();
        
        assertThat(Day5.containsPairWithOneBetween("aaa")).isTrue();
        
        
    }

}
