import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Panel extends JPanel implements MouseListener, MouseMotionListener,MouseWheelListener {
    private ArrayList<Figura> figury = new ArrayList<Figura>();
    private int mouseX, mouseY;
    private JPopupMenu popup = new MyPopupMenu(this);
    private ArrayList<Integer> coordsOfCircle = new ArrayList<Integer>();
    private ArrayList<Integer> coordsOfRectangle = new ArrayList<Integer>();
    private ArrayList<Integer> coordsOfPolygonsX = new ArrayList<Integer>();
    private ArrayList<Integer> coordsOfPolygonsY = new ArrayList<Integer>();


    private int countCircles = 0;
    private int countRectanges = 0;
    private int countPolygons = 0;


    public void setCoordsOfCircle(ArrayList<Integer> coordsOfCircle) {
        this.coordsOfCircle = coordsOfCircle;
    }

    public void setCoordsOfRectangle(ArrayList<Integer> coordsOfRectangle) {
        this.coordsOfRectangle = coordsOfRectangle;
    }


    public void setCoordsOfPolygonsX(ArrayList<Integer> coordsOfPolygonsX) {
        this.coordsOfPolygonsX = coordsOfPolygonsX;
    }


    public void setCoordsOfPolygonsY(ArrayList<Integer> coordsOfPolygonsY) {
        this.coordsOfPolygonsY = coordsOfPolygonsY;
    }

    public int getCountCircles() {
        return countCircles;
    }

    public void setCountCircles(int countCircles) {
        this.countCircles = countCircles;
    }

    public int getCountRectanges() {
        return countRectanges;
    }

    public void setCountRectanges(int countRectanges) {
        this.countRectanges = countRectanges;
    }

    public int getCountPolygons() {
        return countPolygons;
    }

    public void setCountPolygons(int countPolygons) {
        this.countPolygons = countPolygons;
    }

    public ArrayList<Figura> getFigury() {
        return figury;
    }

    public void setFigury(ArrayList<Figura> figury) {
        this.figury = figury;
    }

    public void addFigure(Figura figura) {
        for (Figura f : figury) {
            f.selectFalse();
        }
        figura.selectTrue();
        figury.add(figura);
        //"odswiezenie" okna
        repaint();
    }

    public void move(int dx, int dy) {
        for (Figura f : figury) {
            //gdy figura jest zaznaczona - przesuwany
            if (f.getSelect()) f.move(dx, dy);
        }
        repaint();
    }

    public void addCircle(int x_center, int y_center, int x_edge, int y_edge) {
        addFigure(new Okrag(x_center, y_center, x_edge, y_edge));
    }

    public void addRectangle(int x1, int y1, int x2, int y2) {
        addFigure(new Prostokat(x1, y1, x2, y2));
    }



    /*//** Metoda rysujaca komponent - figure */
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        for (Figura f : figury) f.draw(g);
        for (int i = 1; i < coordsOfPolygonsX.size(); i++) {
            g.drawLine(coordsOfPolygonsX.get(i - 1), coordsOfPolygonsY.get(i - 1), coordsOfPolygonsX.get(i), coordsOfPolygonsY.get(i));
        }
    }


    //** Metoda umozliwiajaca obsluge klikania przyciskow myszki *//*
    public void mouseClicked(MouseEvent e) {
        //pobranie wspolrzednych figury
        int px = e.getX();
        int py = e.getY();


        if (SwingUtilities.isRightMouseButton(e)) {
            for (Figura f : figury) {
                if (f.getSelect() && f.inside(px, py))
                    popup.show(this, px, py);
            }
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (countCircles > 0) {
                coordsOfCircle.add(px);
                coordsOfCircle.add(py);

                if (coordsOfCircle.size() == 4) {

                    addCircle(coordsOfCircle.get(0),coordsOfCircle.get(1),coordsOfCircle.get(2),coordsOfCircle.get(3));
                    countCircles -= 1;

                    coordsOfCircle = new ArrayList<Integer>();
                }
            }

            if (countRectanges > 0) {
                coordsOfRectangle.add(px);
                coordsOfRectangle.add(py);

                if (coordsOfRectangle.size() == 4) {
                    addRectangle(coordsOfRectangle.get(0), coordsOfRectangle.get(1), coordsOfRectangle.get(2), coordsOfRectangle.get(3));
                    countRectanges -= 1;

                    coordsOfRectangle = new ArrayList<Integer>();
                }
            }

            if (countPolygons > 0) {
                coordsOfPolygonsX.add(px);
                coordsOfPolygonsY.add(py);

                if (Math.abs(coordsOfPolygonsX.get(0) - px) < 5 &&
                        Math.abs(coordsOfPolygonsY.get(0) - py) < 5 && coordsOfPolygonsX.size() > 1) {
                    addFigure(new Wielokat(coordsOfPolygonsX, coordsOfPolygonsY));

                    countPolygons = 0;
                    coordsOfPolygonsX = new ArrayList<Integer>();
                    coordsOfPolygonsY = new ArrayList<Integer>();
                }

                repaint();
            }
        }

        for (int i = 0; i < figury.size(); i++) {
            figury.get(i).selectFalse();
        }

        for (int i = figury.size() - 1; i >= 0; i--) {
            if (figury.get(i).inside(px, py)) {
                Figura f = figury.get(i);
                figury.remove(i);
                figury.add(f);
                figury.get(figury.size() - 1).selectTrue();
                break;
            }
        }
        repaint();
    }

    /* Metoda umozliwiajaca obsluge "najechania" na figure*/
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Metoda umozliwiajaca obsluge opuszczenia obszaru figury
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Metoda umozliwiajaca obsluge wcisniecia przyciskow myszki
     */
    public void mousePressed(MouseEvent e) {
        //zapamietujemy pozycje myszki
        mouseX = e.getX();
        mouseY = e.getY();
    }


    public void mouseWheelMoved(MouseWheelEvent e) {

        mouseX = e.getX();
        mouseY = e.getY();

        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {

            for (Figura figura : figury) {
                if (figura.getSelect()) {
                    double amount = e.getWheelRotation() * (-5f);
                    figura.changeSize(amount);
                    repaint();
                }
            }
        }
    }

    /**
     * Metoda umozliwiajaca obsluge zwolnienia przyciskow myszki
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Metoda umozliwiajaca obsluge "przeciagania" figury myszka
     */
    public void mouseDragged(MouseEvent e) {
        if (figury.get(figury.size() - 1).getSelect()) {

            //zmienne pomocnicze przechowujace nowe wspolrzedne myszki
            int newMouseX = e.getX();
            int newMouseY = e.getY();
            //wspolrzedne przesuniecia
            int x = 0, y = 0;

            //wartosc przesuniecia
            x += newMouseX - mouseX;
            y += newMouseY - mouseY;

            //przesuniecie figury
            move(x, y);

            //aktualizacja pozycji myszki
            mouseX = newMouseX;
            mouseY = newMouseY;
            repaint();
        }
    }

    /**
     * Metoda umozliwiajaca obsluge przesuniecia myszki
     */
    public void mouseMoved(MouseEvent e) {
    }

    public void writeInFile() throws IOException {
        try {
            String outputFileName =
                    System.getProperty("user.home",
                            File.separatorChar + "home" +
                                    File.separatorChar + "zelda") +
                            File.separatorChar + "text.txt";
            File outputFile = new File(outputFileName);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));

            for (Figura figura : figury) {
                if(figura.getClass()==Okrag.class){
                    int x1 = ((Okrag) figura).getX();
                    int y1 = ((Okrag) figura).getY();
                    int x2 = x1+((Okrag) figura).getPromien();
                    int y2 = y1;
                    writer.println("O ("+ x1 + "," + y1 + ")"+
                            " (" + x2 + "," + y2 + ");") ;
                }

                if(figura.getClass()==Prostokat.class){
                    int x1 = ((Prostokat) figura).getX1();
                    int y1 = ((Prostokat) figura).getY1();
                    int x2 = ((Prostokat) figura).getX2();
                    int y2 = ((Prostokat) figura).getY2();
                    writer.println("P ("+ x1 + "," + y1 + ")"+
                            " (" + x2 + "," + y2 + ");") ;
                }

                if(figura.getClass()==Wielokat.class){

                    String text = "";
                    for (int i = 0; i < ((Wielokat)figura).getX().length -1; i++) {
                        int x=((Wielokat)figura).getX()[i];
                        int y=((Wielokat)figura).getY()[i];
                        text+=(" ("+x + "," + y + ")");
                    }
                    writer.println("W"+text+";");
                }
            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("Cannot access text.txt");
        }
    }

    public void readFile() throws Exception{
        try {
            String sourse = "";

            String inputFileName =
                    System.getProperty("user.home",
                            File.separatorChar + "home" +
                                    File.separatorChar + "zelda") +
                            File.separatorChar + "text.txt";
            File inputFile = new File(inputFileName);
            FileReader in = new FileReader(inputFile);
            char c[] = new
                    char[(char) inputFile.length()];
            in.read(c);
            sourse = new String(c);
            in.close();

            System.out.println(sourse);
            figury = new ArrayList<Figura>();

            char[] chars = sourse.toCharArray();
            for (int i = 0, n = chars.length; i < n; i++) {
                if(chars[i]=='O'){
                    int x1=0,x2=0,y1=0,y2=0;
                    String  s="";

                    i+=3;
                    do{s+=String.valueOf(chars[i++]);}while(chars[i]!=',');
                    i++;
                    x1=Integer.parseInt(s);
                    s="";
                    do{s+=String.valueOf(chars[i++]);}while(chars[i]!=')');
                    i+=3;
                    y1=Integer.parseInt(s);
                    s="";
                    do{s+=String.valueOf(chars[i++]);}while(chars[i]!=',');
                    i++;
                    x2=Integer.parseInt(s);
                    s="";
                    do{s+=String.valueOf(chars[i++]);}while(chars[i]!=')');
                    y2=Integer.parseInt(s);

                    addCircle(x1,y1,x2,y2);

                    repaint();
                }

                if(chars[i]=='P'){
                    int x1=0,x2=0,y1=0,y2=0;
                    String  s="";

                    i+=3;
                    do{s+=String.valueOf(chars[i++]);}while(chars[i]!=',');
                    i++;
                    x1=Integer.parseInt(s);
                    s="";
                    do{s+=String.valueOf(chars[i++]);}while(chars[i]!=')');
                    i+=3;
                    y1=Integer.parseInt(s);
                    s="";
                    do{s+=String.valueOf(chars[i++]);}while(chars[i]!=',');
                    i++;
                    x2=Integer.parseInt(s);
                    s="";
                    do{s+=String.valueOf(chars[i++]);}while(chars[i]!=')');
                    y2=Integer.parseInt(s);

                    addRectangle(x1,y1,x2,y2);

                    repaint();

                }

                if(chars[i]=='W'){
                    ArrayList<Integer> x= new ArrayList<Integer>();
                    ArrayList<Integer> y= new ArrayList<Integer>();
                    String  s="";
                    i += 1;
                    while(chars[i]!=';') {
                        i+=2;
                        do {
                            s += String.valueOf(chars[i++]);
                        } while (chars[i] != ',');
                        i++;
                        x.add(Integer.parseInt(s));
                        s = "";
                        do {
                            s += String.valueOf(chars[i++]);
                        } while (chars[i] != ')');
                        i += 1;
                        y.add(Integer.parseInt(s));
                        s = "";
                    }

                    addFigure(new Wielokat(x, y));

                    repaint();
                }
            }

        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Text nie zostal zapisany");
        }
    }
}
