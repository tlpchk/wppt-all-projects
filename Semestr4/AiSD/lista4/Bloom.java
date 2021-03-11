public class Bloom extends DataStructure{

    private int k;
    private int m;
    boolean[] filter;

    public Bloom(int m,int k) {
        this.m = m;
        this.k = k;
        filter = new boolean[m];
        for (int i = 0; i < m; i++) {
            filter[i] = false;
        }
    }

    int hashString(String s, int individual_hash){
        int hash = HMap.hash;
        for (int i = 0; i < s.length(); i++) {
            hash = hash*individual_hash*i + s.charAt(i);
            hash %= m;
        }
        return hash;
    }

    @Override
    void insert(String s) {
        insert++;
        s = cleanMyNodeForInsert(s);
        if (s.equals("")){
            return;
        }

        int index;
        for (int i = 1; i <= this.k; i++) {
            index = hashString(s,i);
            filter[index] = true;
        }

        size++;
        int b = 0;
        for (boolean bit: filter) { if(bit) b++; }
        maxSize = b;

    }

    @Override
    int delete(String key) {
        return 0;
    }

    @Override
    void load(String fileName) {
        super.load(fileName);
    }

    @Override
    public int find(String s) {
        int index;
        for (int i = 1; i <= this.k; i++) {
            index = hashString(s,i);
            if(filter[index] == false){
                return 0;
            }
        }
        return 1;
    }

    @Override
    public String min() {
        return "";
    }

    @Override
    public String max() {
        return "";
    }

    @Override
    public String successor(String k) {
        return "";
    }

    @Override
    public String inorder() {
        return "";
    }

}
