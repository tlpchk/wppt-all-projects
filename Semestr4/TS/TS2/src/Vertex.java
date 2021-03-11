import java.util.HashMap;
import java.util.Set;

class Vertex {

    protected int id;
    protected final HashMap<Integer, Double> neighbours;

    public Vertex(int id) {
        // Stores the set of neighbours and the edge weight
        this.id = id;
        this.neighbours = new HashMap<Integer, Double>();
    }

    public void addNeighbour(int id, Double h) {
        this.neighbours.put(id, h);
    }

    public Set<Integer> getNeighbours() {
        return this.neighbours.keySet();
    }

    public int getId() {
        return this.id;
    }
}