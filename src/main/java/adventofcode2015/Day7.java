package adventofcode2015;

import static java.lang.Integer.parseInt;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7 {
    public static class InputInstruction extends Instruction {

        @Override
        public String toString() {
            return input + " -> " + output;
        }

        private String input;

        public InputInstruction(String output, String input) {
            super(output);
            this.input = input;
        }

        @Override
        int getValue(Map<String, Instruction> outputToInstruction,
                Map<String, Integer> cache) {
            if (cache.containsKey(getOutput()))
                return cache.get(getOutput());
            // System.out.println(this);

            int value;
            if (isNumber(input)) {
                value = parseInt(input);
            } else {
                value = outputToInstruction.get(input)
                        .getValue(outputToInstruction, cache);
            }
            cache.put(getOutput(), value);
            return value;
        }

        @Override
        protected String getOutput() {
            return output;
        }

    }

    public static class NotInstruction extends Instruction {

        private String input;

        public NotInstruction(String output, String input) {
            super(output);
            assert (!isNumber(input));
            this.output = output;
            this.input = input;
        }

        @Override
        public String toString() {
            return "NOT " + input + " -> " + output;
        }

        @Override
        int getValue(Map<String, Instruction> outputToInstruction,
                Map<String, Integer> cache) {
            if (cache.containsKey(getOutput()))
                return cache.get(getOutput());
            // System.out.println(this);

            assert (!isNumber(input));
            int value = ~outputToInstruction.get(input)
                    .getValue(outputToInstruction, cache);
            cache.put(getOutput(), value);
            return value;
        }

    }

    public static class OrInstruction extends Instruction {

        private String input1;
        private String input2;

        public OrInstruction(String output, String input1, String input2) {
            super(output);
            assert (!isNumber(input1));
            assert (!isNumber(input2));
            this.input1 = input1;
            this.input2 = input2;
        }

        @Override
        int getValue(Map<String, Instruction> outputToInstruction,
                Map<String, Integer> cache) {
            if (cache.containsKey(getOutput()))
                return cache.get(getOutput());
            // System.out.println(this);

            int input1Value = isNumber(input1) ? parseInt(input1)
                    : outputToInstruction.get(input1)
                            .getValue(outputToInstruction, cache);
            int input2Value = isNumber(input2) ? parseInt(input2)
                    : outputToInstruction.get(input2)
                            .getValue(outputToInstruction, cache);

            int value = input1Value | input2Value;
            cache.put(getOutput(), value);
            return value;
        }

        @Override
        public String toString() {
            return input1 + " OR " + input2 + " -> " + output;
        }

    }

    public static class LshiftInstruction extends Instruction {

        private String input;
        private int shiftBy;

        public LshiftInstruction(String output, String input, int shiftBy) {
            super(output);
            assert (!isNumber(input));
            this.input = input;
            this.shiftBy = shiftBy;
        }

        @Override
        int getValue(Map<String, Instruction> outputToInstruction,
                Map<String, Integer> cache) {
            if (cache.containsKey(getOutput()))
                return cache.get(getOutput());
            // System.out.println(this);

            int inputValue = isNumber(input) ? parseInt(input)
                    : outputToInstruction.get(input)
                            .getValue(outputToInstruction, cache);
            int value = inputValue << shiftBy;
            cache.put(getOutput(), value);
            return value;
        }

        @Override
        public String toString() {
            return input + " LSHIFT " + shiftBy + " -> " + output;
        }

    }

    public static class RshiftInstruction extends Instruction {

        private String input;
        private int shiftBy;

        public RshiftInstruction(String output, String input, int shiftBy) {
            super(output);
            assert (!isNumber(input));
            this.input = input;
            this.shiftBy = shiftBy;
        }

        @Override
        int getValue(Map<String, Instruction> outputToInstruction,
                Map<String, Integer> cache) {
            if (cache.containsKey(getOutput()))
                return cache.get(getOutput());
            // System.out.println(this);

            int inputValue = isNumber(input) ? parseInt(input)
                    : outputToInstruction.get(input)
                            .getValue(outputToInstruction, cache);

            int value = inputValue >> shiftBy;
            cache.put(getOutput(), value);
            return value;
        }

        @Override
        public String toString() {
            return input + " RSHIFT " + shiftBy + " -> " + output;
        }

    }

    public static class AndInstruction extends Instruction {

        private String input1;
        private String input2;

        public AndInstruction(String output, String input1, String input2) {
            super(output);
            this.input1 = input1;
            this.input2 = input2;
        }

        @Override
        int getValue(Map<String, Instruction> outputToInstruction,
                Map<String, Integer> cache) {
            if (cache.containsKey(getOutput()))
                return cache.get(getOutput());
            // System.out.println(this);

            int input1Value = isNumber(input1) ? parseInt(input1)
                    : outputToInstruction.get(input1)
                            .getValue(outputToInstruction, cache);
            int input2Value = isNumber(input2) ? parseInt(input2)
                    : outputToInstruction.get(input2)
                            .getValue(outputToInstruction, cache);
            int value = input1Value & input2Value;
            cache.put(getOutput(), value);
            return value;
        }

        @Override
        public String toString() {
            return input1 + " AND " + input2 + " -> " + output;
        }
    }

    static abstract class Instruction {
        static Instruction parse(String line) {
            var fields = line.split("\\s+");
            if (fields[1].equals("AND"))
                return new AndInstruction(fields[4], fields[0], fields[2]);
            else if (fields[1].equals("RSHIFT"))
                return new RshiftInstruction(fields[4], fields[0],
                        parseInt(fields[2]));
            else if (fields[1].equals("LSHIFT"))
                return new LshiftInstruction(fields[4], fields[0],
                        parseInt(fields[2]));
            else if (fields[1].equals("OR"))
                return new OrInstruction(fields[4], fields[0], fields[2]);
            else if (fields[0].equals("NOT"))
                return new NotInstruction(fields[3], fields[1]);
            else
                return new InputInstruction(fields[2], fields[0]);
        }

        abstract int getValue(Map<String, Instruction> outputToInstruction,
                Map<String, Integer> cache);

        static boolean isNumber(String s) {
            return Pattern.matches("[0-9]+", s);
        }

        String output;

        public Instruction(String output) {
            this.output = output;
        }

        protected String getOutput() {
            return output;
        }
    }

    public static void main(String[] args) {
        var instructions = Util.inputLinesForDay(7).stream().map(Instruction::parse)
                .collect(Collectors.toList());
        var outputToInstruction = instructions.stream()
                .collect(Collectors.toMap(i -> i.getOutput(), i -> i));
        Map<String, Integer> cache = new HashMap<>();
        int part1 = outputToInstruction.get("a").getValue(outputToInstruction,
                cache);
        System.out.println("Day 7 part 1: " + part1);

        cache.clear();
        outputToInstruction.put("b", new InputInstruction("b", "" + part1));
        int part2 = outputToInstruction.get("a").getValue(outputToInstruction,
                cache);
        System.out.println("Day 7 part 2: " + part2);
    }
}
