import java.util.*;

public class Graph2 extends Graph {
    private Random random = new Random();
    private Set<Channel> channels;
    double p;
    double T_MAX;
    int[][] N;
    /*W tym prypadku c = ilość pakietów*/
    int c;

    public Graph2(double p, double t_MAX,int c) {
        super();
        channels = new HashSet<>();
        this.p = p;
        T_MAX = t_MAX;
        this.c = c;

        for (int i = 1; i <=10 ; i++) {
            nodes.add(new Node(i));
        }
    }

    public void generateEdges(){
        for (Node node : nodes) {
            node.setNeighbours(new HashMap<>());
        }

        generateEdge(1,2);
        generateEdge(2,3);
        generateEdge(3,4);
        generateEdge(4,5);

        generateEdge(6,1);
        generateEdge(7,2);
        generateEdge(8,3);
        generateEdge(9,4);
        generateEdge(10,5);

        generateEdge(6,8);
        generateEdge(7,9);
        generateEdge(8,10);
        generateEdge(9,6);
        generateEdge(10,7);

    }

    public void generateEdge(int uid,int vid){
        int r = random.nextInt(100);
        if(r >= 100*p){
            return;
        }
        addEdge(getNodeById(uid),getNodeById(vid),p);
    }

    public void addChannel(Node u, Node v, int c){
        channels.add(new Channel(u, v, c));
    }

    public Channel getChannel(Node u, Node v ){
        for (Channel channel : channels) {
            if (channel.getPair().contains(u) && channel.getPair().contains(v)){
                return channel;
            }
        }
        return null;
    }

    public double monteCarloChannel(int n,int max_value){
        double success = 0;

        for (int n0 = 0; n0 < n; n0++) {
            generateEdges();
            if(!isConsistent(this)){
                continue;
            }
            N = generateMatrix(max_value);
            if(parseMatrix()==-1) {
                continue;
            }
            if(countT() < T_MAX){
                success++;
            }
        }

        return success/n;
    }

    public int parseMatrix() {
        channels = new HashSet<>();
        List<Node> path;
        Node u;
        Node v;
        Node prev;
        Node next;

        for (int i = 0; i < this.N.length; i++) {
            for (int j = 0; j < this.N.length; j++) {
                if(i == j){
                    continue;
                }
                u = getNodeById(i+1);
                v = getNodeById(j+1);
                Graph.calculateShortestPathFromSource(this,u);
                path = v.getShortestPath();

                prev = path.get(0);
                path.add(v);
                for (int k = 1; k < path.size(); k++) {
                    next = path.get(k);
                    if(getChannel(prev,next) == null) {
                        addChannel(prev,next,c);
                    }

                    getChannel(prev,next).a += N[i][j];
                    if(getChannel(prev,next).a > getChannel(prev,next).c){
                        return -1;
                    }
                    if(k+1 < path.size()) {
                        prev = next;
                        next = path.get(k+1);
                    }
                }
            }
        }
        return 1;
    }

    public double countT(){
        //System.out.println((1.0/countG())*Sum_e() + " T");
        return (1.0/countG())*Sum_e();
    }

    public double Sum_e() {
        double sum_e = 0;
        for (Channel channel : channels) {
            sum_e += ((double)channel.a)/(channel.c-channel.a);
        }
        return sum_e;

    }

    public int countG(){
        int G = 0;
        for (int i = 0; i < N.length; i++) {
            for (int j = 0; j < N.length; j++) {
                G += N[i][j];
            }
        }
        return G;
    }

    public int[][] generateMatrix(int max_value){
        int[][] matrix = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(i == j){
                    continue;
                }

                matrix[i][j] = random.nextInt(max_value);
            }
        }
        return matrix;
    }

    public Set<Channel> getChannels() {
        return channels;
    }
}
