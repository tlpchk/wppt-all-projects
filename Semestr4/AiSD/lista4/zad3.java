import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class zad3 {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        /*
        //k = m/n*ln2
        //n = 55
        Bloom bloom = new Bloom(300,5);
        bloom.load("zad3_test.txt");
        System.out.println(bloom.find("First"));
        System.out.println(bloom.find("three"));
        System.out.println(bloom.find("oops"));
        */
        DataStructure ds = null;
        if(args.length == 2 && args[0].equals("--type")) {
            switch (args[1]){
                case "hmap":
                    ds = new HMap(30, 1);
                    break;
                case "bst":
                    ds = new BSTree();
                    break;
                case "rbt":
                    ds = new RBTree();
                case "bloom":
                    ds = new Bloom(527,7);
            }
        }




        Scanner reader = new Scanner(System.in);
        PrintWriter writer = new PrintWriter("out.res", "UTF-8");
        String command;
        List<String> arguments;
        long time = 0;

        int n = reader.nextInt();
        while (n >= 0 && ds != null) {
            command = reader.nextLine();
            arguments = Arrays.asList(command.split(" "));
            if(arguments.size() == 1){
                long start = System.nanoTime();
                switch (arguments.get(0)) {
                    case "min":
                        writer.println(ds.min());
                        break;
                    case "max":
                        writer.println(ds.max());
                        break;
                    case "inorder":
                        writer.println(ds.inorder());
                        break;
                }
                long end = System.nanoTime();
                time += end - start;
            }
            else if(arguments.size() == 2){
                long start = System.nanoTime();
                String s = arguments.get(1);
                switch (arguments.get(0)) {
                    case "insert":
                        ds.insert(s);
                        break;
                    case "load":
                        ds.load(s);
                        break;
                    case "delete":
                        ds.delete(s);
                        break;
                    case "find":
                        writer.println(ds.find(s));
                        break;
                    case "successor":
                        writer.println(ds.successor(s));
                        break;
                }
                long end = System.nanoTime();
                time += end - start;
            }
            n--;
        }
        reader.close();
        writer.close();

        File out = new File("out.res");
        reader = new Scanner(out);
        while (reader.hasNextLine()) {
            System.out.println(reader.nextLine());
        }
        reader.close();

        System.err.println("Time(ms):"+time*0.000001);
        System.err.println("insert:"+ds.insert);
        System.err.println("load:"+ds.load);
        System.err.println("delete:"+ds.delete);
        System.err.println("find:"+ds.find);
        System.err.println("min:"+ds.min);
        System.err.println("max:"+ds.max);
        System.err.println("successor:"+ds.successor);
        System.err.println("inorder:"+ds.inorder);
        System.err.println("Maximum size:"+ds.maxSize);
        System.err.println("Size:"+ds.size);

    }
}
