import java.util.Arrays;

public class RodCuttingCutTracking {

    public static int cutRod(int[] prices, int length) {
        int[] memo = new int[length + 1];
        int[] cuts = new int[length + 1]; // This array will track the cuts
        Arrays.fill(memo, -1);
        int maxRevenue = cutRodHelper(prices, length, memo, cuts);

        // Backtracking to find the cuts
        int n = length;
        System.out.print("Cuts made at lengths: ");
        while (n > 0) {
            System.out.print(cuts[n] + " ");
            n -= cuts[n]; // Move to the next piece
        }
        System.out.println(); // New line for clean output

        return maxRevenue;
    }

    private static int cutRodHelper(int[] prices, int length, int[] memo, int[] cuts) {
        if (length <= 0) {
            return 0;
        }
        if (memo[length] != -1) {
            return memo[length];
        }
        int maxRevenue = Integer.MIN_VALUE;
        for (int i = 1; i <= length; i++) {
            int revenue = prices[i] + cutRodHelper(prices, length - i, memo, cuts);
            if (revenue > maxRevenue) {
                maxRevenue = revenue;
                cuts[length] = i; // Record this cut as it leads to max revenue
            }
        }
        memo[length] = maxRevenue;
        return maxRevenue;
    }

    public static void main(String[] args) {
        int[] prices = { 0, 1, 5, 8, 10, 13, 17, 18, 22, 25, 30 };
        int length = 9;
        int maxRevenue = 0;
        for (int i = 0; i < length; i++) {
            maxRevenue = cutRod(prices, i);
            System.out.println("Maximum revenue: " + maxRevenue);

        }
        // int maxRevenue = cutRod(prices, length);
        // System.out.println("Maximum revenue: " + maxRevenue);
    }
}
