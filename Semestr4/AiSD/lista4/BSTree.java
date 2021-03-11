public class BSTree extends DataStructure {
    public void insert(String s){
        insert++;

        s = cleanMyNodeForInsert(s);
        if (s.equals("")){
            return;
        }

        BSNode z = new BSNode(this,s);

        BSNode y = (BSNode) Nil;
        BSNode x = (BSNode) root;

        while (x != Nil){
            y = x;
            if(z.key.compareTo(x.key) < 0){
                x = (BSNode) x.left;
            }
            else {
                x = (BSNode) x.right;
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

        size++;
        if(size > maxSize){
            maxSize = size;
        }
    }

    public int delete(String key){
        delete++;
        BSNode x = (BSNode) subfind(key,root);

        if (x == Nil)
            return 0;

        BSNode z;

        if(x.left != Nil && x.right != Nil){
            z = (BSNode) submin(x.right);
            x.key = z.key;
            //x = z;

            if(z.key.compareTo(z.parent.key) < 0)
                z.parent.left = Nil;
            else
                z.parent.right = Nil;
        }
        else if (x.left == Nil && x.right == Nil){
            if (x == root)
                root = Nil;
            else if(x.key.compareTo(x.parent.key) < 0)
                x.parent.left = Nil;
            else
                x.parent.right = Nil;
        }else {
            if (x.left != Nil)
                z = (BSNode) x.left;
            else
                z = (BSNode) x.right;

            if (x == root)
                root = z;
            else if (x.key.compareTo(x.parent.key) < 0)
                x.parent.left = z;
            else
                x.parent.right = z;
        }

        size--;
        return 1;
    }

    BSTree(){
        super();
        //this.Nil = null;
        //this.root = null;
    }
}

