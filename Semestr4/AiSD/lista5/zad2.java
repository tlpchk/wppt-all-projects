import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class zad2 {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner reader = new Scanner(System.in);
        PrintWriter writer = new PrintWriter("out.res", "UTF-8");
        String command;
        List<String> arguments;
        long time = 0;
        long start,end;

        int n = reader.nextInt();
        DijkstraHeap dijkstryHeap = new DijkstraHeap(n);
        int m = reader.nextInt();
        while (m >= 0) {
            command = reader.nextLine();
            arguments = Arrays.asList(command.split(" "));
            if(arguments.size() == 3) {
                start = System.nanoTime();

                dijkstryHeap.addEdge(Integer.parseInt(arguments.get(0)),
                                    Integer.parseInt(arguments.get(1)),
                                    Integer.parseInt(arguments.get(2)));

                end = System.nanoTime();
                time = end - start;
            }
            m--;
        }
        int s = reader.nextInt();

        try {
            start = System.nanoTime();

            dijkstryHeap.dijkstra(s);

            end = System.nanoTime();
            time = end - start;
        } catch (Exception e) {
            e.printStackTrace();
        }

        dijkstryHeap.print();
        dijkstryHeap.printPaths();
        System.err.println(time/Math.pow(10,6) + " ms");
        reader.close();
        writer.close();
    }
}
