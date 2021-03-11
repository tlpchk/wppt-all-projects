import java.util.*;

public class Graph {
    protected Set<Node> nodes;

    public Graph() {
        nodes = new HashSet<>();
    }


    public void addNode(Node v){
        nodes.add(v);
    }
    public void addEdge(Node u, Node v, Double h) {
        if (!(this.nodes.contains(u) && this.nodes.contains(v))) {
            System.out.println("Nodes don't exist");
            return;
        }
        u.addNeighbour(v, h);
        v.addNeighbour(u, h);
    }

    public Node getNodeById(int id) {
        for (Node node : nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }
    public Set<Node> getNodes() {
        return nodes;
    }
    public static boolean isConsistent(Graph graph) {
        calculateShortestPathFromSource(graph,graph.getNodeById(1));
        for (Node node : graph.nodes) {
            if( node.getDistance() == 0){
                return false;
            }
        }
        return true;
    }

    public static void calculateShortestPathFromSource(Graph graph, Node source) {
        for (Node node : graph.getNodes()) {
            node.setDistance(0);
            node.setShortestPath(new LinkedList<>());
        }
        source.setDistance(1);

        List<Node> settledNodes = new LinkedList<>();
        List<Node> unsettledNodes = new LinkedList<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Double> neighbour :
                    currentNode.getNeighbours().entrySet()) {
                Node adjacentNode = neighbour.getKey();
                double edgeWeight = neighbour.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }
    private static Node getLowestDistanceNode(List<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        double lowestDistance = 0;
        for (Node node : unsettledNodes) {
            double nodeDistance = node.getDistance();
            if (nodeDistance > lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
    private static void calculateMinimumDistance(Node evaluationNode, double edgeWeigh, Node sourceNode) {
        double sourceDistance = sourceNode.getDistance();
        if (sourceDistance * edgeWeigh > evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance * edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public static boolean testConnection(Graph graph, Node source, Node destination) {
        Random random = new Random();
        Graph.calculateShortestPathFromSource(graph, source);
        List<Node> path = destination.getShortestPath();
        Node previous = source;
        Node next;
        int r;

        if (path.size() == 0) {
            return false;
        }

        for (int i = 1; i < path.size(); i++) {
            next = path.get(i);
            //System.out.println("Try to go from " + previous.getId() + " to " + next.getId() + ", h = " + previous.getH(next));
            r = random.nextInt(100);
            //System.out.println("r = " + r);
            if (r >= 100 * previous.getH(next)) {
                //System.out.println("I'm sorry");
                return false;
            }
            previous = next;
        }
        //System.out.println("Try to go from " + previous.getId() + " to " + destination.getId() + ", h = " + previous.getH(destination));
        r = random.nextInt(100);
        //System.out.println("r = " + r);
        if (r >= 100 * previous.getH(destination)) {
            return false;
        }
        //System.out.println("Success!");
        return true;
    }
    public static double monteCarlo(Graph graph, int n) {
        Random random = new Random();
        Set<Node> nodes = graph.getNodes();
        Node u;
        Node v;
        double p = 0;
        for (int i = 0; i < n; i++) {
            u = graph.getNodeById(random.nextInt(nodes.size()) + 1);
            v = graph.getNodeById(random.nextInt(nodes.size()) + 1);
            while (u.equals(v)) {
                v = graph.getNodeById(random.nextInt(nodes.size()) + 1);
            }

            if (testConnection(graph, u, v)) {
                p++;
            }
        }
        return p / n;
    }

}