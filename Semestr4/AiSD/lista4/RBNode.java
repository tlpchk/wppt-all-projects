class RBNode extends Node {
    COLOR color;

    RBNode(RBTree tree, String key, COLOR color)
    {
        super(tree,key);
        this.color = color;
    }

}

