package adventofcode2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import adventofcode2015.Day9.PairDistance;

public class Day9 {

    static public class PairDistance {
        @Override
        public String toString() {
            return "PairDistance [from=" + from + ", to=" + to + ", distance="
                    + distance + "]";
        }

        public final String from;
        public final String to;
        public final int distance;

        public PairDistance(String from, String to, int distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + distance;
            result = prime * result + ((from == null) ? 0 : from.hashCode());
            result = prime * result + ((to == null) ? 0 : to.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            PairDistance other = (PairDistance) obj;
            if (distance != other.distance)
                return false;
            if (from == null) {
                if (other.from != null)
                    return false;
            } else if (!from.equals(other.from))
                return false;
            if (to == null) {
                if (other.to != null)
                    return false;
            } else if (!to.equals(other.to))
                return false;
            return true;
        }

        static PairDistance parse(String s) {
            var fields = s.split("\\s+");
            return new PairDistance(fields[0], fields[2],
                    Integer.parseInt(fields[4]));
        }
    }

    static class DistanceTo implements Comparable<DistanceTo> {
        final int distance;
        final String endpoint;

        public DistanceTo(String endpoint, int distance) {
            this.endpoint = endpoint;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "DistanceTo [distance=" + distance + ", endpoint=" + endpoint
                    + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + distance;
            result = prime * result
                    + ((endpoint == null) ? 0 : endpoint.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            DistanceTo other = (DistanceTo) obj;
            if (distance != other.distance)
                return false;
            if (endpoint == null) {
                if (other.endpoint != null)
                    return false;
            } else if (!endpoint.equals(other.endpoint))
                return false;
            return true;
        }

        @Override
        public int compareTo(DistanceTo o) {
            return Integer.compare(distance, o.distance);
        }
    }

    public static void main(String[] args) {
        var distances = Util.inputLinesForDay(9).stream()
                .map(PairDistance::parse).collect(Collectors.toSet());

        HashMap<String, TreeSet<DistanceTo>> edges = new HashMap<>();
        for (PairDistance pairDistance : distances) {
            if (!edges.containsKey(pairDistance.from)) {
                edges.put(pairDistance.from, new TreeSet<>());
            }
            if (!edges.containsKey(pairDistance.to)) {
                edges.put(pairDistance.to, new TreeSet<>());
            }
            edges.get(pairDistance.from).add(
                    new DistanceTo(pairDistance.to, pairDistance.distance));
            edges.get(pairDistance.to).add(
                    new DistanceTo(pairDistance.from, pairDistance.distance));
        }

        var start = edges.keySet().stream()
                .min((k1, k2) -> Integer.compare(
                        edges.get(k1).iterator().next().distance,
                        edges.get(k2).iterator().next().distance))
                .get();
        MinPathFinder minPathFinder = new MinPathFinder(edges);
        ArrayList<String> path = minPathFinder.findMinimumPath();
        System.out.println(path);
        System.out.println(minPathFinder.bestPathCost);
    }

}
