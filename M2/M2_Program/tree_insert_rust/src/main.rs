use std::io::Write;
use std::{collections::VecDeque, fs::File};
struct TreeNode {
    key: i32,
    left: Option<Box<TreeNode>>,
    right: Option<Box<TreeNode>>,
}

impl TreeNode {
    fn new(key: i32) -> Self {
        TreeNode {
            key,
            left: None,
            right: None,
        }
    }
}

struct DepthLevel<'a> {
    node: &'a TreeNode,
    level: i32,
}
struct BinaryTree {
    root: Option<Box<TreeNode>>,
}

impl BinaryTree {
    fn new() -> Self {
        BinaryTree { root: None }
    }

    fn insert(&mut self, z: TreeNode) {
        let new_node = Box::new(TreeNode {
            key: z.key,
            left: None,
            right: None,
        });
        if self.root.is_none() {
            self.root = Some(new_node);
            return;
        }

        let mut current = &mut self.root;

        while let Some(ref mut unwrapped) = current {
            if z.key < unwrapped.key {
                current = &mut unwrapped.left;
            } else {
                current = &mut unwrapped.right;
            }
        }
        *current = Some(new_node);
    }

    fn calculate_height(root: &Option<Box<TreeNode>>) -> i32 {
        let mut q: VecDeque<DepthLevel> = VecDeque::new();
        let mut _current_level = 0;
        if let Some(node) = root {
            q.push_back(DepthLevel { node, level: 1 });
            let mut max_level = 0;
            while let Some(depth_level) = q.pop_front() {
                let current_node = depth_level.node;
                let current_level = depth_level.level;

                max_level = max_level.max(current_level);
                if let Some(left) = current_node.left.as_ref() {
                    q.push_back(DepthLevel {
                        node: left,
                        level: current_level + 1,
                    });
                    if let Some(right) = current_node.right.as_ref() {
                        q.push_back(DepthLevel {
                            node: right,
                            level: current_level + 1,
                        });
                    }
                }
            }
            max_level
        } else {
            0
        }
    }
}

fn main() {
    let path = "output.txt";
    let mut file = File::create(path).expect("Should create file");
    writeln!(file, "n, height, H(n)/2n, H(n)/2√n, H(n)/2Lg(n)").expect("Should write to file");
    for n in (2000..=30000).step_by(2000) {
        let mut sum_height: i32 = 0;
        let m = 5;

        let g1 = 2.0 * n as f64;
        let g2 = 2.0 * (n as f64).sqrt();
        let g3 = 2.0 * (n as f64).log2();

        for _ in 0..m {
            let mut tree = BinaryTree::new();
            for _ in 0..n {
                let p = rand::random();
                let z = TreeNode::new(p);
                tree.insert(z);
            }
            let h = BinaryTree::calculate_height(&tree.root);
            sum_height += h;
        }
        let average_height = sum_height / m;

        let tg1 = average_height as f64 / g1;
        let tg2 = average_height as f64 / g2;
        let tg3 = average_height as f64 / g3;
        writeln!(file, "{}, {}, {}, {}, {}", n, average_height, tg1, tg2, tg3)
            .expect("Should write to file");
    }
}
