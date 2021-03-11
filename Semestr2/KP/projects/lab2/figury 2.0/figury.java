/*LAB_3_1*@autor Max Telepchuk*
*program, ktory oblicza pole i obwod figue*/

public class figury {
    public static void main(String[] args) {
        if (args.length==0){                          // sprawdza, czy sa parametry
            System.out.print("NIEPOPRAWNE DANE");
            return;
        }

        Figura[] tabFigur = new Figura[args[0].length()];

        int i;                          //dla znakow w zerowym agrumencie
        int index=1;                     //"normalne" indeksy
        for(i=0;i<args[0].length();i++){
            System.out.println("");
            if(index>=args.length){return;}
            switch (args[0].charAt(i)) {
                case 'c':
                    if(args.length<index+4){                        //sprawdza,czy wystarczy argumentow dla tworzenia figury
                        System.out.print("NIEPOPRAWNE DANE");
                        return;
                    }
                    double bok1 = 0;
                    double bok2 = 0;
                    double bok3 = 0;
                    double bok4 = 0;
                    double kat = 0;
                    try {
                        bok1 = Double.parseDouble(args[index]);
                        bok2 = Double.parseDouble(args[index + 1]);
                        bok3 = Double.parseDouble(args[index + 2]);
                        bok4 = Double.parseDouble(args[index + 3]);
                        kat = Double.parseDouble(args[index + 4]);
                    } catch (NumberFormatException ex) {
                        System.out.println("NIEPOPRAWNE DANE");
                        return;
                    }

                    if (bok1 <= 0 || bok2 <= 0 || bok3 <= 0 || bok4 <= 0 || kat <= 0) {          //dodatnie argumenty
                        System.out.println("NIEPOPRAWNE DANE");
                        return;
                    }

                    if (bok1 == bok2 && bok2 == bok3 && bok3 == bok4) {
                        if (kat == 90) {
                            tabFigur[i] = new Kwadrat(bok1, bok2, bok3, bok4, kat);

                            } else if (kat > 0 && kat < 90) {
                            tabFigur[i] = new Romb(bok1, bok2, bok3, bok4, kat);

                        } else {
                            System.out.println("Kat musi byc < 90");
                        }
                    } else if (((bok1 == bok2 && bok3 == bok4)||(bok1==bok3 && bok2==bok4)||(bok1==bok4 && bok2==bok3)) && kat == 90) {        
                   	tabFigur[i] = new Prostokat(bok1, bok2, bok3, bok4, kat);
                    } else {
                        System.out.println("NIEPOPRAWNE DANE");
                        return;
                    }

                    index = index + 5;
                    break;

                case 'o':
                    if(args.length<index){
                        System.out.print("NIEPOPRAWNE DANE");
                        return;
                    }

                    double promien = 0;

                    try {
                        promien = Double.parseDouble(args[index]);
                    } catch (NumberFormatException ex) {
                        System.out.println("NIEPOPRAWNE DANE");
                        return;
                    }

                    if (promien < 0) {
                        System.out.println("NIEPOPRAWNE DANE");
                        return;
                    }

                    tabFigur[i] = new Okrag(promien);

                    index++;
                    break;

                case 'p':
                    if(args.length<index){
                        System.out.print("NIEPOPRAWNE DANE");
                        return;
                    }
                    double bok = 0;
                    try {
                        bok = Double.parseDouble(args[index]);
                    } catch (NumberFormatException ex) {
                        System.out.println("NIEPOPRAWNE DANE");
                        return;
                    }

                    if (bok < 0) {
                        System.out.println("NIEPOPRAWNE DANE");
                        return;
                    }

                    tabFigur[i] = new Piecokat(bok);

                    index++;
                    break;

                case 's':
                    if(args.length<index){
                        System.out.print("NIEPOPRAWNE DANE");
                        return;
                    }
                    double bokk = 0;
                    try {
                        bokk = Double.parseDouble(args[index]);
                    } catch (NumberFormatException ex) {
                        System.out.println("NIEPOPRAWNE DANE");
                        return;
                    }

                    if (bokk < 0) {
                        System.out.println("NIEPOPRAWNE DANE");
                        return;
                    }

                    tabFigur[i] = new Szesciokat(bokk);

                    index++;
                    break;

                default:
                    System.out.println("NIEPOPRAWNE DANE");
                    return;
            }
        }
        for(i=0;i<tabFigur.length();i++){
            System.out.println(tabFigur[i].square() + " " + tabFigur[i].circuit());
        }
    }
}


abstract class Czworokat extends Figura {
    private double bok1;
    private double bok2;
    private double bok3;
    private double bok4;
    private double kat;

    public Czworokat(double bok1, double bok2, double bok3, double bok4, double kat) {
        this.bok1 = bok1;
        this.bok2 = bok2;
        this.bok3 = bok3;
        this.bok4 = bok4;
        this.kat = kat;

    }
}

abstract class Figura {
    public abstract double square();
    public abstract double circuit();
}

class Kwadrat extends Czworokat {
    private double bok1;

    //public Kwadrat(double a){
        //super(a,a,a,a,90);
    public Kwadrat(double bok1, double bok2, double bok3, double bok4, double kat) {
        super(bok1, bok2, bok3, bok4, kat);
        this.bok1 = bok1;
    }

    public double square(){ return bok1*bok1; }
    public double circuit() { return 4*bok1; }
}

class Prostokat extends Czworokat{
   private double bok1, bok2, bok3, bok4;


    public Prostokat(double bok1, double bok2, double bok3, double bok4, double kat){
        super(bok1, bok2, bok3, bok4,kat);
        this.bok1 = bok1;
        this.bok2 = bok2;
        this.bok3 = bok3;
        this.bok4 = bok4;
    }
    public double square(){
        return Math.sqrt(bok1 * bok2 * bok3 * bok4);
    }

    public double circuit() {
        return bok1+bok2+bok3+bok4;
    }
}

class Romb extends Czworokat {
    private double bok1, kat;

    public Romb(double bok1, double bok2, double bok3, double bok4, double kat) {
        super(bok1, bok2, bok3, bok4, kat);
        this.bok1 = bok1;
        this.kat = kat;
    }

    public double square() {
        return bok1 * bok1 * Math.sin(Math.PI*kat/180);
    }

    public double circuit() {
        return bok1 * 4;
    }
}

class Okrag extends Figura{
    private double promien;

    public Okrag(double a){
        this.promien = a;
    }
    public double square(){ return promien*promien*Math.PI; }
    public double circuit() { return 2*promien*Math.PI; }
}

class Piecokat extends Figura{
    private double bok;

    Piecokat(double bok){
        this.bok = bok;
    }

    public double square(){return bok*bok*1/4*Math.sqrt(25 +Math.sqrt(500));}
    public double circuit(){return 5*bok;}
}

class Szesciokat extends Figura{
    private double bok;

    Szesciokat(double bok){
        this.bok = bok;
    }

    public double square(){return bok*bok*Math.sqrt(6.75);}
    public double circuit(){return 6*bok;}
}