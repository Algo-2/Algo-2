import java.util.ArrayList;

public class GreedyActivitySelector {
    public static void greedyActivitySelector(ArrayList<Integer> A, int[] s, int[] f, int n) {
        A.add(0);
        int k = 0;
        for (int m = 1; m <= n; m++) {
            if (s[m] >= f[k]) {
                A.add(m);
                k = m;
            }
        }

    }

    public static void main(String[] args) {
        int[] s = { 0, 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12 };
        int[] f = { 0, 4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16 };
        ArrayList<Integer> A = new ArrayList<>();
        greedyActivitySelector(A, s, f, s.length - 1);
        System.out.println("Selected Activities: " + A);
    }
}