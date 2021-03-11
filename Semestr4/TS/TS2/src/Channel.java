import java.util.HashSet;
import java.util.Set;

public class Channel {
    private Set<Node> pair;
    int c;
    int a;

    public Channel(Node u, Node v, int c) {
        pair = new HashSet<>();
        this.pair.add(u);
        this.pair.add(v);
        this.c = c;
    }

    public Set<Node> getPair() {
        return pair;
    }

}
