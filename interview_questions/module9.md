## Module 9 interview questions.


### Java autoboxing
**Consider  two double values a, b and their corresponding Double values a and y. Find values such that a==b is true, but x.equals(y) is false, and a==b is false but x.equals(y) is true.**  
For the first one, take a=0.0, b=-0.0.  
For the second one, set a=b=Double.NaN.

### Check if a binary tree is a BST.
**Given a binary tree where each Node contains a key, determine whether it is a binary search tree. Use extra space proportional to the height of the tree.**
We can recursively iterate through the tree:

    bool check_binary(Node x, int min, int max) {
        if (x.key < min || x.key > max) return false;
        return check_binary(x.left, min, x.key) && check_binary(x.right, x.key, max);
    }

This recursion is at most as deep as the tree, and each level uses a fixed amount of memory, thus it satisfies the memory requirements.

### Inorder traversal with constant extra space.
**Design an algorithm to perform an inorder traversal of a binary search tree using only a constant amount of extra space.**
We are going to define an algorithm, that rewires the BST into a single branch, where nodes only have right neighbors.

    void rewire(Node root) {
        if (root == null) return;

        while(root != null) {

            if (root.left == null) { root = root.right; continue; }

            Node temp = root.left;

            while (temp.right != null) temp = temp.right;

            temp.right = root;
            temp = root.left;
            root.left = null;
            root = temp;
        }
    }

After this, one can just iterate through the rewired BST.
