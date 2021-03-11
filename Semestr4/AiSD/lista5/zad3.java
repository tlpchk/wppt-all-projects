import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class zad3 {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

       /* int n0 = 5;
        int m0 = 10;
        System.out.println(n0);
        generate(n0,m0);*/

        Scanner reader = new Scanner(System.in);
        PrintWriter writer = new PrintWriter("out.res", "UTF-8");
        String command;
        List<String> arguments;
        long time = 0;
        long start, end;

        MST minimumSpanningTree = null;
        ArrayList<int[]> E = null;

        int n = reader.nextInt();
        if (args[0].equals("-k")) {
            minimumSpanningTree = new MSTKruksal(n);
        } else if (args[0].equals("-p")) {
            minimumSpanningTree = new MSTPrim(n);
        }
        int m = reader.nextInt();
        while (m >= 0) {
            command = reader.nextLine();
            arguments = Arrays.asList(command.split(" "));
            if (arguments.size() == 3) {
                if (args[0].equals("-k") || args[0].equals("-p") ) {
                    minimumSpanningTree.addEdge(Integer.parseInt(arguments.get(0)),
                            Integer.parseInt(arguments.get(1)),
                            Integer.parseInt(arguments.get(2)));
                }
            }
            m--;
        }


        start = System.nanoTime();

        if (args[0].equals("-k") || args[0].equals("-p")) {
            E = minimumSpanningTree.mst();
        }

        end = System.nanoTime();
        time = end - start;

        printEdges(E);
        System.err.println(time / Math.pow(10, 6) + " ms");
        reader.close();
        writer.close();

    }

    private static void printEdges(ArrayList<int[]> E) {
        int min;
        int max;
        int sum = 0;
        for (int[] edge: E) {
            min = Math.min(edge[0],edge[1]);
            max = Math.max(edge[0],edge[1]);

            System.out.println(min + " " + max + " " + edge[2]);
            sum += edge[2];
        }
        System.out.println(sum);
    }

    private static void generate(int n,int edges){
        Random random = new Random();
        int[][] tab = new int[n+1][n+1];
        int u, v, w;
        for (int i = 0; i < edges; i++) {
            u = random.nextInt(n) + 1;
            v = random.nextInt(n) + 1;
            while (u == v || tab[u][v] != 0){
                v = random.nextInt(n) + 1;
            }
            w = random.nextInt(n) + 1;
            tab[u][v] = w;
            tab[v][u] = w;
            System.out.println(Math.min(u,v)+" "+Math.max(u,v)+" "+w);
        }
    }

}

