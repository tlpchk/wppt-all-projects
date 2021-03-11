import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class zad1 {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner reader = new Scanner(System.in);
        PrintWriter writer = new PrintWriter("out.res", "UTF-8");
        String command;
        List<String> arguments;

        PriorityQueue<Integer> priorityQueue = new PriorityQueue();

        int n = reader.nextInt();
        while (n >= 0) {
            command = reader.nextLine();
            arguments = Arrays.asList(command.split(" "));
            if (arguments.size() == 1) {
                switch (arguments.get(0)) {
                    case "empty":
                        if(priorityQueue.empty()){
                            writer.println(1);
                        }else {
                            writer.println(0);
                        }
                        break;
                    case "top":
                        priorityQueue.top();
                        break;
                    case "pop":
                        try {
                            writer.println(priorityQueue.pop());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "print":
                        priorityQueue.print();
                        break;
                }
            } else if (arguments.size() == 3) {
                int x = Integer.parseInt(arguments.get(1));
                int p = Integer.parseInt(arguments.get(2));
                switch (arguments.get(0)) {
                    case "insert":
                        priorityQueue.insert(x,p);
                        break;
                    case "priority":
                        priorityQueue.priority(x,p);
                        break;
                }
            }
            n--;
        }
        reader.close();
        writer.close();

        /*File out = new File("out.res");
        reader = new Scanner(out);
        while (reader.hasNextLine()) {
            System.out.println(reader.nextLine());
        }
        reader.close();*/
    }
}

