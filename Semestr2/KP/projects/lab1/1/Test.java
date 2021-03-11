/******* lab_2
***@autor Max Telepchuk****/
class WierszTrojkataPascala {                   
    int[] wiersz;

    public int wspolczynnik(int m) {return wiersz[m];}      // zwraca wartosc m-tego elementa wiersza pascala

    WierszTrojkataPascala(int n) {		//tworzy wiersz trojkata Pascala
        this.wiersz = new int[n + 1];
        this.wiersz[0] = 1;
        for (int i = 1; i <= n; i++) {
            int i_copy = i;
            while (i > 0) {
                wiersz[i] = wiersz[i] + wiersz[i - 1];
                i--;
            }
            i = i_copy;
        }
    }
}
public class Test {
    public static void main(String[] args) {
        int k=0;
	if (args.length==0){
		System.out.println("Podaj numer wiersu");
		return;
	}

        try {k = Integer.parseInt(args[0]);}
        catch (NumberFormatException ex) {
            	System.out.println("Numer wiersza musi byc liczba calkowita");
		return;
        }
        if(Integer.parseInt(args[0])>=0 && Integer.parseInt(args[0])<=33){
            int index=1;
            WierszTrojkataPascala a = new WierszTrojkataPascala(Integer.parseInt(args[0]));

            while(index<args.length){
                int h;
                try {h = Integer.parseInt(args[index]);}
                catch (NumberFormatException ex) {
                    System.out.println(args[index] + " - " + "he he he, ale jestes smieszny ;)");
                    index++;
                    continue;
                }

                if(h>=0 && h<a.wiersz.length) {
                    System.out.println(Integer.parseInt(args[index]) + " - " + a.wspolczynnik(Integer.parseInt(args[index])));
                }
                else{
                    System.out.println(Integer.parseInt(args[index]) + " - " + "liczba z poza zakresu");
                }
                index++;
            }
        }else{
       		System.out.print("Sorry, nie moge utworzyc wiersz " + Integer.parseInt(args[0]));
		return;
        }
    }
}



