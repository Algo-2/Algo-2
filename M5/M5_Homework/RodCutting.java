import java.util.Arrays;

public class RodCutting {

    public static int cutRod(int[] p, int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        int maxRevenue = cutRodHelper(p, n, memo);

        return maxRevenue;
    }

    private static int cutRodHelper(int[] p, int n, int[] memo) {
        if (n <= 0) {
            return 0;
        }
        if (memo[n] != -1) {
            return memo[n];
        }
        int maxRevenue = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            int revenue = p[i] + cutRodHelper(p, n - i, memo);
            if (revenue > maxRevenue) {
                maxRevenue = revenue;
            }
        }
        memo[n] = maxRevenue;
        return maxRevenue;
    }

    public static void main(String[] args) {
        int[] p = { 0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 };
        int n = 10;
        for (int i = 0; i <= n; i++) {
            int maxRevenue = cutRod(p, i);
            System.out.println("Maximum revenue: " + maxRevenue);
        }
    }
}
