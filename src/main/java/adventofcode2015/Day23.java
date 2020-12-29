package adventofcode2015;

import java.util.List;

public class Day23 {

    static public class JumpIfOne extends Instruction {

        private String register;
        private int offset;

        public JumpIfOne(String register, int offset) {
            this.register = register;
            this.offset = offset;
        }

        @Override
        public void apply(Computer computer) {
            boolean isEven = false;
            if (register.equals("a"))
                isEven = computer.registerA == 1;
            else
                isEven = computer.registerB == 1;

            if (isEven)
                computer.position += offset;
            else
                computer.position++;
        }
    }

    static public class JumpIfEven extends Instruction {

        private String register;
        private int offset;

        public JumpIfEven(String register, int offset) {
            this.register = register;
            this.offset = offset;
        }

        @Override
        public void apply(Computer computer) {
            boolean isEven = false;
            if (register.equals("a"))
                isEven = computer.registerA % 2 == 0;
            else
                isEven = computer.registerB % 2 == 0;

            if (isEven)
                computer.position += offset;
            else
                computer.position++;
        }

    }

    static public class Half extends Instruction {

        private String register;

        public Half(String register) {
            this.register = register;
        }

        @Override
        public void apply(Computer computer) {
            if (register.equals("a"))
                computer.registerA /= 2;
            else
                computer.registerB /= 2;
            computer.position += 1;
        }

    }

    static public class Triple extends Instruction {

        private String register;

        public Triple(String register) {
            this.register = register;
        }

        @Override
        public void apply(Computer computer) {
            if (register.equals("a"))
                computer.registerA *= 3;
            else
                computer.registerB *= 3;
            computer.position += 1;
        }
    }

    static public class Increment extends Instruction {

        private String register;

        public Increment(String register) {
            this.register = register;
        }

        @Override
        public void apply(Computer computer) {
            if (register.equals("a"))
                computer.registerA++;
            else
                computer.registerB++;
            computer.position += 1;
        }
    }

    static public class Jump extends Instruction {

        private int offset;

        public Jump(int offset) {
            this.offset = offset;
        }

        @Override
        public void apply(Computer computer) {
            computer.position += offset;
        }

    }

    static public abstract class Instruction {
        static Instruction parse(String l) {
            String[] fields = l.split(" ");
            if (fields[0].equals("hlf"))
                return new Half(fields[1]);
            else if (fields[0].equals("tpl"))
                return new Triple(fields[1]);
            else if (fields[0].equals("inc"))
                return new Increment(fields[1]);
            else if (fields[0].equals("jmp"))
                return new Jump(Integer.parseInt(fields[1]));
            else if (fields[0].equals("jie"))
                return new JumpIfEven(fields[1].replace(",", ""),
                        Integer.parseInt(fields[2]));
            else if (fields[0].equals("jio"))
                return new JumpIfOne(fields[1].replace(",", ""),
                        Integer.parseInt(fields[2]));
            else
                throw new RuntimeException("Unknown instruction " + l);
        }

        abstract public void apply(Computer computer);
    }

    static class Computer {
        int registerA = 0;
        int registerB = 0;
        int position = 0;

        boolean executeOneInstruction(List<Instruction> instructions) {
            if (position >= instructions.size())
                return false;
            instructions.get(position).apply(this);
            return true;
        }

        public void run(List<Instruction> instructions) {
            while (executeOneInstruction(instructions))
                ;
        }
    }

    public static void main(String[] strings) {
        List<Instruction> instructions = Util.parseAndCollectForDay(23,
                Instruction::parse);
        var computer = new Computer();
        computer.run(instructions);
        System.out.println("Day 23 part 1: " + computer.registerB);
        
        var computer2 = new Computer();
        computer2.registerA = 1;
        computer2.run(instructions);
        System.out.println("Day 23 part 2: " + computer2.registerB);

    }

}
