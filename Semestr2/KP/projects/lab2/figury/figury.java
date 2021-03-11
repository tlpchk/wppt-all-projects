/*LAB_3_1*@autor Max Telepchuk*
*program, ktory oblicza pole i obwod figue*/

public class figury {
    public static void main(String[] args) {
        if (args.length==0){                          // sprawdza, czy sa parametry
            System.out.print("NIEPOPRAWNE DANE");
            return;
        }

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
                            Czworokat c = new Kwadrat(bok1, bok2, bok3, bok4, kat);
                            System.out.println("Pole kwadratu o boku "+bok1 + " = " + c.square());
                            System.out.println("Obwod kwadratu o boku "+bok1 + " = " +  c.circuit());
                        } else if (kat > 0 && kat < 90) {
                            Czworokat c = new Romb(bok1, bok2, bok3, bok4, kat);
                            System.out.println("Pole rombu o boku " + bok1 + " i katu " + kat + " = "+ c.square());
                            System.out.println("Obwod rombu o boku " + bok1 +" = "  + c.circuit());
                        } else {
                            System.out.println("Kat musi byc < 90");
                        }
                    } else if (((bok1 == bok2 && bok3 == bok4)||(bok1==bok3 && bok2==bok4)||(bok1==bok4 && bok2==bok3)) && kat == 90) {        
                   	Czworokat c = new Prostokat(bok1, bok2, bok3, bok4, kat);
                        System.out.println("Pole prostokatu o bokach " + Math.min(Math.min(bok1,bok2),Math.min(bok3,bok4)) +" i " + Math.max(Math.max(bok1,bok2),Math.max(bok3,bok4)) + " = " + c.square());
                        System.out.println("Obwod prostokatu o bokach " + Math.min(Math.min(bok1,bok2),Math.min(bok3,bok4)) +" i " + Math.max(Math.max(bok1,bok2),Math.max(bok3,bok4)) + " = " + c.circuit());
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

                    Okrag o = new Okrag(promien);
                    System.out.println("Pole okragu o promienu " + promien + " = " + o.square());
                    System.out.println("Obwod okragu o promienu " + promien + " = " + o.circuit());

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

                    Piecokat p = new Piecokat(bok);
                    System.out.println("Pole piecokatu o boku " + bok + " = " + p.square());
                    System.out.println("Obwod piecokatu o boku " + bok + " = "+ p.circuit());

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

                    Szesciokat s = new Szesciokat(bokk);
                    System.out.println("Pole szesciokatu o boku " + bokk + " = "+ s.square());
                    System.out.println("Obwod szesciokatu o boku " + bokk + " = "+ s.circuit());

                    index++;
                    break;

                default:
                    System.out.println("NIEPOPRAWNE DANE");
                    return;
            }
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

public enum K1 extends Figura{
    OKRAG(super.bok){
        public double ObliczPole(){return bok*bok*Math.PI;}
        public double ObliczObwod(){return 2*bok*Math.PI;}
    },
    KWADRAT(super.bok){
        public double ObliczPole(){return bok*bok;}
        public double ObliczObwod(){return 4*bok;}
    },
    PIECOKAT(super.bok){
        public double ObliczPole(){return bok*bok*1/4*Math.sqrt(25 +Math.sqrt(500));}
        public double ObliczObwod(){return 5*bok;}
    },
    SZESCIOKAT(super.bok){
        public double ObliczPole(){return bok*bok*Math.sqrt(6.75);}
        public double ObliczObwod(){return 6*bok;}
    };
        K1(double bok){
            super.bok = bok;
    }
}

abstract class Figura {
    public double bok;
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