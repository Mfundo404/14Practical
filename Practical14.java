import java.util.*;

public class Practical14 {

    static double average(double[] arr) {
        double sum = 0;
        for (double v : arr) sum += v;
        return sum / arr.length;
    }

    static double stdDev(double[] arr, double mean) {
        double sumSq = 0;
        for (double v : arr) sumSq += Math.pow(v - mean, 2);
        return Math.sqrt(sumSq / arr.length);
    }

    public static void main(String[] args) {
        int[] loadFactors = {75, 80, 85, 90, 95};
        int repetitions = 30;
        int batchSize = 10000;
        Random rand = new Random();

        System.out.println("LoadFactor | N | AvgOpen(s) | AvgChained(s) | StdOpen | StdChained");
        System.out.println("---------------------------------------------------------------");

        for (int lf : loadFactors) {
            int N = lf * 10000;
            int m = (int)(N / (lf / 100.0));

            openHash openTable = new openHash(m + 1);
            chainedHash chainedTable = new chainedHash(m + 1);

            // Insert N key-value pairs
            for (int i = 1; i <= N; i++) {
                String key = String.valueOf(i);
                String value = "Data" + i;
                openTable.insert(key, value);
                chainedTable.insert(key, value);
            }

            double[] openTimes = new double[repetitions];
            double[] chainedTimes = new double[repetitions];

            for (int r = 0; r < repetitions; r++) {
                // --- Open hash timing ---
                long start = System.nanoTime();
                for (int j = 0; j < batchSize; j++) {
                    int testKey = rand.nextInt(N) + 1;
                    String key = String.valueOf(testKey);
                    openTable.lookup(key);
                }
                long finish = System.nanoTime();
                openTimes[r] = (finish - start) / (double) batchSize / 1_000_000_000.0;

                // --- Chained hash timing ---
                start = System.nanoTime();
                for (int j = 0; j < batchSize; j++) {
                    int testKey = rand.nextInt(N) + 1;
                    String key = String.valueOf(testKey);
                    chainedTable.lookup(key);
                }
                finish = System.nanoTime();
                chainedTimes[r] = (finish - start) / (double) batchSize / 1_000_000_000.0;
            }

            double avgOpen = average(openTimes);
            double stdOpen = stdDev(openTimes, avgOpen);
            double avgChained = average(chainedTimes);
            double stdChained = stdDev(chainedTimes, avgChained);

            System.out.printf("α=%d%% | N=%d | %.8f | %.8f | %.8f | %.8f%n",
                    lf, N, avgOpen, avgChained, stdOpen, stdChained);
        }
    }
}
