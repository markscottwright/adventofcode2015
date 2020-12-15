package adventofcode2015;

import java.util.ArrayList;
import java.util.List;

public class ContainerFiller {

    private List<Integer> containers;
    private List<ArrayList<Integer>> minimumSolutions = new ArrayList<>();
    private int desiredCapacity;

    public ContainerFiller(List<Integer> containers, int desiredCapacity) {
        this.containers = containers;
        this.desiredCapacity = desiredCapacity;
    }

    int numValidCombinations(ArrayList<Integer> containers,
            ArrayList<Integer> containersRemaining) {
        int containerSum = Util.sum(containers);
        if (containerSum == desiredCapacity) {

            // first solution found
            if (minimumSolutions.size() == 0) {
                minimumSolutions.add(new ArrayList<>(containers));
            }

            // another minimum
            else if (containers.size() == minimumSolutions.get(0).size()) {
                minimumSolutions.add(new ArrayList<>(containers));
            }

            // better than previous minimum solution
            else if (containers.size() < minimumSolutions.get(0).size()) {
                minimumSolutions.clear();
                minimumSolutions.add(new ArrayList<>(containers));
            }
            return 1;
        } else if (containerSum > desiredCapacity)
            return 0;
        else if (containersRemaining.isEmpty())
            return 0;
        else {
            int combinations = 0;
            Integer container = containersRemaining.remove(0);
            combinations += numValidCombinations(containers,
                    containersRemaining);
            containers.add(container);
            combinations += numValidCombinations(containers,
                    containersRemaining);
            containersRemaining.add(0, container);
            containers.remove(containers.size() - 1);
            return combinations;
        }
    }

    int getNumMinimumSolutions() {
        return minimumSolutions.size();
    }

    public int numValidCombinations() {
        return numValidCombinations(new ArrayList<>(),
                new ArrayList<>(containers));
    }
}
