import java.util.Arrays;

public class ModifiedRodCutting {
    public static int cutRod(int[] p, int n, int c) {
        int[] r = new int[n + 1];
        Arrays.fill(r, -1);
        return cutRodHelper(p, n, c, r);
    }

    private static int cutRodHelper(int[] p, int n, int c, int[] r) {
        int q;
        if (n == 0) {
            return 0;
        } else if (r[n] > 0) {
            return r[n];
        } else {
            q = Integer.MIN_VALUE;
            for (int i = 1; i <= n; i++) {
                int revenue;
                if (i < n) {
                    revenue = p[i] + cutRodHelper(p, n - i, c, r) - c;
                } else {
                    revenue = p[i] + cutRodHelper(p, n - i, c, r);
                }
                q = Math.max(q, revenue);
            }
            r[n] = q;
            return q;
        }
    }

    public static void main(String[] args) {
        int[] p = { 0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 };
        int n = 10;
        int c = 1;
        for (int i = 0; i <= n; i++) {
            int maxRevenue = cutRod(p, i, c);
            System.out.println("Maximum revenue: " + maxRevenue);

        }
        // int maxRevenue = cutRod(p, n, c);
        // System.out.println("Maximum revenue: " + maxRevenue);
    }
}