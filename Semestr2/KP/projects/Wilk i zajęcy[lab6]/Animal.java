package com.company;
/**Klasa umożliwiająca tworzenie zwieżąt w postaci wątków
 *
 * @author Maksym Telepchuk
 * @version 1.0
 */
public abstract class Animal implements Runnable {
    /**Plansza, na której odbywa się gra
     */
    protected Panel panel;

    /**Współrzędna zwierzęcia po x
     */
    protected int x;

    /**Współrzędna zwierzęcia po y
     */
    protected int y;

    /**Wymiar jednego punktu na plansze
    */
    protected int d = Panel.sizeOfPoint;

    /**Współrzędna wroga po x
     */
    protected int x_enemy;
    /**Współrzędna wroga po y
     */
    protected int y_enemy;

    /**Stwarza wątek z danymi zwierzęcia
     * @param panel Plansza, na której odbywa się gra
     */
    public Animal(Panel panel){
        this.panel=panel;
        this.x = (int) (Math.random() * panel.n) * d + 1;
        this.y = (int) (Math.random() * panel.m) * d + 1;
        int i =0;
        while(i!=panel.hares.size()) {
            if((x==panel.hares.get(i).x&&y==panel.hares.get(i).y)||(x==panel.wolf.x&&y==panel.wolf.y)) {
                this.x = (int) (Math.random() * panel.n) * d + 1;
                this.y = (int) (Math.random() * panel.m) * d + 1;
                i=-1;
            }
            i++;
        }
    }

    @Override
    public abstract void run();
}
