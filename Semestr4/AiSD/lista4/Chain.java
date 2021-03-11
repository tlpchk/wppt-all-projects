class Chain extends DataStructure {

    @Override
    void insert(String s) {

        insert++;

        s = cleanMyNodeForInsert(s);
        if (s.equals("")){
            return;
        }

        Node newHead = new Node(this,s);
        if(root == Nil){
            newHead.right = Nil;
        }else {
            newHead.right = root;
        }
        this.root = newHead;

        size++;
        if(size > maxSize){
            maxSize = size;
        }
    }

    @Override
    public String inorder() {
        inorder++;
        Node copyHead = this.root;
        while (copyHead != Nil){
            System.out.println(copyHead.key);
            copyHead = copyHead.right;
        }
        return "";
    }

    @Override
    public String successor(String k) {
        successor++;
        return "";
    }

    @Override
    public String max() {
        max++;
        return "";
    }

    @Override
    public String min() {
        min++;
        return "";
    }

    @Override
    public int find(String s) {
        find++;
        Node next = root;
        while(next != Nil){
            comparisons++;
            if (s.equals(next.key)) {
                return 1;
            }
            next = next.right;
        }
        return 0;
    }

    @Override
    int delete(String key) {
        delete++;
        Node p = Nil;
        Node l = this.root;
        while (l != Nil){
            if (l.key.equals(key)){
                size--;
                if(p == Nil)
                    root = root.right;
                else
                    p.right = l.right;
                return 1;
            }
            p = l;
            l = l.right;
        }
        return 0;
    }
}
