import java.util.*;

public class MSTPrim implements MST {
    Graph G;
    int n;

    public MSTPrim(int n) {
        this.n = n;
        this.G = new Graph();
        G.constructNodes(n);
        infinityDistanceForEach();
    }

    @Override
    public ArrayList<int[]> mst() {
        Graph T = new Graph();
            int w,u,v;

            PriorityQueue<Integer> Q = new PriorityQueue();
            G.nodes.get(1).setMinWeight(0);
            copyNodesToPriorityQueue(Q,G);


            while (!Q.empty()){
                v = Q.pop();
                //System.out.println("pop: " + v);
                T.transferNode(v,G);

                Set<Map.Entry<Integer, Integer>> neighbourhood =
                        G.nodes.get(v).neighbourhood.entrySet();
                for (Map.Entry<Integer, Integer> neighbor: neighbourhood) {
                    u = neighbor.getKey();
                    w = neighbor.getValue();

                    //System.out.println(u + " old:" + G.nodes.get(u).minWeight + ", proposed: "+w);

                    if(G.nodes.get(u).minWeight > w ){
                        //if node was poped, don't change minWeight
                        if(Q.priority(u,w) != 0) {
                            G.nodes.get(u).setMinWeight(w);
                            G.nodes.get(u).setMinNeighbor(v);
                        }
                    }
                }
            }
            return mstEdges();
        }

    @Override
    public void addEdge(int u, int v, int w) {
        G.addEdge(u,v,w);
    }

    @Override
    public void addEdges(int[][] edges){
        for (int i = 1; i <=n ; i++) {
            for (int j = 1; j <=n ; j++) {
                if(edges[i][j] != 0){
                    addEdge(i,j,edges[i][j]);
                }
            }
        }
    }
    private void copyNodesToPriorityQueue(PriorityQueue Q, Graph G){
        for (int i = 1; i <= G.nodes.size(); i++) {
            Q.insert(i,G.nodes.get(i).minWeight);
        }
    }

    private void infinityDistanceForEach(){
        for (int i = 1; i <= n; i++) {
            G.nodes.get(i).minWeight = Integer.MAX_VALUE;
        }
    }

    private ArrayList<int[]> mstEdges(){
        ArrayList<int[]> E = new ArrayList<>();
        Set<Map.Entry<Integer, Graph.Node>> nodes =
                G.getNodes().entrySet();
        for (Map.Entry<Integer, Graph.Node> node : nodes) {
            if(node.getValue().minNeighbor > 0) {
                E.add(new int[]{node.getKey(), node.getValue().minNeighbor, node.getValue().minWeight});
            }
        }
        return E;
    }

    private class Graph {
        HashMap<Integer,Node> nodes;

        public Graph() {
            nodes = new HashMap<>();
        }

        public void addNode(int id, Node v) {
            nodes.put(id,v);
        }

        public void addEdge(int u, int v, int w) {
            if (this.nodes.get(u) == null && this.nodes.get(v) == null) {
                System.out.println("Nodes don't exist");
                return;
            }
            nodes.get(u).addNeighbor(v, w);
            nodes.get(v).addNeighbor(u, w);
        }

        public int weightOfEdge(int u,int v){
            if(nodes.get(u).getNeighbourhood().get(v) != null){
                return nodes.get(u).getNeighbourhood().get(v);
            }
            return Integer.MAX_VALUE;
        }

        public void transferNode(int id, Graph g){
            Node node = new Node();
            node.minNeighbor = g.nodes.get(id).minNeighbor;
            node.minWeight = g.nodes.get(id).minWeight;

            nodes.put(id,node);

            if(node.minNeighbor > 0){
                addEdge(id,node.minNeighbor,g.weightOfEdge(node.minNeighbor,id));
            }
        }

        public HashMap<Integer, Node> getNodes() {
            return nodes;
        }

        public void constructNodes(int count){
            for (int i = 1; i <= count; i++) {
                addNode(i,new Node());
            }
        }

        public class Node {
            Map<Integer, Integer> neighbourhood;
            int minWeight;
            int minNeighbor;

            public Node() {
                this.neighbourhood = new HashMap<>();
                this.minWeight = Integer.MAX_VALUE;
                this.minNeighbor = -1;
            }

            public void addNeighbor(int id, int w) {
                this.neighbourhood.put(id, w);
            }

            public Map<Integer, Integer> getNeighbourhood() {
                return neighbourhood;
            }

            public void setMinNeighbor(int minNeighbor) {
                this.minNeighbor = minNeighbor;
            }

            public void setMinWeight(int minWeight) {
                this.minWeight = minWeight;
            }
        }
    }
}
