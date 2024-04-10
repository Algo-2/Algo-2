import java.util.Arrays;

public class RodCuttingCutTracking {

    public static int cutRod(int[] p, int n) {
        int[] memo = new int[n + 1];
        int[] cuts = new int[n + 1]; // This array will track the cuts
        Arrays.fill(memo, -1);
        int maxRevenue = cutRodHelper(p, n, memo, cuts);

        if (maxRevenue > p[n]) { // This condition checks if making cuts is better than not making any cuts
            System.out.print("Cuts made to produce lengths: ");
            while (n > 0) {
                System.out.print(cuts[n] + " ");
                n -= cuts[n]; // Move to the next piece
            }
        } else {
            System.out.print("No cuts made ");
        }
        return maxRevenue;
    }

    private static int cutRodHelper(int[] p, int n, int[] memo, int[] cuts) {
        if (n <= 0) {
            return 0;
        }
        if (memo[n] != -1) {
            return memo[n];
        }
        int maxRevenue = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            int revenue = p[i] + cutRodHelper(p, n - i, memo, cuts);
            if (revenue > maxRevenue) {
                maxRevenue = revenue;
                cuts[n] = i; // Record this cut as it leads to max revenue
            }
        }
        memo[n] = maxRevenue;
        return maxRevenue;
    }

    public static void main(String[] args) {
        int[] p = { 0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 };
        int n = 9;
        int maxRevenue = 0;
        for (int i = 0; i < n; i++) {
            maxRevenue = cutRod(p, i);
            System.out.println("and Maximum revenue: " + maxRevenue);

        }
        // int maxRevenue = cutRod(p, n);
        // System.out.println("Maximum revenue: " + maxRevenue);
    }
}
