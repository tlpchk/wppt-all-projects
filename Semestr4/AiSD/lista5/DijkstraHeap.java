import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DijkstraHeap {
    private int n;
    public ArrayList<Node> nodes;
    private PriorityQueue<Integer> heap;

    DijkstraHeap(int n){
        this.n = n;

        this.heap = new PriorityQueue();
        this.nodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nodes.add(i,new Node(i+1,Integer.MAX_VALUE));
        }
    }

    void dijkstra(int s) throws Exception {
        int v,u,d;

        infinityDistanceForEach();
        changeDistance(s,0);
        insert(s);

        while (!heap.empty()){
            u = heap.pop();
            Set<Map.Entry<Node, Integer>> entrySet =
                    nodes.get(u-1).neighbours.entrySet();
            for (Map.Entry<Node,Integer> entry: entrySet) {
                v = entry.getKey().i;
                d = entry.getValue();
                if( getDistance(u) + d < getDistance(v)){
                    changeDistance(v,getDistance(u)+d);
                    changePrev(v,u);
                    if(heap.priority(v,getDistance(u)+d) == 0) {
                        insert(v);
                    }
                }
            }
        }

    }

    public void addEdge(int i, int j, int w){
        nodes.get(i-1).neighbours.put(nodes.get(j-1),w);
    }

    public void print(){
        for (int i = 0; i < nodes.size(); i++) {
            System.out.println(nodes.get(i).i + " " + nodes.get(i).d);
        }
    }

    public void printPaths(){
        ArrayList<int[]> path;
        for (int i = 0; i < nodes.size(); i++) {
            path = path(i+1);
            for (int j = path.size()-1; j >=0 ; j--) {
                System.err.print("" + path.get(j)[0] + "---(" + path.get(j)[1] + ")--->");
            }
            System.err.print(i+1 + "\n");
        }
    }

    public ArrayList<int[]> path(int index){
        Node v = nodes.get(index-1);
        Node u;
        int[] t;
        ArrayList<int[]> path = new ArrayList<>();
        u = v.prev;
        if(u == null){
            t = new int[2];
            t[0] = v.i;
            t[1] = 0;
            path.add(0,t);
            return path;
        }
        while (u != null){
            t = new int[2];
            t[0] = u.i;
            t[1] = u.neighbours.get(v);
            path.add(t);
            v = u;
            u = u.prev;
        }
        return path;
    }

    private void changePrev(int v, int u){
        nodes.get(v-1).prev = nodes.get(u-1);
    }

    private void changeDistance(int index, int d){
        nodes.get(index-1).d = d;
    }

    private int getDistance(int index){
        return nodes.get(index-1).d;
    }

    private void infinityDistanceForEach(){
        for (int i = 0; i < n; i++) {
            nodes.get(i).d = Integer.MAX_VALUE;
        }
    }

    private void insert(int s){
        heap.insert(s,nodes.get(s-1).d);
    }

    private class Node{
        int i;
        int d;
        Node prev;
        HashMap<Node,Integer> neighbours;

        Node(int i, int d) {
            this.i = i;
            this.d = d;
            this.neighbours = new HashMap<>();
            prev = null;
        }
    }

}
