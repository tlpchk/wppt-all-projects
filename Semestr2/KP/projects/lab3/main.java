/*LAB_3_1*@autor Max Telepchuk*
*program, ktory oblicza pole i obwod figue*/

public class main {
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
                            Figura.K1 c = Figura.K1.KWADRAT;
                            c.bok=bok1;
                            System.out.println("Pole kwadratu " + c.ObliczPole());
                            System.out.println("Obwod kwadratu " +c.ObliczObwod());
                        } else if (kat > 0 && kat < 90) {
                            Figura.K2 c = Figura.K2.ROMB;
                            c.parametr1=bok1;
                            c.parametr2=kat;
                            System.out.println("Pole rombu " + c.ObliczPole());
                            System.out.println("Obwod rombu " +c.ObliczObwod());
                        } else {
                            System.out.println("Kat musi byc < 90");
                        }
                    } else if (((bok1 == bok2 && bok3 == bok4)||(bok1==bok3 && bok2==bok4)||(bok1==bok4 && bok2==bok3)) && kat == 90) {
                        Figura.K2 c = Figura.K2.PROSTOKAT;
                        c.parametr1 = Math.max(Math.max(bok1,bok2),Math.max(bok3,bok4));
                        c.parametr2 = Math.min(Math.min(bok1,bok2),Math.min(bok3,bok4));
                        System.out.println("Pole prostokatu " + c.ObliczPole());
                        System.out.println("Obwod prostokatu " +c.ObliczObwod());
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

                    Figura.K1 c = Figura.K1.OKRAG;
                    c.bok = promien;
                    System.out.println("Pole okragu " + c.ObliczPole());
                    System.out.println("Obwod okragu " + c.ObliczObwod());

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

                    Figura.K1 p = Figura.K1.PIECOKAT;
                    p.bok = bok;
                    System.out.println("Pole piecokatu " + p.ObliczPole());
                    System.out.println("Obwod piecokatu " + p.ObliczObwod());

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

                    Figura.K1 s = Figura.K1.SZESCIOKAT;
                    s.bok = bokk;
                    System.out.println("Pole " + s.ObliczPole());
                    System.out.println("Obwod " + s.ObliczObwod());

                    index++;
                    break;

                default:
                    System.out.println("NIEPOPRAWNE DANE");
                    return;
            }
        }
    }
}

class Figura {
    public enum K1{

        OKRAG{
            public double ObliczPole() {
                return bok * bok * Math.PI;
            }

            public double ObliczObwod() {
                return 2 * bok * Math.PI;
            }
        },
        KWADRAT{
            public double ObliczPole() {
                return bok * bok;
            }

            public double ObliczObwod() {
                return 4 * bok;
            }
        },
        PIECOKAT{
            public double ObliczPole() {
                return bok * bok * 1 / 4 * Math.sqrt(25 + Math.sqrt(500));
            }

            public double ObliczObwod() {
                return 5 * bok;
            }
        },
        SZESCIOKAT{
            public double ObliczPole() {
                return bok * bok * Math.sqrt(6.75);
            }

            public double ObliczObwod() {
                return 6 * bok;
            }
        };
        public double bok;
        public abstract double ObliczPole();
        public abstract double ObliczObwod();


    }

    public enum K2 {

        PROSTOKAT {
            public double ObliczPole() {return parametr1 * parametr2;}

            public double ObliczObwod() {
                return 2 * (parametr1 + parametr2);
            }
        },
        ROMB{
            public double ObliczPole() {
                return parametr1 * parametr1 * Math.sin(Math.PI * parametr2 / 180);
            }

            public double ObliczObwod() {
                return parametr1 * 4;
            }
        };

        double parametr1;
        double parametr2;
        public abstract double ObliczPole();
        public abstract double ObliczObwod();
    }
}