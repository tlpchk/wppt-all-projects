package com.company;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Klasa głowna, w której odbywa się gra
 * @author Maksym Telepchuk
 * @version 1.0
 */
public class Main extends JFrame{
    /**Plansza dla gry
     */
    private Panel panel;
    /**Stwarza nową grę, ustawia rozmiary
     */
    public Main(){
        panel=new Panel();
        getContentPane().add(panel);
        setSize(panel.getWidth()+17,panel.getHeight()+40);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException {
        Main m = new Main();
        ArrayList<Thread> threads = new ArrayList<>();
        for (Hare hare : m.panel.hares) {
            threads.add(new Thread(hare));
        }
        threads.add(new Thread(m.panel.wolf));
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        for (Thread thread : threads) {
            thread.stop();
        }
    }
}