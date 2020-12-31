package adventofcode2015;

public class Day20 {

    static int sumEvenDivsors(int n) {
        int sum = 0;
        for (int i = 1; i * i <= n; ++i) {
            if (n % i == 0) {
                sum += i;
                if (n / i != i)
                    sum += n / i;
            }
        }
        return sum;
    }

    static int sumEvenDivsorsWithMultipleUpTo50(int n) {
        int sum = 0;
        for (int i = 1; i * i <= n; ++i) {
            if (n % i == 0) {
                if (n / i <= 50)
                    sum += i;
                if (n / i != i && (n / (n / i)) <= 50)
                    sum += n / i;
            }
        }
        return sum;
    }

    static int numPresentsAtHouse(int houseNumber) {
        return sumEvenDivsors(houseNumber) * 10;
    }

    static int numPresentsAtHouse2(int houseNumber) {
        return sumEvenDivsorsWithMultipleUpTo50(houseNumber) * 11;
    }

    public static void main(String[] strings) {
        // Stopwatch stopwatch = Stopwatch.createStarted();
        int houseNumber = 0;
        int n = 33100000;
        for (houseNumber = 1; houseNumber < n; ++houseNumber) {
            if (numPresentsAtHouse(houseNumber) >= n)
                break;
        }
        System.out.println("Day 20 part 1: " + houseNumber);

        for (houseNumber = 1; houseNumber < n; ++houseNumber) {
            if (numPresentsAtHouse2(houseNumber) >= n)
                break;
        }
        System.out.println("Day 20 part 2: " + houseNumber);
        // System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS)/1000.0);
    }

}
