package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


/**Klasa, która implementuje funkcji klienta w programie drzewa binarnego
 */
public class Main extends JFrame implements Runnable, ActionListener {

    /**Zabiezpiecza połączenie z serwerem
     */
    static private Socket connection;

    /**Strumień danych wyjściowych
     */
    static private ObjectOutputStream output;

    /**Strumień danych wejściowych
     */
    static private ObjectInputStream input;

    /**Tekstowe pole dla danych
     */
    final private JTextField t;

    /**Przycisk wyszukiwania elementu
     */
    private final JButton b1;

    /**Przycisk wstawienia elementu
     */
    private final JButton b2;

    /**Przycisk usunięcia elementu
     */
    private final JButton b3;

    /**Przycisk wyśwetlenia drzewa w konsoli
     */
    private final JButton b4;

    /**Konstruktor tworzący okno klienta
     */
    Main (String name){
        super(name);
        setLocation(550,300);
        setLayout(new FlowLayout());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        t = new JTextField(10);

        b1 = new JButton("Check");
        b2 = new JButton("Insert");
        b3 = new JButton("Delete");
        b4 = new JButton("Draw");
        b1.setBackground(Color.WHITE);
        b2.setBackground(Color.WHITE);
        b3.setBackground(Color.WHITE);
        b4.setBackground(Color.WHITE);


        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        add(t);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        setSize(200,150);
    }

    public static void main(String[] args) {
        new Thread(new Main("Test")).start();
        new Thread(new Server()).start();
    }

    @Override
    public void run() {
        try {
            while (true){
                connection = new Socket(InetAddress.getByName("127.0.0.1"),5678);
                output = new ObjectOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());
                JOptionPane.showMessageDialog(null,input.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**Umoźliwia wysyłanie danych na serwer
     *@param obj Wysłane dane
     */
    private static void sendData(Object obj) throws IOException {
        try {
            output.flush();
            output.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String key = null;

        Object source = evt.getSource();

        if (source == b1) {key="check :";}
        if (source == b2) {key="add   :";}
        if (source == b3) {key="delete:";}
        if (source == b4) {key="draw  :";}

        try {
            sendData(key+t.getText());
            t.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
