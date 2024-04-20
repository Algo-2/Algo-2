import java.util.ArrayList;

public class RecursiveActivitySelector {

    public static void recursiveActivitySelector(ArrayList<Integer> A, int[] s, int[] f, int k, int n) {
        int m = k + 1;
        while (m <= n && s[m] < f[k]) {
            m++;
        }

        if (m <= n) {
            A.add(m);
            recursiveActivitySelector(A, s, f, m, n);
        }

    }

    public static void main(String[] args) {
        int[] s = { 0, 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12 };
        int[] f = { 0, 4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16 };
        ArrayList<Integer> A = new ArrayList<>();
        recursiveActivitySelector(A, s, f, 0, s.length - 1);
        System.out.println("Selected Activities: " + A);
    }

}