public class HMap extends DataStructure{
    static int hash = 7;
    DataStructure[] hmap;
    int m;
    int n;

    public HMap(int m, int n) {
        this.m = m;
        this.n = n;
        this.hmap = new DataStructure[m];
        for (int i = 0; i < m; i++) {
            hmap[i] = new Chain();
        }
    }

    @Override
    void insert(String s) {
        insert++;
        String s2 = cleanMyNodeForInsert(s);
        if (s2.equals("")){
            return;
        }

        int i = hashString(s2);
        hmap[i].insert(s);

        if(hmap[i].size > n && hmap[i].getClass().equals(Chain.class)){
            hmap[i] = toRBT((Chain) hmap[i]);
        }
        size++;
        if(size > maxSize){
            maxSize = size;
        }
    }

    @Override
    int delete(String key) {
        delete++;
        int i = hashString(key);
        if(hmap[i].delete(key) == 1) {
            size--;
            if(hmap[i].size <= n && hmap[i].getClass().equals(RBTree.class)){
                hmap[i] = toChain((RBTree) hmap[i]);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int find(String s) {
        find++;
        int i = hashString(s);

        int comp = hmap[i].comparisons;
        int result =  hmap[i].find(s);
        comparisons += hmap[i].comparisons - comp;

        return result;
    }

    @Override
    public String min() {
        min++;
        return "";
    }

    @Override
    public String max() {
        max++;
        return "";
    }

    @Override
    public String successor(String k) {
        successor++;
        return "";
    }

    @Override
    public String inorder() {
        inorder++;
        return "";
    }

    Chain toChain(RBTree tree){
    Chain chain = new Chain();
    transferToChain(tree, (RBNode) tree.root,chain);
    return chain;
    }
    void transferToChain(RBTree tree,RBNode node, Chain chain){
        if(node != tree.Nil){
            transferToChain(tree, (RBNode) node.left,chain);
            chain.insert(node.key);
            transferToChain(tree, (RBNode) node.right,chain);
        }
    }

    RBTree toRBT(Chain chain){
        RBTree rbt = new RBTree();
        transferToRBT(chain,chain.root,rbt);
        return rbt;
    }
    void transferToRBT(Chain chain, Node node, RBTree rbt) {
        if(node != chain.Nil){
            transferToRBT(chain,node.left,rbt);
            rbt.insert(node.key);
            transferToRBT(chain,node.right,rbt);
        }
    }

    int hashString(String s){
        int hash = HMap.hash;
        for (int i = 0; i < s.length(); i++) {
            hash = hash*31 + s.charAt(i);
            hash %= m;
        }
        return hash;
    }
}
