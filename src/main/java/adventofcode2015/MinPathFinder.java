package adventofcode2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import adventofcode2015.Day9.DistanceTo;

public class MinPathFinder {

    private HashMap<String, TreeSet<DistanceTo>> edges;
    int bestPathCost = Integer.MAX_VALUE;
    ArrayList<String> bestPath = null;

    public MinPathFinder(HashMap<String, TreeSet<DistanceTo>> edges) {
        this.edges = edges;
    }

    public ArrayList<String> findPath() {
        for (String start : edges.keySet()) {
            var path = new ArrayList<String>();
            path.add(start);
            findPath(path, 0);
        }
        return bestPath;
    }

    private void findPath(ArrayList<String> path, int cost) {
        if (path.size() == edges.keySet().size() && cost < bestPathCost) {
            bestPathCost = cost;
            bestPath = new ArrayList<>(path);
        }

        for (DistanceTo distanceTo : edges.get(path.get(path.size() - 1))) {
            if (seen(path, distanceTo.endpoint))
                continue;

            int nextCost = cost + distanceTo.distance;
            if (nextCost > bestPathCost)
                continue;

            path.add(distanceTo.endpoint);
            findPath(path, nextCost);
            path.remove(path.size() - 1);
        }
    }

    private boolean seen(ArrayList<String> path, String endpoint) {
        for (String e : path) {
            if (e.equals(endpoint))
                return true;
        }
        return false;
    }

}
