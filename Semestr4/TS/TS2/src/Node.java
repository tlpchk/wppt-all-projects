import java.util.*;

public class Node {
    private int id;
    private Map<Node, Double> neighbours;
    private double distance;
    private List<Node> shortestPath;

    public Node(int id) {
        this.id = id;
        this.neighbours = new HashMap<>();
        shortestPath = new LinkedList<>();
        distance = 0;
    }

    public void addNeighbour(Node neighbour, Double h) {
        this.neighbours.put(neighbour, h);
    }

    public Map<Node, Double> getNeighbours() {
        return neighbours;
    }
    public List<Node> getShortestPath() {
        return shortestPath;
    }
    public int getId() {
        return this.id;
    }
    public double getH(Node node){
        return neighbours.get(node);
    }
    public double getDistance() {
        return distance;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public void setNeighbours(Map<Node,Double> neighbours){
        this.neighbours = neighbours;
    }
}
