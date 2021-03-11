class BSNode extends Node {
    BSNode(BSTree tree, String key)
    {
        super(tree,key);
        this.key = key;
        left = null;
        right = null;
        parent = null;
        this.tree = tree;
    }
}

