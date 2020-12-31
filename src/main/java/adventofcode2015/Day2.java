package adventofcode2015;

import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day2 {
    private static final String INPUT = "src/main/resources/day2.txt";

    static public class GiftBox {
        int height;
        int depth;
        int width;

        public GiftBox(int height, int depth, int width) {
            this.height = height;
            this.depth = depth;
            this.width = width;
        }

        int surfaceArea() {
            return 2 * height * depth + 2 * height * width + 2 * depth * width;
        }

        int smallestSide() {
            return Math.min(depth * width,
                    Math.min(height * depth, height * width));
        }

        int paperNeeded() {
            return surfaceArea() + smallestSide();
        }

        int cubicFeet() {
            return depth * width * height;
        }

        int minimumPerimeter() {
            return Math.min(2 * depth + 2 * width,
                    Math.min(2 * height + 2 * depth, 2 * height + 2 * width));
        }

        int ribbonNeeded() {
            return minimumPerimeter() + cubicFeet();
        }

        public static GiftBox parse(String boxDescription) {
            var sideStrings = boxDescription.split("x");
            return new GiftBox(parseInt(sideStrings[0]),
                    parseInt(sideStrings[1]), parseInt(sideStrings[2]));
        }
    }

    public static void main(String[] args) throws IOException {
        int totalPaperNeeded = Files.readAllLines(Paths.get(INPUT)).stream()
                .map(GiftBox::parse).mapToInt(GiftBox::paperNeeded).sum();
        System.out.println("Day 2 Part 1: " + totalPaperNeeded);
        
        int totalRibbonNeeded = Files.readAllLines(Paths.get(INPUT)).stream()
                .map(GiftBox::parse).mapToInt(GiftBox::ribbonNeeded).sum();
        System.out.println("Day 2 Part 2: " + totalRibbonNeeded);
    }
}
