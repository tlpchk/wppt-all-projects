import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.util.*;

import static jdk.nashorn.internal.objects.Global.print;

public class zad4 {
    private static Random random = new Random();

    /*
5
3 4 2
1 4 3
3 5 5
1 3 1
1 5 2
2 5 5
2 4 2
4 5 1
1 2 2
2 3 3
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner reader = new Scanner(System.in);
        PrintWriter writer = new PrintWriter("out.res", "UTF-8");
        String command;
        List<String> arguments;


        int n = reader.nextInt();
        Graph graph = new Graph(n);
        n = (n*(n-1))/2;
        while (n >= 0) {
            command = reader.nextLine();
            arguments = Arrays.asList(command.split(" "));
            if (arguments.size() == 3) {
                    graph.addEdge(Integer.parseInt(arguments.get(0)),
                            Integer.parseInt(arguments.get(1)),
                            Integer.parseInt(arguments.get(2)));
            }
            n--;
        }

        int[] randomExplore = randomExplore(graph);
        int[] greedyExplore = greedyExplore(graph);
        int[] mstExplore = mstExplore(graph);
        printArray(randomExplore);
        printArray(greedyExplore);
        printArray(mstExplore);

        reader.close();
        writer.close();

        //tests();
    }


    private static void tests(){
        Graph graph;
        int[] randomExplore;
        int[] greedyExplore;
        int[] mstExplore;
        for (int i = 5; i <= 500000 ; i*=10) {
            graph = new Graph(i);
            graph.K_Graph();
            randomExplore = randomExplore(graph);
            greedyExplore = greedyExplore(graph);
            mstExplore = mstExplore(graph);

            printArray(randomExplore);
            printArray(greedyExplore);
            printArray(mstExplore);
        }
    }

    private static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.print("\n");
    }

    private static int[] randomExplore(Graph G){
        int[] result;
        int k = 0,W = 0;
        long t,M;
        t = System.nanoTime();

        M = Runtime.getRuntime().freeMemory();

        int n = G.n;
        int u,v;
        int[] unvisitedNodes = new int[n+1];

        M = Runtime.getRuntime().freeMemory() - M;

        for (int i = 1; i <= n; i++) {
            unvisitedNodes[i] = G.nodes[i];
        }

        v = random.nextInt(n)+1;
        System.err.print("RandomExplore:" + v);
        unvisitedNodes[v] = 0;
        n--;

        while(n > 0){
            u = random.nextInt(G.n)+1;
            while (u == v){
                u = random.nextInt(G.n)+1;
            }

            System.err.print("-->" + u);
            W = W + G.edges[u][v];
            v = u;
            if(unvisitedNodes[u] != 0){
                unvisitedNodes[u] = 0;
                n--;
            }
            k++;
        }
        System.err.print("\n");

        t = System.nanoTime() - t;
        result = new int[]{k,W, (int) M,(int) t};
        return result;
    }

    private static int[] greedyExplore(Graph G){
        int[] result;
        int k = 0,W = 0;
        long t,M;
        t = System.nanoTime();


        M = Runtime.getRuntime().freeMemory();

        int n = G.n;
        int u,v;
        int[] unvisitedNodes = new int[n+1];


        M = Runtime.getRuntime().freeMemory() - M;

        for (int i = 1; i <= n; i++) {
            unvisitedNodes[i] = G.nodes[i];
        }

        v = 1;
        System.err.print("GreedyExplore:" + v);
        unvisitedNodes[v] = 0;
        n--;

        while(n > 0){
            u = nearestUnvisitedNeighbour(v,G,unvisitedNodes);
            System.err.print("-->" + u);
            W = W + G.edges[u][v];
            v = u;

            unvisitedNodes[u] = 0;
            n--;

            k++;
        }
        System.err.print("\n");

        t = System.nanoTime() - t;
        result = new int[]{k,W, (int) M,(int) t};
        return result;
    }

    private static int[] mstExplore(Graph G){
        int[] result;
        int k = 0,W = 0;
        long t,M;
        t = System.nanoTime();


        M = Runtime.getRuntime().freeMemory();

        int n = G.n;
        MST mstObject = new MSTKruksal(n);
        mstObject.addEdges(G.edges);
        int u,v;
        int[] prev = new int[n+1];
        ArrayList<int[]> mstEdges = mstObject.mst();

        ArrayList<ArrayList<Integer>> neighborhood = new ArrayList<>();

        M = Runtime.getRuntime().freeMemory() - M;

        for (int i = 0; i <= n; i++) {
            prev[i] = -1;
            neighborhood.add(new ArrayList<>());
        }

        for (int[] edge: mstEdges) {
            W += 2 * edge[2];
            neighborhood.get(edge[0]).add(edge[1]);
            neighborhood.get(edge[1]).add(edge[0]);
            neighborhood.get(edge[0]).add(edge[1]);
            neighborhood.get(edge[1]).add(edge[0]);
        }
        n *= 2;
        v = 1;
        System.err.print("MSTExplore:" + v);
        n--;

        while(n > 1){
            u = eulerStep(v,neighborhood,prev);
            System.err.print("-->" + u);
            v = u;

            n--;
            k++;
        }
        System.err.print("\n");

        t = System.nanoTime() - t;
        result = new int[]{k,W, (int) M,(int) t};
        return result;
    }

    private static int eulerStep(int v, ArrayList<ArrayList<Integer>> neighborhood, int[] prev) {
        //if v is leaf or if we won't fail connectivity
        int result;
        if(neighborhood.get(v).size() == 1 ){
            result = neighborhood.get(v).get(0);
        }
        else if(prev[v] != neighborhood.get(v).get(0)){
            result = neighborhood.get(v).get(0);
            prev[result] = v;
        }
        else {
            result = neighborhood.get(v).get(1);
            prev[result] = v;
        }

        deleteEdge(v,result,neighborhood);
        return result;
    }

    private static void deleteEdge(int u,int v, ArrayList<ArrayList<Integer>> neighborhood){
        ArrayList<Integer> U = neighborhood.get(u);
        ArrayList<Integer> V = neighborhood.get(v);
        for (int i = 0; i < U.size(); i++) {
            if(U.get(i) == v){
                U.remove(i);
                break;
            }
        }
        for (int i = 0; i < V.size(); i++) {
            if(V.get(i) == u){
                V.remove(i);
                break;
            }
        }
    }

    private static int nearestUnvisitedNeighbour(int v, Graph graph, int[] unvisited) {
        int result = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 1; i <= graph.n; i++) {
            if(unvisited[i] !=0 && graph.edges[v][i] < min && i != v){
                min = graph.edges[v][i];
                result = i;
            }
        }
        return result;
    }

    private static class Graph {
        int[] nodes;
        int[][] edges;
        int n;

        public Graph(int n) {
            this.n = n;
            edges = new int[n+1][n+1];
            nodes = new int[n+1];

            constructNodes(n);
        }

        public void addEdge(int u, int v, int w) {
            if (this.nodes[u] == 0 && this.nodes[v] == 0) {
                System.err.println("Nodes don't exist");
                return;
            }
            edges[u][v] = w;
            edges[v][u] = w;
        }

        public void constructNodes(int count){
            for (int i = 1; i <= count; i++) {
                nodes[i] = i;
            }
        }

        public void K_Graph(){
            int w;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if(i == j) {
                        continue;
                    }
                    w = (2*random.nextInt(n))+1;
                    if(i<j) {
                        addEdge(i,j,w);
                    }
                }
            }
        }
    }
}
