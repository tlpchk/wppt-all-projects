import java.io.*;
import java.util.*;


public class zad1 {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
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
            }
        }

        //Część do szacowania n(t)
        /*
        System.out.println(test_RBT_and_chain_speed(100000,33,"sample.txt"));
        for (int i = 0; i < ((HMap) ds).hmap.length; i++) {
           System.out.println(((HMap) ds).hmap[i].size);
        }*/


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

    static double test_RBT_and_chain_speed(int count,int nt,String fileName) throws FileNotFoundException {
        long timeRBT = 0;
        long timeCHAIN = 0;
        long start;
        long end;
        int index;
        Chain chain = new Chain();
        RBTree rbTree = new RBTree();

        File file = new File(fileName);
        List<String> elements = new ArrayList<>();
        Random random = new Random();
        Scanner inputStream = new Scanner(file);
        while (inputStream.hasNextLine()) {
            String data = inputStream.nextLine();
            elements.addAll(Arrays.asList(data.split(" ")));
        }
        for (int i = 0; i < nt; i++) {
            rbTree.insert(elements.get(i));
            chain.insert(elements.get(i));
        }
        inputStream.close();

        for (int i = 0; i < count; i++) {
            index = random.nextInt(nt);
            String word = elements.get(index);

            start = System.nanoTime();
            rbTree.find(word);
            end = System.nanoTime();
            timeRBT += end - start;

            start = System.nanoTime();
            chain.find(word);
            end = System.nanoTime();
            timeCHAIN += end - start;
        }

        return (1.0*timeRBT)/timeCHAIN;
    }
}
