import java.util.ArrayList;

public class MSTKruksal implements MST {
    ArrayList<int[]> E;
    int n;

    public MSTKruksal(int n) {
        this.n = n;
        this.E = new ArrayList<>();
    }

    public ArrayList<int[]> mst(){
        ArrayList<int[]> X = new ArrayList<>();
        DisjointSets A = new DisjointSets(n);
        PriorityQueue<int[]> Q = new PriorityQueue();

        int[] e;
        copyEdgesToPriorityQueue(Q,E);

        for (int i = 1; i <= n ; i++) {
            A.makeSet(i);
        }

        while (!Q.empty()){
            e = Q.pop();
            int u = e[0];
            int v = e[1];
            int w = e[2];
            if(A.find(u) != A.find(v)){
                A.union(u,v);
                X.add(new int[]{u,v,w});
            }
        }

        return X;
    }

    public void addEdge(int u, int v, int w) {
        int[] edge = new int[]{u,v,w};
        E.add(edge);
    }

    public void addEdges(int[][] edges){
        for (int i = 1; i <=n ; i++) {
            for (int j = 1; j <=n ; j++) {
                if(edges[i][j] != 0){
                    addEdge(i,j,edges[i][j]);
                }
            }
        }
    }

    private static void copyEdgesToPriorityQueue(PriorityQueue Q, ArrayList<int[]> E){
        for (int i = 0; i < E.size() ; i++) {
            Q.insert(E.get(i),E.get(i)[2]);
        }
    }
}
