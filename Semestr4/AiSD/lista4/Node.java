class Node {
    DataStructure tree;

    String key;
    Node left;
    Node right;
    Node parent;

    Node(DataStructure tree, String key)
    {
        this.key = key;
        left = null;
        right = null;
        parent = null;
        this.tree = tree;
    }
}
