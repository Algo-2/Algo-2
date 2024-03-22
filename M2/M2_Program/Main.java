import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        TreeNode z;

        // Creates print writer object and prints headings for the columns
        try (PrintWriter output = new PrintWriter("output.txt")) {
            output.println("n" + "," + "height" + "," + "H(n)/2n" + "," + "H(n)/2√n" + "," + "H(n)/2Lg(n)");
            for (int n = 2000; n <= 30000; n += 2000) {
                int sum_height = 0;
                int m = 5;
                double g1 = 2 * n;
                double g2 = 2 * Math.sqrt(n);
                double g3 = 2 * Math.log(n) / Math.log(2);

                for (int j = 1; j <= m; j++) {
                    BinaryTree tree = new BinaryTree();
                    for (int i = 1; i <= n; i++) {
                        int p = rand.nextInt(15001);
                        z = new TreeNode(p);
                        tree.insertNode(z);
                    }
                    int h = BinaryTree.calculateHeight(tree.root);
                    sum_height += h;
                }
                double averageHeight = sum_height / m;
                // Calculate items for plot
                double hg1 = averageHeight / g1;
                double hg2 = averageHeight / g2;
                double hg3 = averageHeight / g3;

                // Writes all 5 variables to .txt file for easy excel importing
                output.println(n + "," + averageHeight + "," + hg1 + "," + hg2 + "," + hg3);

            }
        }
        // Catches error if the file is not found
        catch (FileNotFoundException e) {
            System.out.println("Error while trying to write to file");
        }

        System.out.print("Done");
    }

    // Node

    public static class TreeNode {
        public int key;
        public TreeNode left, right;

        public TreeNode(int item) {
            key = item;
            left = right = null;
        }
    }

    public static class BinaryTree {
        TreeNode root;

        BinaryTree() {
            root = null;
        }

        void insertNode(TreeNode z) {
            if (root == null) {
                root = z;
                return;
            }
            TreeNode current = root;
            TreeNode parent = null;

            while (current != null) {
                parent = current;

                if (z.key < current.key) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            if (z.key < parent.key) {
                parent.left = z;
            } else {
                parent.right = z;
            }

        }

        static int calculateHeight(TreeNode root) {
            if (root == null) {
                return 0;
            } else {
                int leftHeight = calculateHeight(root.left);
                int rightHeight = calculateHeight(root.right);
                return Math.max(leftHeight, rightHeight) + 1;
            }
        }
    }

}
