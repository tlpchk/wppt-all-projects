public class RBTree extends DataStructure {

    void insert(String s) {
        insert++;

        s = cleanMyNodeForInsert(s);
        if (s.equals("")){
            return;
        }

        RBNode z = new RBNode(this,s,COLOR.RED);

        RBNode y = (RBNode) Nil;
        RBNode x = (RBNode) this.root;

        while (x != Nil){
            y = x;
            if(z.key.compareTo(x.key) < 0){
                x = (RBNode) x.left;
            }
            else {
                x = (RBNode) x.right;
            }
        }
        z.parent = y;

        if (y == Nil){
            this.root = z; // drzewo było puste
        }
        else if (z.key.compareTo(y.key) < 0) {
            y.left = z;
        }
        else{
            y.right = z;
        }

        z.left = Nil;
        z.right = Nil;
        z.color = COLOR.RED;

        insertFix(z);
        size++;
        if(size > maxSize){
            maxSize = size;
        }
    }

    int delete(String key) {
        delete++;
        RBNode z = (RBNode) subfind(key,root);
        if(z != Nil) {
            subdelete(z);
            size--;
            return 1;
        }
        return 0;
    }

    protected void subdelete(RBNode z){
        RBNode y = z;
        RBNode x ;
        COLOR y_original_color = y.color;
        if (z.left == Nil){
            x = (RBNode) z.right;
            rb_transplant(z, (RBNode) z.right);
        }else if(z.right == Nil){
            x = (RBNode) z.left;
            rb_transplant(z, (RBNode) z.left);
        }
        else {
            y = (RBNode) submin(z.right);
            y_original_color = y.color;
            x = (RBNode) y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rb_transplant(y, (RBNode) y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            rb_transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if(y_original_color == COLOR.BLACK){
            deleteFix(x);
        }
    }

    private void deleteFix(RBNode x) {
            while(x!=root && x.color == COLOR.BLACK){
                if(x == x.parent.left){
                    RBNode w = (RBNode) x.parent.right;
                    if(w.color == COLOR.RED){
                        w.color = COLOR.BLACK;
                        ((RBNode)x.parent).color = COLOR.RED;
                        leftRotate((RBNode) x.parent);
                        w = (RBNode) x.parent.right;
                    }
                    if(((RBNode)w.left).color == COLOR.BLACK && ((RBNode)w.right).color == COLOR.BLACK){
                        w.color = COLOR.RED;
                        x = (RBNode) x.parent;
                        continue;
                    }
                    else if(((RBNode)w.right).color == COLOR.BLACK){
                        ((RBNode)w.left).color = COLOR.BLACK;
                        w.color = COLOR.RED;
                        rightRotate(w);
                        w = (RBNode) x.parent.right;
                    }
                    if(((RBNode)w.right).color == COLOR.RED){
                        w.color = ((RBNode)x.parent).color;
                        ((RBNode)x.parent).color = COLOR.BLACK;
                        ((RBNode)w.right).color = COLOR.BLACK;
                        leftRotate((RBNode) x.parent);
                        x = (RBNode) root;
                    }
                }else{
                    Node w = x.parent.left;
                    if(((RBNode)w).color == COLOR.RED){
                        ((RBNode)w).color = COLOR.BLACK;
                        ((RBNode)x.parent).color = COLOR.RED;
                        rightRotate((RBNode) x.parent);
                        w = x.parent.left;
                    }
                    if(((RBNode)w.right).color == COLOR.BLACK && ((RBNode)w.left).color == COLOR.BLACK){
                        ((RBNode)w).color = COLOR.RED;
                        x = (RBNode) x.parent;
                        continue;
                    }
                    else if(((RBNode)w.left).color == COLOR.BLACK){
                        ((RBNode)w.right).color = COLOR.BLACK;
                        ((RBNode)w).color = COLOR.RED;
                        leftRotate((RBNode) w);
                        w = x.parent.left;
                    }
                    if(((RBNode)w.left).color == COLOR.RED){
                        ((RBNode)w).color = ((RBNode)x.parent).color;
                        ((RBNode)x.parent).color = COLOR.BLACK;
                        ((RBNode)w.left).color = COLOR.BLACK;
                        rightRotate((RBNode) x.parent);
                        x = (RBNode) root;
                    }
                }
            }
            x.color = COLOR.BLACK;

    }

    private void insertFix(RBNode x) {
        RBNode y;
        while (((RBNode) x.parent).color == COLOR.RED) {
            if (x.parent == x.parent.parent.left) {
                y = (RBNode) x.parent.parent.right;
                if (y.color == COLOR.RED) {
                    ((RBNode) x.parent).color = COLOR.BLACK;
                    y.color = COLOR.BLACK;
                    ((RBNode) x.parent.parent).color = COLOR.RED;
                    x = (RBNode) x.parent.parent;
                } else {
                    if (x == x.parent.right) {
                        x = (RBNode) x.parent;
                        leftRotate(x);
                    }
                    ((RBNode) x.parent).color = COLOR.BLACK;
                    ((RBNode) x.parent.parent).color = COLOR.RED;
                    rightRotate((RBNode) x.parent.parent);
                    }
            } else {
                y = (RBNode) x.parent.parent.left;
                if (y.color == COLOR.RED) {
                    ((RBNode) x.parent).color = COLOR.BLACK;
                    y.color = COLOR.BLACK;
                    ((RBNode) x.parent.parent).color = COLOR.RED;
                    x = (RBNode) x.parent.parent;
                } else {
                    if (x == x.parent.left) {
                        x = (RBNode) x.parent;
                        this.subinorder(root);
                        rightRotate(x);
                        this.subinorder(root);
                    }
                    ((RBNode) x.parent).color = COLOR.BLACK;
                    ((RBNode) x.parent.parent).color = COLOR.RED;
                    leftRotate((RBNode) x.parent.parent);
                }
            }
        }
        ((RBNode) root).color = COLOR.BLACK;
    }

    private void leftRotate(RBNode x){

        RBNode y = (RBNode) x.right;
        x.right = y.left;

        if (y.left != Nil)
            y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == Nil)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;

        x.parent = y;
    }

    private void rightRotate(RBNode x){

        if (x.parent != Nil) {
            if (x == x.parent.left) {
                x.parent.left = x.left;
            } else {
                x.parent.right = x.left;
            }

            x.left.parent = x.parent;
            x.parent = x.left;
            if (x.left.right != Nil) {
                x.left.right.parent = x;
            }
            x.left = x.left.right;
            x.parent.right = x;
        } else {
            Node left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = Nil;
            root = left;
        }
    }

    private void rb_transplant(RBNode u, RBNode v){
        if (u.parent == Nil){
            root = v;
        }
        else if (u.parent.left == u){
            u.parent.left = v;
        }
        else
            u.parent.right = v;
        v.parent = u.parent;
    }

    RBTree(){
        super();
        Nil = new RBNode(this,"",COLOR.BLACK);
        Nil.parent = Nil;
        Nil.left = Nil;
        Nil.right = Nil;
        this.root = Nil;
    }
}
