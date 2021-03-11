import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class DataStructure {
    protected String inorderString;
    protected int size;
    protected int maxSize;
    protected Node root;
    protected Node Nil;

    int insert;
    int load;
    int delete;
    int find;
    int min;
    int max;
    int successor;
    int inorder;
    int comparisons;

    abstract void insert(String s);
    abstract int delete(String key);

    void load(String fileName){
        load++;
        File file = new File(fileName);
        String data;

        try {
            Scanner inputStream = new Scanner(file);
            List<String> elements = new ArrayList<>();
            while (inputStream.hasNextLine()) {
                data = inputStream.nextLine();
                elements.addAll(Arrays.asList(data.split(" ")));
            }
            for (String s : elements) {
                this.insert(s);
                this.insert--;
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    };
    public int find(String s){
        find++;
        Node x = subfind(s,root);
        if(x == Nil)
            return 0;
        else
            return 1;
    }
    protected Node subfind(String key, Node x) {
        if(x != Nil) {
            int c = x.key.compareTo(key);
            comparisons++;
            if (c == 0) {
                return x;
            }
            if (c > 0) {
                return subfind(key, x.left);
            } else {
                return subfind(key, x.right);
            }
        }
        else{
            return Nil;
        }
    }
    public String min(){
        min++;
        for (int i = 0; i < 100000; i++) {

        }
        Node x = submin(root);
        if (x == Nil)
            return "";
        else
            return x.key;

    }
    protected Node submin(Node x){
        if (x == Nil)
            return x;
        while (x.left != Nil){
            x = x.left;
        }
        return x;
    }
    public String max(){
        max++;
        Node x = submax(root);
        if (x == Nil)
            return "";
        else
            return x.key;
    }
    protected Node submax(Node x){
        if (x == Nil)
            return x;
        while (x.right!= Nil){
            x = x.right;
        }
        return x;
    }
    public String successor(String k){
        successor++;
        Node x = subsuccessor(subfind(k,root));
        if (x == Nil)
            return "";
        else
            return x.key;
    }
    protected Node subsuccessor(Node x){
        if (x == Nil){
            return x;
        }
        if (x.right != Nil){
            return submin(x.right);
        }

        Node y = x.parent;
        while (y != Nil && y.right == x){
            x = x.parent;
            y = y.parent;
        }

        return y;
    }
    public String inorder() {
        inorder++;
        return subinorder(root);
    }
    protected String subinorder(Node x){
        if(x == root){
            this.inorderString = "";
        }

        if ( x != Nil){
            subinorder(x.left);
            this.inorderString += x.key + " ";
            subinorder(x.right);
        }
        return this.inorderString;
    }

    protected String cleanMyNodeForInsert(String s){
        if(s.length() == 0){
            return "";
        }
        char c = s.charAt(0);
        if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z'))
            s = s.substring(1);

        if(s.length() == 0){
            return "";
        }

        c = s.charAt(s.length()-1);
        if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z'))
            s = s.substring(0, s.length() - 1);

        return s;
    }

    DataStructure(){
        insert = 0;
        load = 0;
        delete = 0;
        find = 0;
        min = 0;
        max = 0;
        successor = 0;
        inorder = 0;

        Nil = null;
        root = Nil;
        inorderString = "";
    }
}
