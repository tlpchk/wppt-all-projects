package com.company;
/**Klasa umożliwiająca tworzenie wilka w postaci wątku
 *
 * @author Maksym Telepchuk
 * @version 1.0
 */
public class Wolf extends Animal {

    /**Pokazuje, czy wilk jest głodny
     */
    boolean hungry = true;

    /**Stwarza wątek z danymi wilka
     * @param panel Plansza, na której odbywa się gra
     */
    public Wolf(Panel panel) {
        super(panel);
    }
    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep((long) Panel.speed);

                if(hungry==false) {
                    Thread.sleep((long) (4*Panel.speed));
                    hungry=true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!panel.hares.isEmpty()) {
                x_enemy = panel.hares.get(0).x;
                y_enemy = panel.hares.get(0).y;
            }
            for (Hare hare : panel.hares) {
                if (steps(x, y,hare.x, hare.y) < steps(x, y, x_enemy, y_enemy)) {
                    x_enemy = hare.x;
                    y_enemy = hare.y;
                }
            }

            if (x - x_enemy > 0)
                x-=d;
            if (x - x_enemy < 0)
                x+=d;
            if (y - y_enemy > 0)
                y-=d;
            if (y - y_enemy < 0)
                y+=d;
            if(x==x_enemy && y==y_enemy) {
                for (Hare hare : panel.hares) {
                    if(hare.x==x_enemy&&hare.y==y_enemy){
                        panel.hares.remove(hare);
                        break;
                    }
                }
                panel.wolf.hungry=false;
            }
        }
    }

    /**Liczy odległość do zająca
     * @param x1 Współędna wilka po x
     * @param y1 Współędna wilka po y
     * @param x2 Współędna zająca po x
     * @param y2 Współędna zająca po y
     * @return ilość krokow od wilka do zająca
     */
    public int steps(int x1,int y1,int x2,int y2) {return Math.max(Math.abs(x1-x2),Math.abs(y1-y2));}
}
