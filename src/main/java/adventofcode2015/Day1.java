package adventofcode2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day1 {

    public static void main(String[] strings) throws IOException {
        String input = Files.readString(Path.of("src/main/resources/day1.txt"));

        int floor = 0;
        int position = 1;
        int basementEnteringInstructionPosition = 0;
        for (char c : input.toCharArray()) {
            if (c == ')')
                floor--;
            else if (c == '(')
                floor++;
            else
                throw new RuntimeException("Unexpected char:" + c);
            
            if (floor < 0 && basementEnteringInstructionPosition == 0)
                basementEnteringInstructionPosition = position;
            position++;
        }
        System.out.println("Day 1 part 1: " + floor);
        System.out.println("Day 1 part 2: " + basementEnteringInstructionPosition);
    }

}
