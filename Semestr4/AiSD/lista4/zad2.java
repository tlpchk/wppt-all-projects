import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class zad2 {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Test №1.Słownik");
        test_comparisons(100,"zad2_test1.txt");
        System.out.println("Test №2.Text");
        test_comparisons(100,"zad2_test2.txt");
    }

    static void test_comparisons(int count,String fileName) throws FileNotFoundException {
        int RBTavgComp = 0;
        int BSTavgComp = 0;
        int ChainAvgComp = 0;

        int index;
        int start;
        int end;
        RBTree rbTree = new RBTree();
        BSTree bsTree = new BSTree();
        Chain chain = new Chain();

        File file = new File(fileName);
        List<String> elements = new ArrayList<>();
        Random random = new Random();
        Scanner inputStream = new Scanner(file);
        while (inputStream.hasNextLine()) {
            String data = inputStream.nextLine();
            elements.addAll(Arrays.asList(data.split(" ")));
        }
        for (int i = 0; i < elements.size(); i++) {
            rbTree.insert(elements.get(i));
            chain.insert(elements.get(i));
            bsTree.insert(elements.get(i));
        }
        inputStream.close();
        System.out.println("Size: " + elements.size());

        for (int i = 0; i < count; i++) {
            index = random.nextInt(elements.size());
            String word = elements.get(index);

            start = rbTree.comparisons;
            rbTree.find(word);
            end = rbTree.comparisons;
            RBTavgComp += end - start;

            start = bsTree.comparisons;
            bsTree.find(word);
            end = bsTree.comparisons;
            BSTavgComp += end - start;

            start = chain.comparisons;
            chain.find(word);
            end = chain.comparisons;
            ChainAvgComp += end - start;

        }

        System.out.println("RBT: " + RBTavgComp/(double)count);
        System.out.println("BST: " + BSTavgComp/(double)count);
        System.out.println("CHAIN: "+ ChainAvgComp/(double)count);
    }
}
