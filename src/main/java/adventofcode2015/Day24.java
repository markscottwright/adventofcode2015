package adventofcode2015;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class Day24 {

    static class PackageArrangement {

        @Override
        public String toString() {
            return "PackageArrangement [packages=" + packages
                    + ", quantumEntanglement=" + quantumEntanglement
                    + ", weight=" + weight + "]";
        }

        TreeSet<Integer> packages = new TreeSet<Integer>();
        BigInteger quantumEntanglement = BigInteger.ONE;
        int weight = 0;

        public PackageArrangement copy() {
            PackageArrangement out = new PackageArrangement();
            out.packages.addAll(this.packages);
            out.quantumEntanglement = quantumEntanglement;
            out.weight = weight;
            return out;
        }

        public void add(Integer nextPackage) {
            packages.add(nextPackage);
            weight += nextPackage;
            quantumEntanglement = quantumEntanglement
                    .multiply(BigInteger.valueOf(nextPackage));
        }

        public void remove(Integer nextPackage) {
            packages.remove(nextPackage);
            weight -= nextPackage;
            quantumEntanglement = quantumEntanglement
                    .divide(BigInteger.valueOf(nextPackage));
        }

        public boolean isBetterThan(PackageArrangement bestGroup1) {
            return bestGroup1 == null
                    || packages.size() <= bestGroup1.packages.size()
                            && quantumEntanglement.compareTo(
                                    bestGroup1.quantumEntanglement) < 0;
        }

        public void addAll(Collection<Integer> remaining) {
            for (int i : remaining) {
                packages.add(i);
                weight += i;
                quantumEntanglement = quantumEntanglement
                        .multiply(BigInteger.valueOf(i));
            }
        }
    }

    static class Packer {
        PackageArrangement bestGroup1 = null;
        private ArrayList<Integer> input;

        public Packer(List<Integer> input) {
            this.input = new ArrayList<Integer>(input);
        }

        public boolean findSolutionForGroups(int numGroups) {
            return arrangePackages(Util.sum(input) / numGroups, input,
                    new PackageArrangement(), new ArrayList<>(), numGroups - 1);
        }

        boolean arrangePackages(int goalWeight, ArrayList<Integer> remaining,
                PackageArrangement group1, ArrayList<Integer> forOtherGroups,
                int numOtherGroups) {
            if (!group1.isBetterThan(bestGroup1)) {
                return false;
            } else if (group1.weight > goalWeight) {
                return false;
            } else if (group1.weight == goalWeight) {
                ArrayList<PackageArrangement> otherGroups = new ArrayList<>();
                for (int i = 0; i < numOtherGroups; ++i)
                    otherGroups.add(new PackageArrangement());
                ArrayList<Integer> leftToArrange = new ArrayList<Integer>(
                        remaining);
                leftToArrange.addAll(forOtherGroups);
                if (anyEqualDistributionExists(goalWeight, leftToArrange,
                        otherGroups)) {
                    bestGroup1 = group1.copy();
                    // System.out.println(bestGroup1);
                    return true;
                }
                return false;
            } else if (remaining.isEmpty()) {
                return false;
            } else {
                boolean solution = false;

                int next = remaining.remove(0);
                forOtherGroups.add(0, next);
                if (arrangePackages(goalWeight, remaining, group1,
                        forOtherGroups, numOtherGroups))
                    solution = true;
                group1.add(next);
                forOtherGroups.remove(0);
                if (arrangePackages(goalWeight, remaining, group1,
                        forOtherGroups, numOtherGroups))
                    solution = true;
                group1.remove(next);
                remaining.add(0, next);
                return solution;
            }
        }

        public boolean anyEqualDistributionExists(int goalWeight,
                ArrayList<Integer> remaining, List<PackageArrangement> groups) {
            if (remaining.isEmpty()) {
                for (PackageArrangement g : groups)
                    if (g.weight != goalWeight)
                        return false;
                return true;
            }

            else {
                var next = remaining.remove(0);
                for (PackageArrangement g : groups) {
                    g.add(next);
                    if (g.weight <= goalWeight && anyEqualDistributionExists(
                            goalWeight, remaining, groups))
                        return true;
                    g.remove(next);
                }
                remaining.add(0, next);
                return false;
            }
        }

    }

    public static void main(String[] strings) {
        var input = Arrays.asList(1, 3, 5, 11, 13, 17, 19, 23, 29, 31, 41, 43,
                47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109,
                113);

        Packer packer = new Packer(input);
        packer.findSolutionForGroups(3);
        System.out.println(
                "Day 24 part 1: " + packer.bestGroup1.quantumEntanglement);
        packer = new Packer(input);
        packer.findSolutionForGroups(4);
        System.out.println(
                "Day 24 part 2: " + packer.bestGroup1.quantumEntanglement);
    }

}
