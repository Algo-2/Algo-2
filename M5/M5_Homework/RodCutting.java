import java.util.Arrays;

public class RodCutting {

    public static int cutRod(int[] prices, int length) {
        int[] memo = new int[length + 1];
        Arrays.fill(memo, -1);
        int maxRevenue = cutRodHelper(prices, length, memo);

        return maxRevenue;
    }

    private static int cutRodHelper(int[] prices, int length, int[] memo) {
        if (length <= 0) {
            return 0;
        }
        if (memo[length] != -1) {
            return memo[length];
        }
        int maxRevenue = Integer.MIN_VALUE;
        for (int i = 1; i <= length; i++) {
            int revenue = prices[i] + cutRodHelper(prices, length - i, memo);
            if (revenue > maxRevenue) {
                maxRevenue = revenue;
            }
        }
        memo[length] = maxRevenue;
        return maxRevenue;
    }

    public static void main(String[] args) {
        int[] prices = { 0, 1, 5, 8, 10, 13, 17, 18, 22, 25, 30 };
        int length = 9;
        int maxRevenue = cutRod(prices, length);
        System.out.println("Maximum revenue: " + maxRevenue);
    }
}
