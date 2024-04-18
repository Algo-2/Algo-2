import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ActivitySelector {
    private static int[] s;
    private static int[] f;

    // Recursive-Activity-Selector
    public static void recursiveActivitySelector(ArrayList<Integer> A, int k, int n) {
        int m = k + 1;
        while (m <= n && s[m] < f[k]) {
            m++;
        }

        if (m <= n) {
            A.add(m);
            recursiveActivitySelector(A, m, n);
        }

    }

    // Iterative-Activity-Selector
    public static ArrayList<Integer> greedyActivitySelector(int n) {
        ArrayList<Integer> A = new ArrayList<>();
        A.add(0);
        int k = 0;
        for (int m = 1; m <= n; m++) {
            if (s[m] >= f[k]) {
                A.add(m);
                k = m;
            }
        }
        return A;
    }

    public static void StudyOverhead(int numberPoints) throws IOException {
        FileWriter fileWriter = new FileWriter("output.csv");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        double[] M = new double[numberPoints + 1];
        bufferedWriter.write("i" + "," + "M[i]" + "\n");
        for (int i = 1; i <= numberPoints; i++) {
            double timeRecursive = 0;
            double timeIterative = 0;
            int numberRuns = 500;

            for (int j = 1; j < numberRuns; j++) {
                ArrayList<Integer> A = new ArrayList<>();

                double rstartTime = System.nanoTime();
                recursiveActivitySelector(A, 0, i - 1);
                double rendTime = System.nanoTime();
                timeRecursive += rendTime - rstartTime;

                double gstartTime = System.nanoTime();
                greedyActivitySelector(i - 1);
                double gendTime = System.nanoTime();
                timeIterative += gendTime - gstartTime;
            }
            M[i] = timeIterative / timeRecursive;
            bufferedWriter.write(i + "," + M[i] + "\n");
        }
        bufferedWriter.close();
        fileWriter.close();
    }

    public static void initializeArrays(int n) {
        s = new int[n];
        f = new int[n];
        s[0] = 0;
        f[0] = 0;
        for (int i = 1; i < n; i++) {
            if (i % 2 == 0) {
                s[i] = f[i - 2];
                f[i] = s[i] + 2;
            } else {
                s[i] = f[i - 1] - 1;
                f[i] = f[i - 1] + 1;
            }
        }
    }

    public static void main(String[] args) {
        int numberPoints = 15000;

        initializeArrays(numberPoints);

        try {
            StudyOverhead(numberPoints);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        System.out.println("Done!");
    }
}