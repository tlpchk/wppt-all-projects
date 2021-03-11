
public class DisjointSets {
    private int[] rank,p;
    int n;

    public DisjointSets(int n) {
        rank = new int[n+1];
        p = new int[n+1];
        this.n = n;
    }

    void makeSet(int x)
    {
        p[x] = x;
    }

    int find(int x) {
        if (p[x] != x){
            p[x] = find(p[x]);
        }
        return p[x];
    }

    void union(int x, int y) {
        int rx = find(x);
        int ry = find(y);
        if ( rx  == ry )
            return ;

        if ( rank[rx] > rank[ry] )
            p[ry] = rx;
        else {
            p[rx] = ry;
            if ( rank[rx] == rank[ry] )
                rank[ry]++;
        }
    }
}
