package adventofcode2015;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

public class Day3 {
    private static final String INPUT = "src/main/resources/day3.txt";

    static class Location {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + "," + y;
        }

        Location move(char instruction) {
            if (instruction == '^')
                return new Location(x, y - 1);
            else if (instruction == '>')
                return new Location(x + 1, y);
            else if (instruction == '<')
                return new Location(x - 1, y);
            else if (instruction == 'v')
                return new Location(x, y + 1);
            else
                throw new IllegalArgumentException();
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Location other = (Location) obj;
            return x == other.x && y == other.y;
        }
    }

    static HashMap<Location, Integer> visit(InputStream moves)
            throws IOException {
        HashMap<Location, Integer> locationsSeen = new HashMap<>();

        Location location = new Location(0, 0);
        locationsSeen.put(location, 1);
        int move;
        while ((move = moves.read()) > 0) {
            location = location.move((char) move);
            locationsSeen.put(location,
                    locationsSeen.getOrDefault(location, 0) + 1);
        }

        return locationsSeen;
    }

    static HashMap<Location, Integer> visitWithRobot(InputStream moves)
            throws IOException {
        HashMap<Location, Integer> locationsSeen = new HashMap<>();

        Location santaLocation = new Location(0, 0);
        Location robotLocation = new Location(0, 0);
        boolean santasTurn = true;
        locationsSeen.put(santaLocation, 1);
        locationsSeen.put(robotLocation, 1);
        int move;
        while ((move = moves.read()) > 0) {
            if (santasTurn) {
                santaLocation = santaLocation.move((char) move);
                locationsSeen.put(santaLocation,
                        locationsSeen.getOrDefault(santaLocation, 0) + 1);
            } else {
                robotLocation = robotLocation.move((char) move);
                locationsSeen.put(robotLocation,
                        locationsSeen.getOrDefault(robotLocation, 0) + 1);
            }
            santasTurn = !santasTurn;
        }

        return locationsSeen;
    }

    public static void main(String[] args) throws FileNotFoundException,
            IOException {

        try (FileInputStream moves = new FileInputStream(new File(INPUT))) {
            System.out.println("Day 3 part 1: " + visit(moves).keySet().size());
        }
        try (FileInputStream moves = new FileInputStream(new File(INPUT))) {
            System.out.println("Day 3 part 2: " + visitWithRobot(moves).keySet().size());
        }
    }
}
