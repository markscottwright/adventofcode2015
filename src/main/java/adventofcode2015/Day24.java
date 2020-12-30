package adventofcode2015;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Day24 {

    static class PackageArrangement {

        @Override
        public String toString() {
            return "PackageArrangement [packages=" + packages
                    + ", quantumEntanglement=" + quantumEntanglement
                    + ", weight=" + weight + "]";
        }

        HashSet<Integer> packages = new HashSet<Integer>();
        BigInteger quantumEntanglement = BigInteger.ONE;
        int weight = 0;

        BigInteger getQuantumEntanglement() {
            return quantumEntanglement;
        }

        int getWeigth() {
            return weight;
        }

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
            return bestGroup1 == null || quantumEntanglement
                    .compareTo(bestGroup1.quantumEntanglement) < 0;
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
        PackageArrangement bestGroup2 = null;
        PackageArrangement bestGroup3 = null;
        private ArrayList<Integer> input;

        public Packer(List<Integer> input) {
            this.input = new ArrayList<Integer>(input);
            Collections.reverse(input);
        }

        boolean arrangePackages(ArrayList<Integer> remaining,
                PackageArrangement group1, PackageArrangement group2,
                PackageArrangement group3) {

            if (!group1.isBetterThan(bestGroup1))
                return false;

            else if (remaining.isEmpty()) {
                // not balanced
                if ((group1.getWeigth() != group2.getWeigth())
                        || (group1.getWeigth() != group3.getWeigth()))
                    return false;

                // balanced and better than previous solution
                else {
                    bestGroup1 = group1.copy();
                    bestGroup2 = group2.copy();
                    bestGroup3 = group3.copy();
                    return true;
                }
            }

            else {
                int remainingWeight = remaining.stream().mapToInt(i -> i).sum();
                if (group1.getWeigth() > remainingWeight + group2.getWeigth())
                    return false;
                if (group1.getWeigth() > remainingWeight + group3.getWeigth())
                    return false;
                if (group2.getWeigth() > remainingWeight + group3.getWeigth())
                    return false;
                if (group2.getWeigth() > remainingWeight + group1.getWeigth())
                    return false;
                if (group3.getWeigth() > remainingWeight + group1.getWeigth())
                    return false;
                if (group3.getWeigth() > remainingWeight + group2.getWeigth())
                    return false;

                Integer nextPackage = remaining.remove(remaining.size() - 1);

                boolean solutionFound = false;
                group1.add(nextPackage);
                if (arrangePackages(remaining, group1, group2, group3))
                    solutionFound = true;
                group1.remove(nextPackage);
                group2.add(nextPackage);
                if (arrangePackages(remaining, group1, group2, group3))
                    solutionFound = true;
                group2.remove(nextPackage);
                group3.add(nextPackage);
                if (arrangePackages(remaining, group1, group2, group3))
                    solutionFound = true;
                group3.remove(nextPackage);
                remaining.add(nextPackage);
                return solutionFound;
            }
        }

        public void findSolution() {
            arrangePackages(input, new PackageArrangement(),
                    new PackageArrangement(), new PackageArrangement());
        }
    }

    static class FourGroupPacker {
        PackageArrangement bestGroup1 = null;
        PackageArrangement bestGroup2 = null;
        PackageArrangement bestGroup3 = null;
        PackageArrangement bestGroup4 = null;
        private ArrayList<Integer> input;

        public FourGroupPacker(List<Integer> input) {
            this.input = new ArrayList<Integer>(input);
            Collections.reverse(input);
        }

        boolean arrangePackages(ArrayList<Integer> remaining,
                PackageArrangement group1, PackageArrangement group2,
                PackageArrangement group3, PackageArrangement group4) {

            if (!group1.isBetterThan(bestGroup1))
                return false;

            else if (remaining.isEmpty()) {
                // not balanced
                if ((group1.getWeigth() != group2.getWeigth())
                        || (group1.getWeigth() != group3.getWeigth())
                        || (group1.getWeigth() != group4.getWeigth()))
                    return false;

                // balanced and better than previous solution
                else {
                    bestGroup1 = group1.copy();
                    bestGroup2 = group2.copy();
                    bestGroup3 = group3.copy();
                    bestGroup4 = group4.copy();
                    return true;
                }
            }

            else {
                int remainingWeight = remaining.stream().mapToInt(i -> i).sum();
                if (group1.getWeigth() > remainingWeight + group2.getWeigth())
                    return false;
                if (group1.getWeigth() > remainingWeight + group3.getWeigth())
                    return false;
                if (group1.getWeigth() > remainingWeight + group4.getWeigth())
                    return false;
                if (group2.getWeigth() > remainingWeight + group3.getWeigth())
                    return false;
                if (group2.getWeigth() > remainingWeight + group1.getWeigth())
                    return false;
                if (group2.getWeigth() > remainingWeight + group4.getWeigth())
                    return false;
                if (group3.getWeigth() > remainingWeight + group1.getWeigth())
                    return false;
                if (group3.getWeigth() > remainingWeight + group2.getWeigth())
                    return false;
                if (group3.getWeigth() > remainingWeight + group4.getWeigth())
                    return false;
                if (group4.getWeigth() > remainingWeight + group1.getWeigth())
                    return false;
                if (group4.getWeigth() > remainingWeight + group2.getWeigth())
                    return false;
                if (group4.getWeigth() > remainingWeight + group3.getWeigth())
                    return false;

                Integer nextPackage = remaining.remove(remaining.size() - 1);

                boolean solutionFound = false;
                group1.add(nextPackage);
                if (arrangePackages(remaining, group1, group2, group3, group4))
                    solutionFound = true;
                group1.remove(nextPackage);
                group2.add(nextPackage);
                if (arrangePackages(remaining, group1, group2, group3, group4))
                    solutionFound = true;
                group2.remove(nextPackage);
                group3.add(nextPackage);
                if (arrangePackages(remaining, group1, group2, group3, group4))
                    solutionFound = true;
                group3.remove(nextPackage);
                group4.add(nextPackage);
                if (arrangePackages(remaining, group1, group2, group3, group4))
                    solutionFound = true;
                group4.remove(nextPackage);

                remaining.add(nextPackage);
                return solutionFound;
            }
        }

        public void findSolution() {
            arrangePackages(input, new PackageArrangement(),
                    new PackageArrangement(), new PackageArrangement(),
                    new PackageArrangement());
        }
    }

    static class RecursivePacker {
        ArrayList<Integer> input = new ArrayList<Integer>();
        ArrayList<PackageArrangement> solution = new ArrayList<>();

        public RecursivePacker(Collection<Integer> input) {
            this.input.addAll(input);
        }

        boolean arrange(int perGroupWeight, HashSet<Integer> remaining,
                ArrayList<PackageArrangement> assignedGroups,
                ArrayList<PackageArrangement> remainingGroups) {

            if (remainingGroups.size() == 1) {
                if (remainingGroups.get(0).weight != perGroupWeight)
                    return false;

                remainingGroups.get(0).addAll(remaining);
                assignedGroups.add(remainingGroups.remove(0));

                if (solution.isEmpty() || assignedGroups.get(0)
                        .isBetterThan(solution.get(0))) {
                    setSolution(assignedGroups);
                    remainingGroups.add(
                            assignedGroups.remove(assignedGroups.size() - 1));
                    return true;
                } else {
                    remainingGroups.add(
                            assignedGroups.remove(assignedGroups.size() - 1));
                    return false;
                }
            }

            if (remainingGroups.get(0).weight > perGroupWeight)
                return false;
            else if (remainingGroups.get(0).weight == perGroupWeight) {
                assignedGroups.add(remainingGroups.remove(0));
                boolean solution = arrange(perGroupWeight, remaining,
                        assignedGroups, remainingGroups);
                remainingGroups
                        .add(assignedGroups.remove(assignedGroups.size() - 1));
                return solution;
            }

            else {
                boolean solution = false;
                for (Integer i : new ArrayList<>(remaining)) {
                    remainingGroups.get(0).add(i);
                    remaining.remove(i);
                    if (arrange(perGroupWeight, remaining, assignedGroups,
                            remainingGroups))
                        solution = true;
                    remaining.add(i);
                    remainingGroups.get(0).remove(i);
                }
                return solution;
            }

        }

        private void setSolution(ArrayList<PackageArrangement> assignedGroups) {
            solution = new ArrayList<PackageArrangement>();
            for (PackageArrangement a : assignedGroups)
                solution.add(a.copy());
        }

        public void findSolution() {
            ArrayList<PackageArrangement> assignedGroups = new ArrayList<PackageArrangement>();
            ArrayList<PackageArrangement> remainingGroups = new ArrayList<PackageArrangement>();
            remainingGroups.add(new PackageArrangement());
            remainingGroups.add(new PackageArrangement());
            remainingGroups.add(new PackageArrangement());
            var perGroupWeight = input.stream().mapToInt(i -> i).sum() / 3;
            arrange(perGroupWeight, new HashSet<>(input), assignedGroups,
                    remainingGroups);
        }
    }
    
    public static void main(String[] strings) {
        var input = Arrays.asList(1, 3, 5, 11, 13, 17, 19, 23, 29, 31, 41, 43,
                47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109,
                113);

        // RecursivePacker packer = new RecursivePacker(input);
        // packer.findSolution();
        // packer.solution.forEach(System.out::println);
        Packer packer = new Packer(input);
        packer.findSolution();
        System.out.println(packer.bestGroup1);
        System.out.println(packer.bestGroup2);
        System.out.println(packer.bestGroup3);

        FourGroupPacker packer2 = new FourGroupPacker(input);
        packer2.findSolution();
        System.out.println(packer2.bestGroup1);
        System.out.println(packer2.bestGroup2);
        System.out.println(packer2.bestGroup3);
        System.out.println(packer2.bestGroup4);
    }

}
