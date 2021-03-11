import java.util.ArrayList;

public interface MST {
    ArrayList<int[]> mst();
    void addEdge(int u,int v, int w);
    void addEdges(int[][] edges);
}
