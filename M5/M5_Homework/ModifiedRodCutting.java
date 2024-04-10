import java.util.Arrays;

public class ModifiedRodCutting {
    public static int cutRod(int[] p, int n, int c) {
        int[] r = new int[n + 1];
        Arrays.fill(r, -1);
        return cutRodHelper(p, n, c, r);
    }

    private static int cutRodHelper(int[] p, int n, int c, int[] r) {
        if (n <= 0) {
            return 0;
        }
        if (r[n] != -1) {
            return r[n];
        }
        int q = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            int revenue;
            if (i == n) {
                revenue = p[i];
            } else {
                revenue = p[i] - c + cutRodHelper(p, n - i, c, r);
            }
            q = Math.max(q, revenue);
        }
        r[n] = q;
        return q;
    }

    public static void main(String[] args) {
        int[] p = { 0, 1, 5, 8, 10, 13, 17, 18, 22, 25, 30 };
        int n = 10;
        int c = 1;
        for (int i = 0; i < n; i++) {
            int maxRevenue = cutRod(p, i, c);
            System.out.println("Maximum revenue: " + maxRevenue);

        }
        // int maxRevenue = cutRod(p, n, c);
        // System.out.println("Maximum revenue: " + maxRevenue);
    }
}