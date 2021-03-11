import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Model1 {

    public static void main(String[] args) {
        double h = 0.95;
        Graph graph = graph2(h);
        System.out.println(Graph.monteCarlo(graph,10000000));
        graph = graph3(h);
        System.out.println(Graph.monteCarlo(graph,10000000));
        graph = graph4(h);
        System.out.println(Graph.monteCarlo(graph,10000000));

    }

    public static Graph graph1(double h){
        Graph graph = new Graph();
        Node v = new Node(1);
        graph.addNode(v);
        Node u;
        for (int i = 2; i <= 20; i++) {
            u = new Node(i);
            graph.addNode(u);
            graph.addEdge(v,u,h);
            v = u;
        }
        return graph;
    }
    public static Graph graph2(double h) {
        Graph graph = graph1(h);
        graph.addEdge(graph.getNodeById(1), graph.getNodeById(20), h);
        return graph;
    }
    public static Graph graph3(double h){
        Graph graph = graph2(h);
        graph.addEdge(graph.getNodeById(1),graph.getNodeById(10),0.8);
        graph.addEdge(graph.getNodeById(5),graph.getNodeById(15),0.7);
        return graph;
    }
    public static Graph graph4(double h){
        Graph graph = graph3(h);
        Random random = new Random();
        Set<Node> nodes = graph.getNodes();
        Node u;
        Node v;
        for (int i = 0; i < 4; i++) {
            v = graph.getNodeById(random.nextInt(nodes.size())+1);
            u = graph.getNodeById(random.nextInt(nodes.size())+1);
            while (v.equals(u) || v.getNeighbours().containsKey(u)){
                u = graph.getNodeById(random.nextInt(nodes.size())+1);
            }

            graph.addEdge(u,v,0.4);
        }
        return graph;

    }
}