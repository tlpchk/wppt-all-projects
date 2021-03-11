package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**Klasa, która implementuje funkcji serwera w programie drzewa binarnego
 */
public class Server implements Runnable {

    /*Drzewo binarne serwera
     */
    private Tree d;

    /*Przyjmowane dane
     */
    private String Messenge;

    /**Instrukcji przesyłane razem z elementem
     */
    private String key;

    /**Przysłąny element w formacie String
     */
    private String element;

    /**Przekształcony element
     */
    private Object element1;

    /**Wkazuje czy dane drzewo binarne jest typu Integer
     *
     */
    private boolean integ;

    /**Wkazuje czy dane drzewo binarne jest typu Double
     */
    private boolean doub;

    /**Serwer programu
     */
    static private ServerSocket server;

    /**Zabiezpiecza połączenie z klientem
     */
    static private Socket connection;

    /**Strumień danych wejściowych
     */
    static private ObjectInputStream input;

    /**Strumień danych wyjściowych
     */
    static private ObjectOutputStream output;

    /**Konstruktor tworzący serwer
     */
    Server(){
        integ=false;
        doub=false;

        TypDrzewa typDrzewa = new TypDrzewa();
    }


    @Override
    public void run() {
        try {
            server = new ServerSocket(5678,1);
            while (true) {
                connection = server.accept();
                output = new ObjectOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());

                Messenge = (String) input.readObject();

                key = Messenge.substring(0, 7);
                element = Messenge.substring(7);

                element1=element;

                if (key.equals("draw  :")) {
                    output.writeObject(d.toString());
                }
                try {
                    if (integ) {
                        int x = Integer.parseInt(element);
                        element1=x;
                    }
                    if (doub) {
                        double y = Double.parseDouble(element);
                        element1 = y;
                    }

                    if (key.equals("add   :")) {
                        d.insert((Comparable) element1);
                        System.out.println(d);
                        output.writeObject("Wstawiono element " + element1);
                    }

                    if (key.equals("delete:")) {
                        if (d.isElement((Comparable) element1)) {
                            d.delete((Comparable) element1);
                            System.out.println(d);
                            output.writeObject("Usunięto element " + element1);
                        } else {
                            output.writeObject(element1 + " nie ma w drzewie");
                        }
                    }
                    if (key.equals("check :")) {
                        if (d.isElement((Comparable) element1)) {
                            output.writeObject(element1 + " jest w dzewie");
                        } else {
                            output.writeObject(element1 + " nie jest w dzewie");
                        }
                    }
                }catch (NumberFormatException e){
                    try {
                        output.writeObject("Niepoprawny typ");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /** Klasa obslugiwająca wybór typu dzewa binarnego
     */
    class TypDrzewa extends JFrame implements ActionListener{
        /**Przycisk wyboru typu Integer jako typu dzewa binarnego serwera
         */
        JButton b1;

        /**Przycisk wyboru typu String jako typu dzewa binarnego serwera
         */
        JButton b2;

        /**Przycisk wyboru typu Double jako typu dzewa binarnego serwera
         */
        JButton b3;

        /**Tworzy okno wybory typu drzewa binarnego
         */
        TypDrzewa(){
            setLocation(550,300);
            setLayout(new GridLayout(3,1));
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            b1 = new JButton("Integer");
            b2 = new JButton("String");
            b3 = new JButton("Double");
            b1.setBackground(Color.WHITE);
            b2.setBackground(Color.WHITE);
            b3.setBackground(Color.WHITE);
            b1.addActionListener(this);
            b2.addActionListener(this);
            b3.addActionListener(this);
            add(b1);
            add(b2);
            add(b3);
            setSize(200,150);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==b1){
                integ=true;
                d = new Tree<Integer>();
            }
            if(e.getSource()==b2){
                d = new Tree<String>();
            }if(e.getSource()==b3){
                doub=true;
                d = new Tree<Double>();
            }
            setVisible(false);
        }
    }
}
