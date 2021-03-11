package com.company;

/**Klasa umożliwiająca tworzenie zajęcy w postaci wątków
 *
 * @author Maksym Telepchuk
 * @version 1.0
 */

public class Hare extends Animal {
    private int position = 0;
//    position 1 - lewy gorny
//             2 - górna ściana
//             3 - prawy górny
//             4- lewa ściana
//             5 - prawa ściana
//             6- lewy dolny
//             7 - dolna ściana
//             8 - prawy dolny
//             0 - center

    /**Stwarza wątek z danymi zająca
     * @param panel Plansza, na której odbywa się gra
     */
    public Hare(Panel panel) {
        super(panel);
        x_enemy=panel.wolf.x;
        y_enemy=panel.wolf.y;
    }
    @Override
    public void run() {
        while (true)
        {
            try {
                Thread.sleep((long) Panel.speed);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            panel.setSize(panel.width,panel.height+1);
            panel.setSize(panel.width,panel.height);

            if (x - d <= 0 && x + d < panel.width  && y - d <= 0 && y + d < panel.height) position = 1;
            if (x - d > 0  && x + d < panel.width  && y - d <= 0 && y + d < panel.height) position = 2;
            if (x - d > 0  && x + d >= panel.width && y - d <= 0 && y + d < panel.height) position = 3;
            if (x - d <= 0 && x + d < panel.width  && y - d > 0  && y + d < panel.height) position = 4;
            if (x - d > 0  && x + d >= panel.width && y - d > 0  && y + d < panel.height) position = 5;
            if (x - d <= 0 && x + d < panel.width  && y - d >  0 && y + d >= panel.height) position = 6;
            if (x - d > 0  && x + d < panel.width  && y - d >  0 && y + d >= panel.height) position = 7;
            if (x - d > 0  && x + d >= panel.width && y - d >  0 && y + d >= panel.height) position = 8;
            if (x - d > 0  && x + d < panel.width  && y - d >  0 && y + d < panel.height) position = 0;

            if(position == 0)
            {
                if (x - x_enemy > 0)
                    right();
                if (x - x_enemy < 0)
                    left();
                if (y-y_enemy>0)
                    down();
                if(y-y_enemy<0)
                    up();
            }
            else {
                switch ((int) (Math.random() * 5)) {
                    case 0:
                        if (position == 1 || position == 2 || position == 3 || position == 4) down();
                        if (position == 6 || position == 7 || position == 8) up();
                        break;
                    case 1:
                        if (position == 1 || position == 4 || position == 6) right();
                        if (position == 2 || position == 3 || position == 5 || position == 7 || position == 8) left();
                        break;
                    case 2:
                        if (position == 1 || position == 4) {down();right();}
                        if (position == 2 || position == 3 || position == 5) {down();left();}
                        if (position == 7 || position == 8) {up();left();}
                        if (position == 6) {up();right();}
                        break;
                    case 3:
                        if (position == 4 || position == 7) {up();right();}
                        if (position == 2) {down();right();}
                        if (position == 5) {up();left();}
                        break;
                    case 4:
                        if (position == 4 || position == 5) up();
                        if (position == 2 || position == 7) right();
                }
            }

        }
    }

    /**
     * Premieszczenie zajęca na jeden punkt w górę
     */
    public synchronized void up(){
//        for (Hare hare : p.hares) {
//            if(hare.y == y-d)
//                return;
//        }
        y -= d;
    }

    /**
     * Premieszczenie zajęca na jeden punkt w prawo
     */
    public synchronized void right(){
//        for (Hare hare : p.hares) {
//            if(hare.x == x+d)
//                return;
//        }
        x += d;
    }

    /**
     * Premieszczenie zajęca na jeden punkt na dół
     */
    public synchronized void down(){
//        for (Hare hare : p.hares) {
//            if(hare.y==y+d)
//                return;
//        }
        y += d;
    }

    /**Premieszczenie zajęca na jeden punkt w lewo
     */
    public synchronized void left(){
//        for (Hare hare : p.hares) {
//            if(hare.x == x-d)
//                return;
//        }
        x -= d;
    }
}
