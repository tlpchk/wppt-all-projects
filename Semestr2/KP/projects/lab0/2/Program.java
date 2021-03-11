public class Program {                                     /*wypisuje parametry programy w kolejnych wierszach
							    ************@autor Max Telepchuk*****************/
    static public void main(String[] args) {
        int n = args.length;
        for (int i = 0; i < n; i++) {
            System.out.println(args[i]);
        }
    }
}
