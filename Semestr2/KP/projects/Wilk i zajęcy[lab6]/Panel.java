package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**Klasa tworząca planszę dla gry
 *
 * @author Maksym Telepchuk
 * @version 1.0
 */
public class Panel extends JPanel {

    /**
     * Lista przechowójąca żywych zajęcy
     */
    ArrayList<Hare> hares = new ArrayList<Hare>();

    /**Wilk bierzący udział w grze
     */
    Wolf wolf;

    /**Wymair jednego punktu na plansze
     */
    static int sizeOfPoint=30;

    /**Liczba zajęcy bierzących udział w grze
     */
    private int countOfHares=20;

    /**Parametr prędkości zwierząt
     */
    private static double k=100;

    /**Prędkość zwierząt
     */
    static double speed =k*(Math.random()+0.5);

    /**Parametr długości planszy dla gry
     */
    final static int n=20;
    /**Parametr szerokości planszy dla gry
     */
    final static int m=30;

    /**Długość planszy dla gry
     */
    int width;

    /**Szerokość planszy dla gry
     */
    int height;

    /**Tworzy planszę dla gry z jej parametrami i parametrami zwierząt
     */
    public Panel(){
        this.height = m*sizeOfPoint;

        while(this.height>700) {
            sizeOfPoint -= 1;
            this.height = m * sizeOfPoint;
        }
        this.height = m * sizeOfPoint;
        this.width=n*sizeOfPoint;

        setSize(this.width, this.height);
        wolf = new Wolf(this);
        if (countOfHares>=m*n) {
            JOptionPane.showMessageDialog(null, "Zbyt wiele zające");
            System.exit(1);
        }
        if (m<1 || n<1) {
            JOptionPane.showMessageDialog(null, "Mało miejsca");
            System.exit(1);
        }
        while(countOfHares!=0){
            hares.add(new Hare(this));
            countOfHares-=1;
        }
    }
    @Override
    public void paintComponent(Graphics g){
        for (int i = 0; i < n+1; i++) {
            g.drawLine(sizeOfPoint*i,0,sizeOfPoint*i,getHeight());
        }
        for (int i = 0; i < m+1; i++) {
            g.drawLine(0,sizeOfPoint*i,getWidth(),sizeOfPoint*i);
        }

        g.setColor(Color.WHITE);
        for (Hare hare : hares) {
            g.fillOval(hare.x, hare.y, sizeOfPoint - 1, sizeOfPoint - 1);
        }
        g.setColor(Color.GRAY);
        g.fillOval(wolf.x,wolf.y,sizeOfPoint-1,sizeOfPoint-1);
        if (hares.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Mniam mniam!");
            System.exit(1);
        }
    };
}
